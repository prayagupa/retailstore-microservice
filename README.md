
Java HTTP/REST micro-service
==========================

![Retail store](https://github.com/prayagupa/retailstore-rest/actions/workflows/cicd.yaml/badge.svg)


This is a micro-service baseline in java 17, spring-boot `2.5.x`

```
|                   |                          |
|                   |                          |
|    /endpoint      |      service             |
|        |          |                          |
|        v          |                          |
|     schema jar    |                          |

```


- [unit tests](#unit-tests)
- [build/ run-app in x env](#run-app-in-x-env)
- [Deployment + Load balancing](#Deployment-+-Load-balancing)
- [build artifact](#build-artifact)
- [performance](#performance)

unit-tests
-----

```bash
## using gradle build tool
./gradlew clean test

## using maven
## mvn test
```

Run application
----------------------------------------------------------------------------------------------------------

Application can be with different profiles. In actual development cycle, there are
multiple environment a software goes through. A Software Engineer first build locally, if that succeeds
pushes the JAR to higher environments like dev, test, stage/prod-like and finally production.

Environment specific information are stored in properties file in `src/main/resources`.

[with `application.properties` configured to `stage`](http://stackoverflow.com/a/35757421/432903)

```bash
export SPRING_PROFILES_ACTIVE=dev
## or set spring.profiles.active=dev in application.properties
./gradlew run

## You will see logs mentioning the active profile
#2022-12-29 20:13:37.946  INFO INFOid --- [           main] c.a.RESTApplication                      : The following profiles are active: dev
```

Usually the Infrastructure team have certain environment variable set to represent the 
environment in higher environments, which can be set to update `spring.profiles.active` so that Spring picks the right
profile.

```bash
## If environment machines have env APP_ENVIRONMENT available
export APP_ENVIRONMENT=production
## assign the value of APP_ENVIRONMENT to spring.profiles.active
## in application.properties
spring.profiles.active=${APP_ENVIRONMENT}
```

```bash
curl -v -XGET http://localhost:8080/health | python -m json.tool
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

uses base container image - https://github.com/lamatola-os/java-microservice-base-image

REST API deps size

```bash
$ du -sh target/restapi/WEB-INF/lib/spring-* | sort
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

[deployment + load balancing](devops/README.md)
-----

metrics
-------
```bash
curl "http://localhost:9080/metrics/system.cpu.usage"

## export prometheus metrics
## look for http_server_requests
curl localhost:8080/actuator/prometheus
# HELP jvm_memory_committed_bytes The amount of memory in bytes that is committed for the Java virtual machine to use
# TYPE jvm_memory_committed_bytes gauge
jvm_memory_committed_bytes{area="nonheap",id="CodeHeap 'profiled nmethods'",} 9764864.0
jvm_memory_committed_bytes{area="heap",id="G1 Survivor Space",} 6291456.0
jvm_memory_committed_bytes{area="heap",id="G1 Old Gen",} 4.4040192E7
jvm_memory_committed_bytes{area="nonheap",id="Metaspace",} 4.4425216E7
jvm_memory_committed_bytes{area="nonheap",id="CodeHeap 'non-nmethods'",} 2555904.0
jvm_memory_committed_bytes{area="heap",id="G1 Eden Space",} 4.194304E7
jvm_memory_committed_bytes{area="nonheap",id="Compressed Class Space",} 6160384.0
jvm_memory_committed_bytes{area="nonheap",id="CodeHeap 'non-profiled nmethods'",} 2555904.0
# HELP log4j2_events_total Number of fatal level log events
# TYPE log4j2_events_total counter
log4j2_events_total{level="warn",} 0.0
log4j2_events_total{level="debug",} 0.0
log4j2_events_total{level="error",} 0.0
log4j2_events_total{level="trace",} 0.0
log4j2_events_total{level="fatal",} 0.0
log4j2_events_total{level="info",} 7.0
# HELP jvm_gc_memory_allocated_bytes_total Incremented for an increase in the size of the (young) heap memory pool after one GC to before the next
# TYPE jvm_gc_memory_allocated_bytes_total counter
jvm_gc_memory_allocated_bytes_total 8.388608E7
# HELP jvm_buffer_total_capacity_bytes An estimate of the total capacity of the buffers in this pool
# TYPE jvm_buffer_total_capacity_bytes gauge
jvm_buffer_total_capacity_bytes{id="mapped",} 0.0
jvm_buffer_total_capacity_bytes{id="direct",} 57344.0
# HELP jvm_buffer_count_buffers An estimate of the number of buffers in the pool
# TYPE jvm_buffer_count_buffers gauge
jvm_buffer_count_buffers{id="mapped",} 0.0
jvm_buffer_count_buffers{id="direct",} 7.0
# HELP jvm_gc_pause_seconds Time spent in GC pause
# TYPE jvm_gc_pause_seconds summary
jvm_gc_pause_seconds_count{action="end of minor GC",cause="Metadata GC Threshold",} 1.0
jvm_gc_pause_seconds_sum{action="end of minor GC",cause="Metadata GC Threshold",} 0.008
jvm_gc_pause_seconds_count{action="end of minor GC",cause="G1 Evacuation Pause",} 1.0
jvm_gc_pause_seconds_sum{action="end of minor GC",cause="G1 Evacuation Pause",} 0.005
# HELP jvm_gc_pause_seconds_max Time spent in GC pause
# TYPE jvm_gc_pause_seconds_max gauge
jvm_gc_pause_seconds_max{action="end of minor GC",cause="Metadata GC Threshold",} 0.008
jvm_gc_pause_seconds_max{action="end of minor GC",cause="G1 Evacuation Pause",} 0.005
# HELP http_server_requests_seconds  
# TYPE http_server_requests_seconds summary
http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/actuator/prometheus",} 1.0
http_server_requests_seconds_sum{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/actuator/prometheus",} 0.054369136
http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/health",} 1.0
http_server_requests_seconds_sum{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/health",} 0.130059295
http_server_requests_seconds_count{exception="None",method="GET",outcome="CLIENT_ERROR",status="404",uri="/**",} 1.0
http_server_requests_seconds_sum{exception="None",method="GET",outcome="CLIENT_ERROR",status="404",uri="/**",} 0.006792914
# HELP http_server_requests_seconds_max  
# TYPE http_server_requests_seconds_max gauge
http_server_requests_seconds_max{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/actuator/prometheus",} 0.054369136
http_server_requests_seconds_max{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/health",} 0.130059295
http_server_requests_seconds_max{exception="None",method="GET",outcome="CLIENT_ERROR",status="404",uri="/**",} 0.006792914
# HELP jvm_classes_unloaded_classes_total The total number of classes unloaded since the Java virtual machine has started execution
# TYPE jvm_classes_unloaded_classes_total counter
jvm_classes_unloaded_classes_total 0.0
# HELP tomcat_sessions_active_max_sessions  
# TYPE tomcat_sessions_active_max_sessions gauge
tomcat_sessions_active_max_sessions 0.0
# HELP jvm_memory_used_bytes The amount of used memory
# TYPE jvm_memory_used_bytes gauge
jvm_memory_used_bytes{area="nonheap",id="CodeHeap 'profiled nmethods'",} 9734400.0
jvm_memory_used_bytes{area="heap",id="G1 Survivor Space",} 6291456.0
jvm_memory_used_bytes{area="heap",id="G1 Old Gen",} 1.4493696E7
jvm_memory_used_bytes{area="nonheap",id="Metaspace",} 4.2456808E7
jvm_memory_used_bytes{area="nonheap",id="CodeHeap 'non-nmethods'",} 1228672.0
jvm_memory_used_bytes{area="heap",id="G1 Eden Space",} 1.4680064E7
jvm_memory_used_bytes{area="nonheap",id="Compressed Class Space",} 5388176.0
jvm_memory_used_bytes{area="nonheap",id="CodeHeap 'non-profiled nmethods'",} 2423936.0
# HELP tomcat_sessions_rejected_sessions_total  
# TYPE tomcat_sessions_rejected_sessions_total counter
tomcat_sessions_rejected_sessions_total 0.0
# HELP system_cpu_count The number of processors available to the Java virtual machine
# TYPE system_cpu_count gauge
system_cpu_count 12.0
# HELP jvm_classes_loaded_classes The number of classes that are currently loaded in the Java virtual machine
# TYPE jvm_classes_loaded_classes gauge
jvm_classes_loaded_classes 8821.0
# HELP tomcat_sessions_active_current_sessions  
# TYPE tomcat_sessions_active_current_sessions gauge
tomcat_sessions_active_current_sessions 0.0
# HELP jvm_gc_max_data_size_bytes Max size of long-lived heap memory pool
# TYPE jvm_gc_max_data_size_bytes gauge
jvm_gc_max_data_size_bytes 8.589934592E9
# HELP tomcat_sessions_alive_max_seconds  
# TYPE tomcat_sessions_alive_max_seconds gauge
tomcat_sessions_alive_max_seconds 0.0
# HELP system_cpu_usage The "recent cpu usage" for the whole system
# TYPE system_cpu_usage gauge
system_cpu_usage 0.06535340634957493
# HELP tomcat_sessions_expired_sessions_total  
# TYPE tomcat_sessions_expired_sessions_total counter
tomcat_sessions_expired_sessions_total 0.0
# HELP jvm_threads_live_threads The current number of live threads including both daemon and non-daemon threads
# TYPE jvm_threads_live_threads gauge
jvm_threads_live_threads 21.0
# HELP jvm_gc_memory_promoted_bytes_total Count of positive increases in the size of the old generation memory pool before GC to after GC
# TYPE jvm_gc_memory_promoted_bytes_total counter
jvm_gc_memory_promoted_bytes_total 2010296.0
# HELP jvm_threads_daemon_threads The current number of live daemon threads
# TYPE jvm_threads_daemon_threads gauge
jvm_threads_daemon_threads 16.0
# HELP tomcat_sessions_created_sessions_total  
# TYPE tomcat_sessions_created_sessions_total counter
tomcat_sessions_created_sessions_total 0.0
# HELP jvm_buffer_memory_used_bytes An estimate of the memory that the Java virtual machine is using for this buffer pool
# TYPE jvm_buffer_memory_used_bytes gauge
jvm_buffer_memory_used_bytes{id="mapped",} 0.0
jvm_buffer_memory_used_bytes{id="direct",} 57344.0
# HELP process_files_open_files The open file descriptor count
# TYPE process_files_open_files gauge
process_files_open_files 89.0
# HELP process_files_max_files The maximum file descriptor count
# TYPE process_files_max_files gauge
process_files_max_files 10240.0
# HELP jvm_gc_live_data_size_bytes Size of long-lived heap memory pool after reclamation
# TYPE jvm_gc_live_data_size_bytes gauge
jvm_gc_live_data_size_bytes 1.24834E7
# HELP jvm_memory_max_bytes The maximum amount of memory in bytes that can be used for memory management
# TYPE jvm_memory_max_bytes gauge
jvm_memory_max_bytes{area="nonheap",id="CodeHeap 'profiled nmethods'",} 1.22908672E8
jvm_memory_max_bytes{area="heap",id="G1 Survivor Space",} -1.0
jvm_memory_max_bytes{area="heap",id="G1 Old Gen",} 8.589934592E9
jvm_memory_max_bytes{area="nonheap",id="Metaspace",} -1.0
jvm_memory_max_bytes{area="nonheap",id="CodeHeap 'non-nmethods'",} 5836800.0
jvm_memory_max_bytes{area="heap",id="G1 Eden Space",} -1.0
jvm_memory_max_bytes{area="nonheap",id="Compressed Class Space",} 1.073741824E9
jvm_memory_max_bytes{area="nonheap",id="CodeHeap 'non-profiled nmethods'",} 1.22912768E8
# HELP process_cpu_usage The "recent cpu usage" for the Java Virtual Machine process
# TYPE process_cpu_usage gauge
process_cpu_usage 0.0026522767064606476
# HELP system_load_average_1m The sum of the number of runnable entities queued to available processors and the number of runnable entities running on the available processors averaged over a period of time
# TYPE system_load_average_1m gauge
system_load_average_1m 2.5556640625
# HELP process_start_time_seconds Start time of the process since unix epoch.
# TYPE process_start_time_seconds gauge
process_start_time_seconds 1.632353901522E9
# HELP process_uptime_seconds The uptime of the Java virtual machine
# TYPE process_uptime_seconds gauge
process_uptime_seconds 81.245
# HELP jvm_threads_states_threads The current number of threads having NEW state
# TYPE jvm_threads_states_threads gauge
jvm_threads_states_threads{state="runnable",} 6.0
jvm_threads_states_threads{state="blocked",} 0.0
jvm_threads_states_threads{state="waiting",} 12.0
jvm_threads_states_threads{state="timed-waiting",} 3.0
jvm_threads_states_threads{state="new",} 0.0
jvm_threads_states_threads{state="terminated",} 0.0
# HELP jvm_threads_peak_threads The peak live thread count since the Java virtual machine started or peak was reset
# TYPE jvm_threads_peak_threads gauge
jvm_threads_peak_threads 21.0
```

[performance](perf/perf.md)
----


Also see
--

- https://github.com/parayaluyanta/eccount-rest-nio (`~6.978ms/request`)
- https://github.com/lamatola-os/chat-server_reactive-spring (`~22.860ms/request`)
- https://github.com/lamatola-os/akka-http-benchmark (`~1.172ms/request`)
- https://github.com/lamatola-os/netty-microservice (`~1.280ms/request`)
- https://github.com/prayagupd/nodejs-microservice
