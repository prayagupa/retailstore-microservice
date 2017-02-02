
Java REST Server
================


```
|                   |                          |
|                   |                          |
|    endpount       |      Service             |
|                   |                          |
|                   |                          |
|                   |                          |

```


tests
-----

```
mvn test
```

run-app
-------

```
$ mvn spring-boot:run
```

```
curl -XGET http://localhost:9000/health

{
"id": 1,
"eventId": "some value",
"status": "I'm Running"
}

```

Ingestion
---------

```
curl -H "Content-Type: application/json" -X POST -d '{"eventType" : "TestIngestionEvent", "field1" : "someValue2"}' localhost:9000/ingest

{"eventId":"b2a3b9e1-8ad1-4d0e-b673-c10125445704","status":"Success"}

```