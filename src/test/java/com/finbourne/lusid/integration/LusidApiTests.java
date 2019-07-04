package com.finbourne.lusid.integration;

import com.finbourne.lusid.ApiClient;
import com.finbourne.lusid.ApiException;
import com.finbourne.lusid.api.InstrumentsApi;
import com.finbourne.lusid.api.PortfoliosApi;
import com.finbourne.lusid.api.PropertyDefinitionsApi;
import com.finbourne.lusid.api.TransactionPortfoliosApi;
import com.finbourne.lusid.model.*;
import com.finbourne.lusid.utilities.ApiClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.finbourne.lusid.integration.TestDataUtilities.LUSID_INSTRUMENT_IDENTIFIER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LusidApiTests {

    private static List<String> instrumentIds;

    private static PortfoliosApi portfoliosApi;
    private static TransactionPortfoliosApi transactionPortfoliosApi;
    private static PropertyDefinitionsApi propertyDefinitionsApi;

    private static InstrumentLoader instrumentLoader;

    @BeforeClass
    public static void setUp() throws Exception {

        ApiClient apiClient = new ApiClientBuilder("secrets.json").build();

        portfoliosApi = new PortfoliosApi(apiClient);
        transactionPortfoliosApi = new TransactionPortfoliosApi(apiClient);
        propertyDefinitionsApi = new PropertyDefinitionsApi(apiClient);

        InstrumentsApi instrumentsApi = new InstrumentsApi(apiClient);
        instrumentLoader = new InstrumentLoader(instrumentsApi);
        instrumentIds = instrumentLoader.loadInstruments();
    }

    @AfterClass
    public static void tearDown() throws ApiException {
        instrumentLoader.deleteInstruments();
    }

    @Test
    public void create_portfolio() throws ApiException {

        final String scope = "finbourne";
        final String uuid = UUID.randomUUID().toString();

        final CreateTransactionPortfolioRequest request = new CreateTransactionPortfolioRequest()
                .displayName(String.format("Portfolio-%s", uuid))
                .code(String.format("Id-%s", uuid))
                .baseCurrency("GBP");

        final Portfolio portfolio = transactionPortfoliosApi.createPortfolio(scope, request);

        assertEquals(request.getCode(), portfolio.getId().getCode());
    }
}
