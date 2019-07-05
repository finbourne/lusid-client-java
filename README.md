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


## Test Configuration

Prior to running the tests create a properties file named `secrets.json` in the `src/test/resources` folder with the structure below and populated with the appropriate values.

``` json
{
  "api" : {
    "tokenUrl": "",
    "username": "",
    "password": "",
    "clientId": "",
    "clientSecret": "",
    "apiUrl": ""
  }
}
```

| Key | Description |
| --- | --- |
| `tokenUrl` | Okta endpoint to generate the authentication token.  This will be of the form https://\<your LUSID domain>.okta.com/oauth2/\<key\>/v1/token |
| `username` | Okta username |
| `password` | Okta password |
| `clientId` | Okta client identifier |
| `clientSecret` | Okta client secret |
| `apiUrl` | API url |

## Running tests

Run the tests using a local maven installion using:

```
$ mvn -e -fae test
```

Alernatively it can be run using docker with:
```
$ docker run -it --rm -v $(pwd):/usr/src/lusid-client-java -w /usr/src/lusid-client-java maven:3.6.0-jdk-11-slim mvn -e -fae test
```