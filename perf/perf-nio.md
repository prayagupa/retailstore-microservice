NIO config
----

```config
## https://tomcat.apache.org/tomcat-8.5-doc/config/executor.html#Standard_Implementation
server.tomcat.threads.max=8

## Request Queue
## https://github.com/spring-projects/spring-boot/blob/v3.0.5/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/ServerProperties.java#L426
server.tomcat.accept-count=10000
```

**50K @30% CPU**

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


**100K**: 60% CPU

```bash
ab -n 100000 -c 100 -k http://127.0.0.1:8080/health-benchmark-eventloop
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

Document Path:          /health-benchmark-eventloop
Document Length:        0 bytes

Concurrency Level:      100
Time taken for tests:   3.777 seconds
Complete requests:      100000
Failed requests:        0
Keep-Alive requests:    99047
Total transferred:      12072363 bytes
HTML transferred:       0 bytes
Requests per second:    26475.82 [#/sec] (mean)
Time per request:       3.777 [ms] (mean)
Time per request:       0.038 [ms] (mean, across all concurrent requests)
Transfer rate:          3121.35 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.1      0       5
Processing:     0    4   1.2      4      28
Waiting:        0    4   1.2      4      28
Total:          0    4   1.2      4      28

Percentage of the requests served within a certain time (ms)
  50%      4
  66%      4
  75%      4
  80%      4
  90%      5
  95%      6
  98%      7
  99%      7
 100%     28 (longest request)
 ```

**500k** : 70%  CPU

```bash
ab -n 500000 -c 100 -k http://127.0.0.1:8080/health-benchmark-eventloop
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 50000 requests
Completed 100000 requests
Completed 150000 requests
Completed 200000 requests
Completed 250000 requests
Completed 300000 requests
Completed 350000 requests
Completed 400000 requests
Completed 450000 requests
Completed 500000 requests
Finished 500000 requests


Server Software:        
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /health-benchmark-eventloop
Document Length:        0 bytes

Concurrency Level:      100
Time taken for tests:   16.879 seconds
Complete requests:      500000
Failed requests:        0
Keep-Alive requests:    495048
Total transferred:      60356392 bytes
HTML transferred:       0 bytes
Requests per second:    29622.62 [#/sec] (mean)
Time per request:       3.376 [ms] (mean)
Time per request:       0.034 [ms] (mean, across all concurrent requests)
Transfer rate:          3492.02 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.1      0      13
Processing:     0    3   2.1      3     143
Waiting:        0    3   2.1      3     143
Total:          0    3   2.1      3     143

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      3
  75%      3
  80%      4
  90%      4
  95%      5
  98%      7
  99%      9
 100%    143 (longest request)
```

**1M @ 75% CPU**

```bash
ab -n 500000 -c 100 -k http://127.0.0.1:8080/health-benchmark-eventloop
millionaire $ ab -n 1000000 -c 100 -k http://127.0.0.1:8080/health-benchmark-eventloop
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 100000 requests
Completed 200000 requests
Completed 300000 requests
Completed 400000 requests
Completed 500000 requests
Completed 600000 requests
Completed 700000 requests
Completed 800000 requests
Completed 900000 requests
Completed 1000000 requests
Finished 1000000 requests


Server Software:        
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /health-benchmark-eventloop
Document Length:        0 bytes

Concurrency Level:      100
Time taken for tests:   32.269 seconds
Complete requests:      1000000
Failed requests:        0
Keep-Alive requests:    990045
Total transferred:      120711305 bytes
HTML transferred:       0 bytes
Requests per second:    30989.97 [#/sec] (mean)
Time per request:       3.227 [ms] (mean)
Time per request:       0.032 [ms] (mean, across all concurrent requests)
Transfer rate:          3653.16 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.0      0       7
Processing:     0    3   1.4      3      83
Waiting:        0    3   1.4      3      83
Total:          0    3   1.4      3      83

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      3
  75%      3
  80%      4
  90%      4
  95%      5
  98%      6
  99%      7
 100%     83 (longest request)
 ```

![](perf-nio_2020.png)