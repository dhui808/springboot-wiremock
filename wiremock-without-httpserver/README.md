## Running Wiremock without HTTP Server
Since Wiremock v2.32.0, the DirectCallHttpServer provides this ability.

## Run WireMock client test
mvn test

## Test
```
curl http://localhost:8080/__admin/mappings
curl http://localhost:8080/service/two
```
