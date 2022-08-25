package com.finbourne.lusid.integration;

import com.finbourne.lusid.ApiClient;
import com.finbourne.lusid.api.InstrumentsApi;
import com.finbourne.lusid.model.InstrumentIdTypeDescriptor;
import com.finbourne.lusid.model.ResourceListOfInstrumentIdTypeDescriptor;
import com.finbourne.lusid.utilities.ApiClientBuilder;
import com.finbourne.lusid.utilities.ApiConfiguration;
import com.finbourne.lusid.utilities.ApiConfigurationBuilder;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

public class LusidApiTests {

    @Test
    public void call_lusid_api() throws Exception {

        ApiConfiguration apiConfiguration = new ApiConfigurationBuilder().build("secrets.json");
        ApiClient apiClient = new ApiClientBuilder().build(apiConfiguration);

        InstrumentsApi instrumentsApi = new InstrumentsApi(apiClient);

        ResourceListOfInstrumentIdTypeDescriptor instrumentIdentifierTypes = instrumentsApi.getInstrumentIdentifierTypes();

        List<InstrumentIdTypeDescriptor> values = instrumentIdentifierTypes.getValues();

        assertThat(values, notNullValue());
        assertThat(values.size(), greaterThanOrEqualTo(0));
    }
}
