
- `~109ms` with endpoint having `100ms` blocking code

```java
    @RequestMapping("/health-sync")
    public HealthStatus healthSync() {

        logger.info("sync healthcheck");

        eccountService.readDataBlocking(100);

        return new HealthStatus(
                System.currentTimeMillis(),
                serviceName,
                serviceVersion
        );
    }
```

```bash
ab -n 10000 -c 100 -k http://127.0.0.1:8080/health-sync
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
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

Document Path:          /health-sync
Document Length:        83 bytes

Concurrency Level:      100
Time taken for tests:   10.975 seconds
Complete requests:      10000
Failed requests:        0
Keep-Alive requests:    0
Total transferred:      1880000 bytes
HTML transferred:       830000 bytes
Requests per second:    911.18 [#/sec] (mean)
Time per request:       109.748 [ms] (mean)
Time per request:       1.097 [ms] (mean, across all concurrent requests)
Transfer rate:          167.29 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   0.5      0       6
Processing:   101  106   6.8    105     253
Waiting:      100  106   5.3    105     245
Total:        101  107   6.9    106     253
ERROR: The median and mean for the initial connection time are more than twice the standard
       deviation apart. These results are NOT reliable.

Percentage of the requests served within a certain time (ms)
  50%    106
  66%    107
  75%    107
  80%    108
  90%    109
  95%    111
  98%    129
  99%    144
 100%    253 (longest request)

```