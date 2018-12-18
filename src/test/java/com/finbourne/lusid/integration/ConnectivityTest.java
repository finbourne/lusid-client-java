package com.finbourne.lusid.integration;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(value = Parameterized.class)
public class ConnectivityTest {

    @Parameterized.Parameter
    public String testUrl;

    @Parameterized.Parameters(name = "{index}: testUrl - {0}")
    public static Object[] data() {
        return new Object[]{
                "https://www.howsmyssl.com/a/check",
                "https://api.lusid.com/api/metadata/versions",
                "https://lusid.okta.com/oauth2/aus5al5yopbHW2wJn2p6/.well-known/oauth-authorization-server"
        };
    }

    @Test
    public void verify_connection() throws IOException {

        OkHttpClient oktaClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(testUrl)
                .header("Accept", "application/json")
                .get()
                .build();

        Response response = oktaClient.newCall(request).execute();

        assertThat(response.code(), is(equalTo(200)));
    }
}
