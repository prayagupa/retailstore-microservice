
Java HTTP/REST micro-service
==========================

![Retail store](https://github.com/prayagupa/retailstore-microservice/actions/workflows/cicd.yaml/badge.svg)


This is a micro-service implementation in java 17, spring-boot `2.7.x`

```
|                   |                          |
|                   |                          |
|    /endpoint      |      service             |
|        |          |                          |
|        v          |                          |
|     schema JAR    |                          |

```


- [unit tests](#unit-tests)
- [build/ run-app in x env](#Run-application)
- [Deployment + Load balancing](devops/README.md)
- [build artifact](#build-artifact)
- [performance](perf/perf_vm.md)

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

Verify the Endpoint
-------------------

```bash
curl -v -XGET http://localhost:8080/health | python -m json.tool
Note: Unnecessary use of -X or --request, GET is already inferred.
*   Trying 127.0.0.1:8080...
> GET /health HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.79.1
> Accept: */*
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Fri, 30 Dec 2022 21:55:50 GMT
< 
{ [94 bytes data]
* Connection #0 to host localhost left intact
{
    "timestamp": 1672433750,
    "applicationName": "retailstore-rest",
    "applicationVersion": "1.0"
}
```

Build
------

```bash
./gradlew clean build

upadhyay_lab $ ls -lh retailstore-rest-server/build/libs/
total 71488
-rw-r--r--  1 prayagupd  staff    13K Apr 11 07:08 retailstore-rest-server-1.0-SNAPSHOT-plain.jar
-rw-r--r--  1 prayagupd  staff    35M Apr 11 07:08 retailstore-rest-server-1.0-SNAPSHOT.jar

## 
upadhyay_lab $ jar -tf retailstore-rest-server/build/libs/retailstore-rest-server-1.0-SNAPSHOT.jar
META-INF/
META-INF/MANIFEST.MF
org/
org/springframework/
org/springframework/boot/
org/springframework/boot/loader/
org/springframework/boot/loader/ClassPathIndexFile.class
org/springframework/boot/loader/ExecutableArchiveLauncher.class
org/springframework/boot/loader/JarLauncher.class
org/springframework/boot/loader/LaunchedURLClassLoader$DefinePackageCallType.class
org/springframework/boot/loader/LaunchedURLClassLoader$UseFastConnectionExceptionsEnumeration.class
org/springframework/boot/loader/LaunchedURLClassLoader.class
org/springframework/boot/loader/Launcher.class
org/springframework/boot/loader/MainMethodRunner.class
org/springframework/boot/loader/PropertiesLauncher$1.class
org/springframework/boot/loader/PropertiesLauncher$ArchiveEntryFilter.class
org/springframework/boot/loader/PropertiesLauncher$ClassPathArchives.class
org/springframework/boot/loader/PropertiesLauncher$PrefixMatchingArchiveFilter.class
org/springframework/boot/loader/PropertiesLauncher.class
org/springframework/boot/loader/WarLauncher.class
org/springframework/boot/loader/archive/
org/springframework/boot/loader/archive/Archive$Entry.class
org/springframework/boot/loader/archive/Archive$EntryFilter.class
org/springframework/boot/loader/archive/Archive.class
org/springframework/boot/loader/archive/ExplodedArchive$AbstractIterator.class
org/springframework/boot/loader/archive/ExplodedArchive$ArchiveIterator.class
org/springframework/boot/loader/archive/ExplodedArchive$EntryIterator.class
org/springframework/boot/loader/archive/ExplodedArchive$FileEntry.class
org/springframework/boot/loader/archive/ExplodedArchive$SimpleJarFileArchive.class
org/springframework/boot/loader/archive/ExplodedArchive.class
org/springframework/boot/loader/archive/JarFileArchive$AbstractIterator.class
org/springframework/boot/loader/archive/JarFileArchive$EntryIterator.class
org/springframework/boot/loader/archive/JarFileArchive$JarFileEntry.class
org/springframework/boot/loader/archive/JarFileArchive$NestedArchiveIterator.class
org/springframework/boot/loader/archive/JarFileArchive.class
org/springframework/boot/loader/data/
org/springframework/boot/loader/data/RandomAccessData.class
org/springframework/boot/loader/data/RandomAccessDataFile$1.class
org/springframework/boot/loader/data/RandomAccessDataFile$DataInputStream.class
org/springframework/boot/loader/data/RandomAccessDataFile$FileAccess.class
org/springframework/boot/loader/data/RandomAccessDataFile.class
org/springframework/boot/loader/jar/
org/springframework/boot/loader/jar/AbstractJarFile$JarFileType.class
org/springframework/boot/loader/jar/AbstractJarFile.class
org/springframework/boot/loader/jar/AsciiBytes.class
org/springframework/boot/loader/jar/Bytes.class
org/springframework/boot/loader/jar/CentralDirectoryEndRecord$1.class
org/springframework/boot/loader/jar/CentralDirectoryEndRecord$Zip64End.class
org/springframework/boot/loader/jar/CentralDirectoryEndRecord$Zip64Locator.class
org/springframework/boot/loader/jar/CentralDirectoryEndRecord.class
org/springframework/boot/loader/jar/CentralDirectoryFileHeader.class
org/springframework/boot/loader/jar/CentralDirectoryParser.class
org/springframework/boot/loader/jar/CentralDirectoryVisitor.class
org/springframework/boot/loader/jar/FileHeader.class
org/springframework/boot/loader/jar/Handler.class
org/springframework/boot/loader/jar/JarEntry.class
org/springframework/boot/loader/jar/JarEntryCertification.class
org/springframework/boot/loader/jar/JarEntryFilter.class
org/springframework/boot/loader/jar/JarFile$1.class
org/springframework/boot/loader/jar/JarFile$JarEntryEnumeration.class
org/springframework/boot/loader/jar/JarFile.class
org/springframework/boot/loader/jar/JarFileEntries$1.class
org/springframework/boot/loader/jar/JarFileEntries$EntryIterator.class
org/springframework/boot/loader/jar/JarFileEntries$Offsets.class
org/springframework/boot/loader/jar/JarFileEntries$Zip64Offsets.class
org/springframework/boot/loader/jar/JarFileEntries$ZipOffsets.class
org/springframework/boot/loader/jar/JarFileEntries.class
org/springframework/boot/loader/jar/JarFileWrapper.class
org/springframework/boot/loader/jar/JarURLConnection$1.class
org/springframework/boot/loader/jar/JarURLConnection$JarEntryName.class
org/springframework/boot/loader/jar/JarURLConnection.class
org/springframework/boot/loader/jar/StringSequence.class
org/springframework/boot/loader/jar/ZipInflaterInputStream.class
org/springframework/boot/loader/jarmode/
org/springframework/boot/loader/jarmode/JarMode.class
org/springframework/boot/loader/jarmode/JarModeLauncher.class
org/springframework/boot/loader/jarmode/TestJarMode.class
org/springframework/boot/loader/util/
org/springframework/boot/loader/util/SystemPropertyUtils.class
BOOT-INF/
BOOT-INF/classes/
BOOT-INF/classes/com/
BOOT-INF/classes/com/api/
BOOT-INF/classes/com/api/MicroserviceApplication.class
BOOT-INF/classes/com/api/rest/
BOOT-INF/classes/com/api/rest/config/
BOOT-INF/classes/com/api/rest/config/NettyServerConfig.class
BOOT-INF/classes/com/api/rest/config/NettyWebServer.class
BOOT-INF/classes/com/api/rest/endpoints/
BOOT-INF/classes/com/api/rest/endpoints/ServiceController.class
BOOT-INF/classes/com/api/rest/schema/
BOOT-INF/classes/com/api/rest/schema/ServiceBuildInfo.class
BOOT-INF/classes/com/api/rest/service/
BOOT-INF/classes/com/api/rest/service/RetailService.class
BOOT-INF/classes/application-dev.properties
BOOT-INF/classes/application-production.properties
BOOT-INF/classes/application-staging.properties
BOOT-INF/classes/application.properties
BOOT-INF/lib/
BOOT-INF/lib/retailstore-rest-schema-1.0-SNAPSHOT.jar
BOOT-INF/lib/lombok-1.18.22.jar
BOOT-INF/lib/resilience4j-spring-boot2-1.7.0.jar
BOOT-INF/lib/HikariCP-3.3.1.jar
BOOT-INF/lib/mysql-connector-java-8.0.17.jar
BOOT-INF/lib/log4j-core-2.8.2.jar
BOOT-INF/lib/log4j-slf4j-impl-2.8.2.jar
BOOT-INF/lib/log4j-jul-2.17.2.jar
BOOT-INF/lib/log4j-api-2.8.2.jar
BOOT-INF/lib/spring-boot-actuator-autoconfigure-2.7.10.jar
BOOT-INF/lib/jackson-datatype-jsr310-2.13.5.jar
BOOT-INF/lib/jackson-module-parameter-names-2.13.5.jar
BOOT-INF/lib/jackson-databind-2.13.4.jar
BOOT-INF/lib/jackson-annotations-2.13.5.jar
BOOT-INF/lib/jackson-core-2.13.4.jar
BOOT-INF/lib/jackson-datatype-jdk8-2.13.4.jar
BOOT-INF/lib/reactor-netty-0.9.6.RELEASE.jar
BOOT-INF/lib/micrometer-registry-prometheus-1.10.6.jar
BOOT-INF/lib/micrometer-core-1.10.6.jar
BOOT-INF/lib/spring-aspects-5.3.19.jar
BOOT-INF/lib/resilience4j-spring-1.7.0.jar
BOOT-INF/lib/resilience4j-micrometer-1.7.0.jar
BOOT-INF/lib/resilience4j-annotations-1.7.0.jar
BOOT-INF/lib/resilience4j-consumer-1.7.0.jar
BOOT-INF/lib/resilience4j-framework-common-1.7.0.jar
BOOT-INF/lib/resilience4j-circuitbreaker-1.7.0.jar
BOOT-INF/lib/resilience4j-ratelimiter-1.7.0.jar
BOOT-INF/lib/resilience4j-retry-1.7.0.jar
BOOT-INF/lib/resilience4j-bulkhead-1.7.0.jar
BOOT-INF/lib/resilience4j-timelimiter-1.7.0.jar
BOOT-INF/lib/resilience4j-core-1.7.0.jar
BOOT-INF/lib/resilience4j-circularbuffer-1.7.0.jar
BOOT-INF/lib/vavr-0.10.2.jar
BOOT-INF/lib/jul-to-slf4j-1.7.36.jar
BOOT-INF/lib/slf4j-api-1.7.36.jar
BOOT-INF/lib/protobuf-java-3.6.1.jar
BOOT-INF/lib/netty-codec-http2-4.1.90.Final.jar
BOOT-INF/lib/netty-handler-proxy-4.1.90.Final.jar
BOOT-INF/lib/netty-codec-http-4.1.90.Final.jar
BOOT-INF/lib/netty-handler-4.1.90.Final.jar
BOOT-INF/lib/netty-transport-native-epoll-4.1.90.Final-linux-x86_64.jar
BOOT-INF/lib/reactor-core-3.4.28.jar
BOOT-INF/lib/micrometer-observation-1.10.6.jar
BOOT-INF/lib/micrometer-commons-1.10.6.jar
BOOT-INF/lib/HdrHistogram-2.1.12.jar
BOOT-INF/lib/LatencyUtils-2.0.3.jar
BOOT-INF/lib/simpleclient_common-0.15.0.jar
BOOT-INF/lib/spring-webmvc-5.3.26.jar
BOOT-INF/lib/spring-web-5.3.26.jar
BOOT-INF/lib/aspectjweaver-1.9.7.jar
BOOT-INF/lib/vavr-match-0.10.2.jar
BOOT-INF/lib/netty-codec-socks-4.1.90.Final.jar
BOOT-INF/lib/netty-codec-4.1.90.Final.jar
BOOT-INF/lib/netty-transport-classes-epoll-4.1.90.Final.jar
BOOT-INF/lib/netty-transport-native-unix-common-4.1.90.Final.jar
BOOT-INF/lib/netty-transport-4.1.90.Final.jar
BOOT-INF/lib/netty-buffer-4.1.90.Final.jar
BOOT-INF/lib/netty-resolver-4.1.90.Final.jar
BOOT-INF/lib/netty-common-4.1.90.Final.jar
BOOT-INF/lib/reactive-streams-1.0.4.jar
BOOT-INF/lib/simpleclient-0.15.0.jar
BOOT-INF/lib/spring-boot-autoconfigure-2.7.10.jar
BOOT-INF/lib/spring-boot-actuator-2.7.10.jar
BOOT-INF/lib/spring-boot-2.7.10.jar
BOOT-INF/lib/jakarta.annotation-api-1.3.5.jar
BOOT-INF/lib/spring-context-5.3.26.jar
BOOT-INF/lib/spring-expression-5.3.26.jar
BOOT-INF/lib/spring-aop-5.3.26.jar
BOOT-INF/lib/spring-beans-5.3.26.jar
BOOT-INF/lib/spring-core-5.3.26.jar
BOOT-INF/lib/snakeyaml-1.30.jar
BOOT-INF/lib/tomcat-embed-websocket-9.0.73.jar
BOOT-INF/lib/tomcat-embed-core-9.0.73.jar
BOOT-INF/lib/tomcat-embed-el-9.0.73.jar
BOOT-INF/lib/simpleclient_tracer_otel-0.15.0.jar
BOOT-INF/lib/simpleclient_tracer_otel_agent-0.15.0.jar
BOOT-INF/lib/spring-jcl-5.3.26.jar
BOOT-INF/lib/simpleclient_tracer_common-0.15.0.jar
BOOT-INF/lib/spring-boot-jarmode-layertools-2.7.10.jar
BOOT-INF/classpath.idx
BOOT-INF/layers.idx
```

Using maven, 
```bash
upadhyay_lab $ ls -lh retailstore-rest-server/target/retailstore-rest.jar
-rwxr--r--  1 prayagupd  staff    28M Apr 11 07:56 retailstore-rest-server/target/retailstore-rest.jar
```

Service uses base container image - https://github.com/lamatola-os/java-microservice-base-image

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

Monitoring
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


Also see
--

- https://github.com/parayaluyanta/retailstore-microservice-nio (`~6.978ms/request`)
- https://github.com/lamatola-os/chat-server_reactive-spring (`~22.860ms/request`)
- https://github.com/lamatola-os/akka-http-benchmark (`~1.172ms/request`)
- https://github.com/lamatola-os/netty-microservice (`~1.280ms/request`)
- https://github.com/prayagupd/nodejs-microservice
