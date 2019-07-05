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
                "https://www.howsmyssl.com/a/check"

                /*
                    enable the following url with your specific LUSID domain e.g.

                        https://myco.lusid.com/api/api/metadata/versions

                */
                //"https://<enter your LUSID domain>.lusid.com/api/api/metadata/versions",

                /*
                    enable the following url with your specific LUSID domain e.g.

                        https://myco.okta.com/oauth2/aus12345abcdef/.well-known/oauth-authorization-server
                 */

                //"https://<your LUSID domain>/oauth2/<your LUSID app id>/.well-known/oauth-authorization-server"
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

