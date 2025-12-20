## Start Wiremock
```
Start Wiremock in the standalone-wiremock project.
```
## Create Symbolic Link
Create folder C:\opt\springboot-wiremock\wiremockclient  
mklink /J "C:\opt\springboot-wiremock\wiremockclient\wiremock-root" "C:/pathto/springboot-wiremock/wiremockclient/src/test/resources/wiremock-root"  

In Java class, use /opt/springboot-wiremock/wiremockclient/wiremock-root  

## Run WireMock client test

## Test
```
curl http://localhost:8080/__admin/mappings
curl http://localhost:8080/service/two
```
