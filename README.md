[![Build Status](https://travis-ci.org/finbourne/lusid-client-java.svg?branch=master)](https://travis-ci.org/finbourne/lusid-client-java)

## Running Connectivity Tests

A set of tests have been provided to help diagnose any issues that may occur trying to connect to the LUSID API. These tests verify that you are able to make https calls (independent of LUSID) and
can access the domains required in order to use the LUSID API. 

Prior to running the tests, edit [ConnectivityTests.java](https://github.com/finbourne/lusid-client-java/blob/master/src/test/java/com/finbourne/lusid/integration/ConnectivityTests.java)
to add in the appropriate values you have been supplied.

The connectivity tests are run using:

```
$ mvn -e -fae -Dtest=ConnectivityTests test
```

or with docker using

```
$ docker run -it --rm -v $(pwd):/usr/src/lusid-client-java -w /usr/src/lusid-client-java maven:3.6.0-jdk-11-slim mvn -e -fae -Dtest=ConnectivityTests test
```

# API Credentials

All authenticated calls to the LUSID API require an OpenID Connect ID token which is issued from a your token issuer url. The details of these can be found on your LUSID portal under "Applications" within the "Identity and Access Management" section. 

[[https://github.com/finbourne/lusid-client-java/blob/master/iam-app.png]]

The API details can be supplied to the SDK as environment variables or via a file. The SDK will first check for the existence of the environment variables and if not found, will then check for `secrets.json` file.

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

To supply the SDK configuration via a file, create a `secrets.json` in the src/test/resources folder with the structure below and populated with the appropriate values. If you use a proxy you can supply the proxy details in the `proxy` section. The `proxy` section is optional and only needed if you use a proxy.

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

## Running tests

Run the tests using a local maven installion using:

```
$ mvn -e -fae test
```

Alernatively it can be run using docker with:
```
$ docker run -it --rm -v $(pwd):/usr/src/lusid-client-java -w /usr/src/lusid-client-java maven:3.6.0-jdk-11-slim mvn -e -fae test
```
