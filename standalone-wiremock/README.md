## Start WireMock - default port 8080
```
java -jar wiremock-standalone-3.13.2.jar
```

## Create a stub mapping by by posting to WireMock’s Admin API:
```bash
curl -X POST \
-d '{ "request": { "url": "/get/this", "method": "GET" }, "response": { "status": 200, "body": "Here it is!\n" }}' \
http://localhost:8080/__admin/mappings
```

## Test the stub
```
curl localhost:8080/get/this
```

## Create 2nd stub mapping
```bash
curl -X POST \
-d '{ "request": { "url": "/get/that", "method": "GET" }, "response": { "status": 200, "body": "Here is the 2nd response!\n" }}' \
http://localhost:8080/__admin/mappings
```

## Test the 2nd stub
```
curl localhost:8080/get/that
```

## Create the 3rd stub mapping for the same endpoint as the 1st stub mapping
```bash
curl -X POST \
-d '{ "request": { "url": "/get/this", "method": "GET" }, "response": { "status": 200, "body": "A differnt response for the same endpoint\n" }}' \
http://localhost:8080/__admin/mappings
```

## Test the 3rd stub
```
curl localhost:8080/get/this
```
The 3rd stub mapping simply overrides the 1st stub mapping.  

## Crate a stub mapping by configuring a JSON file under /mappings folder
```
{
    "request": {
        "method": "GET",
        "url": "/api/mytest"
    },
    "response": {
        "status": 200,
        "body": "Hello mytest"
    }
}
```
curl localhost:8080/api/mytest  

## Multi-stub JSON file
```
{
    "mappings": [
        {
            "request": {
                "method": "GET",
                "url": "/api/one"
            },
            "response": {
                "status": 200,
				"body": "API one"
            }
        },
        {
            "id": "8c5db8b0-2db4-4ad7-a99f-38c9b00da3f7",
            "request": {
                "url": "/api/two"
            },
            "response": {
				"status": 200,
                "body": "Updated API two"
            }
        }
    ]
}

```

## Fetching all stub mappings
```
curl http://localhost:8080/__admin/mappings
```
