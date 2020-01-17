package com.finbourne.lusid.integration;

import com.finbourne.lusid.ApiClient;
import com.finbourne.lusid.api.ScopesApi;
import com.finbourne.lusid.model.ResourceListOfScopeDefinition;
import com.finbourne.lusid.utilities.ApiClientBuilder;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LusidApiTests {

    @Test
    public void call_lusid_api() throws Exception {

        ApiClient apiClient = new ApiClientBuilder().build("secrets.json");
        ScopesApi scopesApi = new ScopesApi(apiClient);

        ResourceListOfScopeDefinition scopes = scopesApi.listScopes(null);

        assertNotNull(scopes.getValues());
        assertNotNull(scopes.getHref());
        assertNotNull(scopes.getLinks());
    }
}
