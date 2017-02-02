
eventstream pipeline
=========================


```
|                   |                          |
|                   |                          |
|    endpoint       |      Eventstream driver  |   Eventstream
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

```bash
curl -H "Content-Type: application/json" -X POST -d '{"eventType" : "TestIngestionEvent", "field1" : "someValue2"}' localhost:9000/ingest

{"eventId":"b2a3b9e1-8ad1-4d0e-b673-c10125445704","status":"Success"}

```

Eventstream
-----------

```bash
/usr/local/kafka_2.11-0.10.1.1/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic "EventStream" --from-beginning

{"createdTime":1486065173552,"eventType":"TestIngestionEvent","field1":"someValue3"}

```

Note
----

you need an event object `TestIngestionEvent` which extends `BaseEvent` in 
`com.api.events` package

Maybe I should just not map to a class, instead just add `createTime`
event attribute. 

That way its kind of endpoint for ingestion, but I will have to add a
 validation layer.