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
$ ab -n 20000 -c 100 -k http://127.0.0.1:8080/health-benchmark
This is ApacheBench, Version 2.3 <$Revision: 1807734 $>
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
apr_socket_recv: Operation timed out (60)
Total of 16465 requests completed
```

**localhost 50K**

```
$ ab -n 50000 -c 100 -k http://127.0.0.1:8080/health
This is ApacheBench, Version 2.3 <$Revision: 1807734 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 5000 requests
Completed 10000 requests
Completed 15000 requests
apr_socket_recv: Operation timed out (60)
Total of 16474 requests completed
```

**on localhost 100K**

```
$ ab -n 100000 -c 100 -k http://127.0.0.1:8080/health
This is ApacheBench, Version 2.3 <$Revision: 1807734 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 10000 requests
apr_socket_recv: Operation timed out (60)
Total of 16441 requests completed
```
