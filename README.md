
Java HTTP/REST micro-service
==========================

This is a micro-service baseline in java 12, spring-boot `2.1.x`

```
|                   |                          |
|                   |                          |
|    /endpoint      |      service             |
|        |          |                          |
|        v          |                          |
|     schema jar    |                          |

```


- [tests](#tests)
- [build/ run-app in x env](#run-app-in-x-env)
- [Deployment + Load balancing](#Deployment-+-Load-balancing)
- [build artifact](#build-artifact)
- [perf](#perf)

tests
-----

```bash
mvn test
```

build/ [run-app in x env](http://docs.spring.io/spring-boot/docs/current/maven-plugin/examples/run-profiles.html)
----------------------------------------------------------------------------------------------------------

[with `application.properties` configured to `e2e`](http://stackoverflow.com/a/35757421/432903)

```bash

spring.profiles.active=e2e

mvn spring-boot:run
```

or set profile in `pom.xml`.

[BUT the best way is to have environment variable](http://stackoverflow.com/a/35534970/432903),
[that will determine which config to use.](http://stackoverflow.com/a/38337109/432903)

```
export SPRING_PROFILES_ACTIVE=production
```

or

```bash
export APP_ENVIRONMENT=production
spring.profiles.active=${APP_ENVIRONMENT} ##not necessary
```

```bash
curl -v -XGET http://localhost:8080/restapi/health | python -m json.tool
Note: Unnecessary use of -X or --request, GET is already inferred.
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /health HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.54.0
> Accept: */*
> 
< HTTP/1.1 200 
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Mon, 08 Jul 2019 03:24:47 GMT
< 
{ [89 bytes data]
100    83    0    83    0     0   3134      0 --:--:-- --:--:-- --:--:--  3192
* Connection #0 to host localhost left intact
{
    "applicationName": "rest-api",
    "applicationVersion": "1.0",
    "timestamp": 1562556287580
}
```

uses base container image - https://github.com/duwamish-os/java-microservice-base-image

or using docker (setup the HTTP_PROXY, HTTPS_PROXY and NO_PROXY)

![](docker_proxy.png)


```bash
mvn clean package
eval $(minikube docker-env) # instead of pushing your Docker image to a registry, you can simply build the image using the same Docker host as the Minikube VM
docker build -t rest-server:v1 .
#docker run -it --rm -p 9090:8080 rest-server:v1
```

publish artifact/ container image
---------------------------

```
docker tag rest-server:v1 ???.dkr.ecr.us-east-1.amazonaws.com/???-dev
aws ecr get-login --no-include-email --profile ???-dev --region us-east-1
docker login -u AWS -p <<password>>  https://???.dkr.ecr.us-east-1.amazonaws.com

docker push <aws_account_id>.dkr.ecr.us-east-1.amazonaws.com/duwamish-repository
```

REST API deps size

```bash
$ du -sh target/restapi/WEB-INF/lib/spring-*
372K	target/restapi/WEB-INF/lib/spring-aop-4.3.6.RELEASE.jar
748K	target/restapi/WEB-INF/lib/spring-beans-4.3.6.RELEASE.jar
648K	target/restapi/WEB-INF/lib/spring-boot-1.4.4.RELEASE.jar
952K	target/restapi/WEB-INF/lib/spring-boot-autoconfigure-1.4.4.RELEASE.jar
4.0K	target/restapi/WEB-INF/lib/spring-boot-starter-1.4.4.RELEASE.jar
4.0K	target/restapi/WEB-INF/lib/spring-boot-starter-logging-1.4.4.RELEASE.jar
4.0K	target/restapi/WEB-INF/lib/spring-boot-starter-tomcat-1.4.4.RELEASE.jar
4.0K	target/restapi/WEB-INF/lib/spring-boot-starter-web-1.4.4.RELEASE.jar
1.1M	target/restapi/WEB-INF/lib/spring-context-4.3.6.RELEASE.jar
1.1M	target/restapi/WEB-INF/lib/spring-core-4.3.6.RELEASE.jar
260K	target/restapi/WEB-INF/lib/spring-expression-4.3.6.RELEASE.jar
800K	target/restapi/WEB-INF/lib/spring-web-4.3.6.RELEASE.jar
896K	target/restapi/WEB-INF/lib/spring-webmvc-4.3.6.RELEASE.jar
```

deployment + load balancing
-----

service

```bash
# kubectl apply -f k8s-nodes.yaml
kubectl create -f restserver-k8-service.yaml --namespace dev
#kubectl delete service rest-server
λ kubectl get services --namspace dev
NAME          TYPE           CLUSTER-IP      EXTERNAL-IP                                                               PORT(S)        AGE
kubernetes    ClusterIP      10.100.0.1      <none>                                                                    443/TCP        32m
rest-server   LoadBalancer   10.100.214.66   a1d4b08e2a6a211e9888c1233f0bb1c4-1052742191.us-east-1.elb.amazonaws.com   80:30214/TCP   13m

## internal LB only accessible from k8s nodes 
λ kubectl get services --namespace dev
NAME          TYPE           CLUSTER-IP       EXTERNAL-IP                                                                        PORT(S)        AGE
rest-server   LoadBalancer   10.100.183.206   internal-afa3a1c3bb5b011e9ac3512ee202494e-2019297208.us-east-1.elb.amazonaws.com   80:31252/TCP   14s
```

deployment


```bash
kubectl create -f restserver-k8-deployment.yaml --namespace dev
#kubectl delete deployment rest-server

λ kubectl get deployments --output wide
NAME          READY     UP-TO-DATE   AVAILABLE   AGE       CONTAINERS    IMAGES                                                   SELECTOR
rest-server   1/1       1            1           16m       rest-server   ???.dkr.ecr.us-east-1.amazonaws.com/rest-server:latest   app=rest-server

minikube service rest-server #expose your Service outside of the cluster
```

https://github.com/redhat-developer-demos/spring-boot-configmaps-demo

```bash
curl -v a6e2953f6a6a411e9888c1233f0bb1c4-146709191.us-east-1.elb.amazonaws.com/health
*   Trying 52.3.214.190...
* TCP_NODELAY set
* Connected to a6e2953f6a6a411e9888c1233f0bb1c4-146709191.us-east-1.elb.amazonaws.com (52.3.214.190) port 80 (#0)
> GET /health HTTP/1.1
> Host: a6e2953f6a6a411e9888c1233f0bb1c4-146709191.us-east-1.elb.amazonaws.com
> User-Agent: curl/7.54.0
> Accept: */*
> 
< HTTP/1.1 200 
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Mon, 15 Jul 2019 02:05:32 GMT
< 
* Connection #0 to host a6e2953f6a6a411e9888c1233f0bb1c4-146709191.us-east-1.elb.amazonaws.com left intact
{"timestamp":1563156332187,"applicationName":"rest-api","applicationVersion":"1.0"}
```


[perf](perf.md)
----


Also see :

https://github.com/prayagupd/nodejs-microservice

