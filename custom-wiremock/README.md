## Create custom WireMock
* Use Maven Shade plugin to create custom WireMock wrapping the real WireMock
* Create a folder under src/main/resources/, e.g. wiremock-root, which inludes  
  __files/ folder and mappings/ folder.
* Put all mapping JSON files under mappings/ folder. __files/ is not of much use
* Run the custom WireMock application as below:
  java -jar custom-wiremock.jar --load-resources-from-classpath wiremock-root  

## Build
mvn clean package  

## Start custom Wiremock
```
java -jar target/custom-wiremock-1.0-SNAPSHOT.jar --load-resources-from-classpath wiremock-root 
```

## Test
curl http://localhost:8080/__admin/mappings  
curl http://localhost:8080/service/three

## Shutdown WireMock server
curl -X POST http://localhost:8080/__admin/shutdown -d "{}"