
Java REST Server
================

The project is created using `maven-archetype-webapp`.

```
|                   |                          |
|                   |                          |
|    endpount       |      Service             |
|                   |                          |
|                   |                          |
|                   |                          |

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