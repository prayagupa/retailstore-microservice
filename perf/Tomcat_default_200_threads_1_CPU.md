
Tomcat Config
---

```config
## https://tomcat.apache.org/tomcat-8.5-doc/config/executor.html#Standard_Implementation
## https://www.baeldung.com/spring-boot-configure-tomcat
server.tomcat.threads.max=200

## Request Queue
## https://github.com/spring-projects/spring-boot/blob/v3.0.5/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/ServerProperties.java#L426
server.tomcat.accept-count=100

```

CPU/ Mem
----

- 1 CPU
- 0.75 Gb

**localhost 10K**

```
ab -n 10000 -c 100 -k http://127.0.0.1:8080/health-benchmark
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests


Server Software:        
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /health-benchmark
Document Length:        79 bytes

Concurrency Level:      100
Time taken for tests:   2.531 seconds
Complete requests:      10000
Failed requests:        0
Keep-Alive requests:    0
Total transferred:      1840000 bytes
HTML transferred:       790000 bytes
Requests per second:    3950.82 [#/sec] (mean)
Time per request:       25.311 [ms] (mean)
Time per request:       0.253 [ms] (mean, across all concurrent requests)
Transfer rate:          709.91 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   12  14.9      9     281
Processing:     5   13  23.2      9     282
Waiting:        0   12  22.7      8     281
Total:          8   25  29.8     18     309

Percentage of the requests served within a certain time (ms)
  50%     18
  66%     23
  75%     25
  80%     28
  90%     37
  95%     58
  98%     72
  99%     80
 100%    309 (longest request)
```


![](spring_perf_2019.png)

![](spring_perf_2020.png)

**localhost 20K**

```
ab -n 20000 -c 100 -k http://127.0.0.1:8080/health-benchmark
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 2000 requests
Completed 4000 requests
Completed 6000 requests
Completed 8000 requests
Completed 10000 requests
Completed 12000 requests
Completed 14000 requests
Completed 16000 requests
Completed 18000 requests
Completed 20000 requests
Finished 20000 requests


Server Software:        
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /health-benchmark
Document Length:        79 bytes

Concurrency Level:      100
Time taken for tests:   37.034 seconds
Complete requests:      20000
Failed requests:        0
Keep-Alive requests:    0
Total transferred:      3680000 bytes
HTML transferred:       1580000 bytes
Requests per second:    540.05 [#/sec] (mean)
Time per request:       185.168 [ms] (mean)
Time per request:       1.852 [ms] (mean, across all concurrent requests)
Transfer rate:          97.04 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   64 826.6      0   13342
Processing:     5  120  54.4    115     467
Waiting:        4  116  52.3    111     432
Total:          6  185 824.0    116   13439

Percentage of the requests served within a certain time (ms)
  50%    116
  66%    135
  75%    150
  80%    159
  90%    187
  95%    219
  98%    281
  99%    378
 100%  13439 (longest request)
```

**localhost 50K**

```
ab -n 20000 -c 100 -k http://127.0.0.1:8080/health-benchmark
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 2000 requests
Completed 4000 requests
Completed 6000 requests
Completed 8000 requests
Completed 10000 requests
Completed 12000 requests
Completed 14000 requests
Completed 16000 requests
Completed 18000 requests
Completed 20000 requests
Finished 20000 requests


Server Software:        
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /health-benchmark
Document Length:        79 bytes

Concurrency Level:      100
Time taken for tests:   37.034 seconds
Complete requests:      20000
Failed requests:        0
Keep-Alive requests:    0
Total transferred:      3680000 bytes
HTML transferred:       1580000 bytes
Requests per second:    540.05 [#/sec] (mean)
Time per request:       185.168 [ms] (mean)
Time per request:       1.852 [ms] (mean, across all concurrent requests)
Transfer rate:          97.04 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   64 826.6      0   13342
Processing:     5  120  54.4    115     467
Waiting:        4  116  52.3    111     432
Total:          6  185 824.0    116   13439

Percentage of the requests served within a certain time (ms)
  50%    116
  66%    135
  75%    150
  80%    159
  90%    187
  95%    219
  98%    281
  99%    378
 100%  13439 (longest request)
```

**on localhost 100K**

```
CONTAINER ID   NAME               CPU %     MEM USAGE / LIMIT     MEM %     NET I/O           BLOCK I/O        PIDS
3f322cd2eaeb   youthful_taussig   75.64%    203.1MiB / 768MiB     26.45%    66.5MB / 70.9MB   717kB / 1.02MB   112

ab -n 100000 -c 100 -k http://127.0.0.1:8080/health-benchmark
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 10000 requests
Completed 20000 requests
Completed 30000 requests
Completed 40000 requests
Completed 50000 requests
Completed 60000 requests
Completed 70000 requests
Completed 80000 requests
Completed 90000 requests
Completed 100000 requests
Finished 100000 requests


Server Software:        
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /health-benchmark
Document Length:        79 bytes

Concurrency Level:      100
Time taken for tests:   255.450 seconds
Complete requests:      100000
Failed requests:        0
Keep-Alive requests:    0
Total transferred:      18400000 bytes
HTML transferred:       7900000 bytes
Requests per second:    391.47 [#/sec] (mean)
Time per request:       255.450 [ms] (mean)
Time per request:       2.554 [ms] (mean, across all concurrent requests)
Transfer rate:          70.34 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   32 688.8      0   19263
Processing:     6  223 178.0    164    2556
Waiting:        5  215 172.7    158    1953
Total:          6  255 705.4    167   19606

Percentage of the requests served within a certain time (ms)
  50%    167
  66%    215
  75%    261
  80%    301
  90%    444
  95%    602
  98%    809
  99%   1029
 100%  19606 (longest request)
```
