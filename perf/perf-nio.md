NIO config
----

```config
## https://tomcat.apache.org/tomcat-8.5-doc/config/executor.html#Standard_Implementation
server.tomcat.threads.max=8

## Request Queue
## https://github.com/spring-projects/spring-boot/blob/v3.0.5/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/ServerProperties.java#L426
server.tomcat.accept-count=10000
```

**10K @30% CPU**

```bash
ab -n 10000 -c 100 -k http://127.0.0.1:8080/health-benchmark-eventloopN
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

Document Path:          /health-benchmark-eventloopN
Document Length:        79 bytes

Concurrency Level:      100
Time taken for tests:   5.028 seconds
Complete requests:      10000
Failed requests:        0
Keep-Alive requests:    0
Total transferred:      1840000 bytes
HTML transferred:       790000 bytes
Requests per second:    1988.89 [#/sec] (mean)
Time per request:       50.279 [ms] (mean)
Time per request:       0.503 [ms] (mean, across all concurrent requests)
Transfer rate:          357.38 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   19  17.8     15     173
Processing:     7   30  19.4     25     171
Waiting:        7   29  18.9     25     171
Total:         18   49  25.1     44     212

Percentage of the requests served within a certain time (ms)
  50%     44
  66%     56
  75%     64
  80%     67
  90%     76
  95%     83
  98%    126
  99%    166
 100%    212 (longest request)
```


**15.5K**: 45% CPU

```bash
millionaire $ ab -n 15500 -c 100 -k http://127.0.0.1:8080/health-benchmark-eventloopN
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 1550 requests
Completed 3100 requests
Completed 4650 requests
Completed 6200 requests
Completed 7750 requests
Completed 9300 requests
Completed 10850 requests
Completed 12400 requests
Completed 13950 requests
Completed 15500 requests
Finished 15500 requests


Server Software:        
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /health-benchmark-eventloopN
Document Length:        79 bytes

Concurrency Level:      100
Time taken for tests:   19.954 seconds
Complete requests:      15500
Failed requests:        0
Keep-Alive requests:    0
Total transferred:      2852000 bytes
HTML transferred:       1224500 bytes
Requests per second:    776.79 [#/sec] (mean)
Time per request:       128.735 [ms] (mean)
Time per request:       1.287 [ms] (mean, across all concurrent requests)
Transfer rate:          139.58 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0  109 1366.8      0   19515
Processing:     0    3   8.9      1     478
Waiting:        0    3   8.7      1     478
Total:          1  112 1367.0      1   19524

Percentage of the requests served within a certain time (ms)
  50%      1
  66%      1
  75%      1
  80%      2
  90%     13
  95%     16
  98%     20
  99%     32
 100%  19524 (longest request)
 ```

![](perf-nio_2020.png)