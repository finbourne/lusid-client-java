package com.finbourne.lusid.integration;

import com.finbourne.lusid.ApiClient;
import com.finbourne.lusid.api.ApplicationMetadataApi;
import com.finbourne.lusid.model.VersionSummaryDto;
import com.finbourne.lusid.utilities.ApiClientBuilder;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;

import static org.junit.Assert.assertNotNull;

public class LusidApiTests {

    @Test
    public void call_lusid_api() throws Exception {

        ApiClient apiClient = new ApiClientBuilder("secrets.json").build();
        ApplicationMetadataApi metadataApi = new ApplicationMetadataApi(apiClient);

        VersionSummaryDto version = metadataApi.getLusidVersions();

        assertNotNull(version.getApiVersion());
        assertNotNull(version.getBuildVersion());
        assertNotNull(version.getExcelVersion());
    }

    @Test
    @Ignore
    public void call_lusid_api_with_proxy() throws Exception {

        //  this should be your proxy address
        InetSocketAddress   proxy = new InetSocketAddress(InetAddress.getLocalHost(), 8888);

        //  username and password for the proxy
        String  proxyUsername = "user";
        String  proxyPassword = "password";

        ApiClient apiClient = new ApiClientBuilder("secrets.json").build();

        apiClient.setHttpClient(
                new OkHttpClient.Builder()
                        .proxy(new Proxy(Proxy.Type.HTTP, proxy))
                        .proxyAuthenticator((route, response) -> {
                            String credential = Credentials.basic(proxyUsername, proxyPassword);
                            return response.request().newBuilder()
                                    .header("Proxy-Authorization", credential)
                                    .build();
                        })
                        .build()
        );

        ApplicationMetadataApi metadataApi = new ApplicationMetadataApi(apiClient);

        VersionSummaryDto version = metadataApi.getLusidVersions();

        assertNotNull(version.getApiVersion());
        assertNotNull(version.getBuildVersion());
        assertNotNull(version.getExcelVersion());
    }

}
