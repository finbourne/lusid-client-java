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
                    enable the following url with your specific LUSID client code e.g.

                        https://myco.lusid.com/api/api/metadata/versions

                */
                //"https://<enter your LUSID client code>.lusid.com/api/api/metadata/versions",

                /*
                    enable the following url with your issuer URL e.g.

                        https://lusid-myco.okta.com/oauth2/aus12345abcdef/.well-known/oauth-authorization-server
                 */

                //"<enter your issuer URL>/.well-known/oauth-authorization-server"
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

        //  proxyAdress, port, username and password for the proxy
        InetAddress  proxyAddress = InetAddress.getLocalHost();
        Integer port = 8889;
        String  proxyUsername = "user";
        String  proxyPassword = "password";
        
        InetSocketAddress   proxy = new InetSocketAddress(proxyAddress, port);

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

