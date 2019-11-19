package com.finbourne.lusid.integration;

import com.finbourne.lusid.ApiClient;
import com.finbourne.lusid.api.ApplicationMetadataApi;
import com.finbourne.lusid.model.VersionSummaryDto;
import com.finbourne.lusid.utilities.ApiClientBuilder;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LusidApiTests {

    @Test
    public void call_lusid_api() throws Exception {

        ApiClient apiClient = new ApiClientBuilder().build("secrets.json");
        ApplicationMetadataApi metadataApi = new ApplicationMetadataApi(apiClient);

        VersionSummaryDto version = metadataApi.getLusidVersions();

        assertNotNull(version.getApiVersion());
        assertNotNull(version.getBuildVersion());
        assertNotNull(version.getExcelVersion());
    }
}
