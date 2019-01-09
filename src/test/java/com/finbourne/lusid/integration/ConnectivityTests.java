package com.finbourne.lusid.integration;

import okhttp3.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(value = Parameterized.class)
public class ConnectivityTests {

    @Parameterized.Parameter
    public String testUrl;

    @Parameterized.Parameters(name = "{index}: testUrl - {0}")
    public static Object[] data() {
        return new Object[] {
                "https://www.howsmyssl.com/a/check",
                "https://api.lusid.com/api/metadata/versions",
                "https://lusid.okta.com/oauth2/aus5al5yopbHW2wJn2p6/.well-known/oauth-authorization-server"
        };
    }

    @Test
    public void verify_connection() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(testUrl)
                .header("Accept", "application/json")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        assertThat(response.code(), is(equalTo(200)));
    }

    @Test
    @Ignore
    public void verify_connection_with_proxy() throws IOException {

        //  this should be your proxy address
        InetSocketAddress   proxy = new InetSocketAddress(InetAddress.getLocalHost(), 8888);

        //  username and password for the proxy
        String  proxyUsername = "user";
        String  proxyPassword = "password";

        OkHttpClient client = new OkHttpClient.Builder()
                .proxy(new Proxy(Proxy.Type.HTTP, proxy))
                .proxyAuthenticator((route, response) -> {
                    String credential = Credentials.basic(proxyUsername, proxyPassword);
                    return response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                })
                .build();

        Request request = new Request.Builder()
                .url(testUrl)
                .header("Accept", "application/json")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        assertThat(response.code(), is(equalTo(200)));
    }
}

