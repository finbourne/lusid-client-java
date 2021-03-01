| branch | status |
|--|--|
|`master`| ![Daily build](https://github.com/finbourne/lusid-client-java/workflows/Daily%20build/badge.svg) ![Build and test](https://github.com/finbourne/lusid-client-java/workflows/Build%20and%20test/badge.svg) [![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Ffinbourne%2Flusid-client-java.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Ffinbourne%2Flusid-client-java?ref=badge_shield)
 |

## Running Connectivity Tests

A set of tests have been provided to help diagnose any issues that may occur trying to connect to the LUSID API. These tests verify that you are able to:
 
- Make secure HTTPS calls (independent of LUSID)
- Access the LUSID API via an unauthenticated endpoint
- Access the identity provider (Okta) for authentication


Prior to running the tests, edit [ConnectivityTests.java](https://github.com/finbourne/lusid-client-java/blob/master/src/test/java/com/finbourne/lusid/integration/ConnectivityTests.java)
to add in the appropriate values you have been supplied. You will need:

- Your LUSID client e.g. if your domain is https://globalfundmanager.lusid.com your client code is "globalfundmanager"
- Your Okta issuer URL, this can be found at https://{your client code}.lusid.com/app/iam/applications e.g. 
https://globalfundmanager.lusid.com/app/iam/applications. It will have the form https://lusid-{your client code}.okta.com/oauth2/{your auth server id}
as shown in the screenshot below.

![API credentials](https://github.com/finbourne/lusid-client-java/blob/master/iam-app.png)


The connectivity tests are run using:

```
$ mvn -e -fae -Dtest=ConnectivityTests test
```

or with docker using

```
$ docker run -it --rm -v $(pwd):/usr/src/lusid-client-java -w /usr/src/lusid-client-java maven:3.6.0-jdk-11-slim mvn -e -fae -Dtest=ConnectivityTests test
```

# Using a Proxy

If the connectivity tests fail and/or you need to use a proxy, remove the `@Ignore` annotation on the
`verify_connection_with_proxy()` test and run it adding in your:

- proxyAddress
- port
- proxyUsername
- proxyPassword


# Authenticating with your Credentials

Once you have successfully demonstrated connectivity, you may wish to verify that you can successfully authenticate your user
and make a call to the LUSID API. You can do this by running the tests in [LusidApiTests.java](https://github.com/finbourne/lusid-client-java/blob/master/src/test/java/com/finbourne/lusid/integration/LusidApiTests.java)

The API details can be supplied to the SDK as environment variables or via a file. 
The SDK will first check for the existence of the environment variables and if not found, will then check for `secrets.json` file.

## Environment Variables

The SDK configuration can be supplied by setting the following environment variables:

| Name |  Value |
| --- | --- |
| `FBN_LUSID_API_URL` | Your LUSID API url, this is the value for 'API Url' in your portal |
| `FBN_TOKEN_URL` | Okta endpoint to generate the authentication token, this is the value for 'Token Url' in your portal |
| `FBN_CLIENT_ID` | OpenID Connect Client ID, this is the value for 'Client Id' in your portal |
| `FBN_CLIENT_SECRET` | OpenID Connect Client Secret, this is the value for 'Secret' in your portal |
| `FBN_USERNAME` | The username of the account being used for accessing the API |
| `FBN_PASSWORD` | The password of the account being used for accessing the API |
| `FBN_APP_NAME` | An optional identifier for your application |

If you use a proxy you can supply the proxy details via the following environment variables. These are optional and only needed if you use a proxy.

| Name |  Value |
| --- | --- |
| `FBN_PROXY_ADDRESS` | Proxy address e.g. proxy.internal.myco.com |
| `FBN_PROXY_PORT` | Proxy port |
| `FBN_PROXY_USERNAME` | Proxy username |
| `FBN_PROXY_PASSWORD` | Proxy password |

## Configuration File

To supply the SDK configuration via a file, create a `secrets.json` in the `src/test/resources` folder (create it if it doesn't exist) with the structure below and populated with the appropriate values. If you use a proxy you can supply the proxy details in the `proxy` section. The `proxy` section is optional and only needed if you use a proxy.

``` json
{
  "api" : {
    "apiUrl": "",
    "tokenUrl": "",
    "clientId": "",
    "clientSecret": "",
    "username": "",
    "password": "",
    "applicationName": ""    
  },
  "proxy": {
    "proxyAddress": "",
    "proxyPort": 8888,
    "username": "",
    "password": ""
  }
}
```

| Key | Description |
| --- | --- |
| `apiUrl` | Your LUSID API url, this is the value for 'API Url' in your portal |
| `tokenUrl` | Okta endpoint to generate the authentication token, this is the value for 'Token Url' in your portal |
| `clientId` | OpenID Connect Client ID, this is the value for 'Client Id' in your portal |
| `clientSecret` | OpenID Connect Client Secret, this is the value for 'Secret' in your portal |
| `username` | The username of the account being used for accessing the API |
| `password` | The password of the account being used for accessing the API |
| `applicationName` | An optional identifier for your application |
| `proxyAddress` | Proxy address e.g. proxy.internal.myco.com |
| `proxyPort` | Proxy port |
| `username` | Proxy username |
| `password` | Proxy password |


The path to the `secrets.json` is then passed to an [`ApiClientBuilder`](https://github.com/finbourne/lusid-sdk-java/blob/master/sdk/src/main/java/com/finbourne/lusid/utilities/ApiClientBuilder.java) which returns a configured LUSID API client.

You can read more about getting your credentials here [Getting Started with the LUSID APIs and SDKs](https://support.finbourne.com/getting-started-with-apis-sdks)

## Running all tests together

Run all the tests together using a local maven installation using:

```
$ mvn -e -fae test
```

Alternatively it can be run using docker with:
```
$ docker run -it --rm -v $(pwd):/usr/src/lusid-client-java -w /usr/src/lusid-client-java maven:3.6.0-jdk-11-slim mvn -e -fae test
```


## License
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Ffinbourne%2Flusid-client-java.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2Ffinbourne%2Flusid-client-java?ref=badge_large)