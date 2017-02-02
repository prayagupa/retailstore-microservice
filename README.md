
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