[![Build Status](https://travis-ci.org/finbourne/lusid-client-java.svg?branch=master)](https://travis-ci.org/finbourne/lusid-client-java)

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
| `tokenUrl` | Okta endpoint to generate the authentication token.  This will be of the form https://lusid.okta.com/oauth2/\<key\>/v1/token |
| `username` | Okta username |
| `password` | Okta password |
| `clientId` | Okta client identifier |
| `clientSecret` | Okta client secret |
| `apiUrl` | API url |

## Running tests

Run the tests using a local maven installion using:

```
$ mvn -e -fae -Dtest=LusidApiTests test
```

Alernatively it can be run using docker with:
```
$ docker run -it --rm -v $(pwd):/usr/src/lusid-client-java -w /usr/src/lusid-client-java maven:3.6.0-jdk-11-slim mvn -e -fae -Dtest=LusidApiTests test`
```

