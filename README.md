
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
curl -XGET http://localhost:9000/restapi/health

{
"id": 1,
"eventId": "some value",
"status": "I'm Running"
}

```

Or, build a war and deploy to tomcat http server, contextPath would be taken from `finalName`.

```
mvn clean package
```

Deployment + Load balancing
---------------------------

build artifact
--------------

```
mvn clean package
```

https://aws.amazon.com/elasticbeanstalk/getting-started/

http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/java-getstarted.html

https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install.html?icmpid=docs_elasticbeanstalk_console

![](code_deployment.png)
