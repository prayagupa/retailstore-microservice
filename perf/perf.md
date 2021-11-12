performance
-----------

This is tested
1) with the endpoint involving no IO
2) whatever thread pool size and queue Spring configures

1 - 100K requests
-----------------

**on AWS VM backed by nginx, `c5.2xlarge`**

```
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/instance-type=c5.2xlarge
                    beta.kubernetes.io/os=linux
Capacity:
 attachable-volumes-aws-ebs:  25
 cpu:                         8
 ephemeral-storage:           20959212Ki
 hugepages-1Gi:               0
 hugepages-2Mi:               0
 memory:                      15835156Ki
 pods:                        58
Allocatable:
 attachable-volumes-aws-ebs:  25
 cpu:                         8
 ephemeral-storage:           19316009748
 hugepages-1Gi:               0
 hugepages-2Mi:               0
 memory:                      15732756Ki
 pods:                        58

###
ab -n 100000 -c 100 -k abc-xyz.us-east-1.elb.amazonaws.com/svc1/health
This is ApacheBench, Version 2.3 <$Revision: 1826891 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking abc-xyz.us-east-1.elb.amazonaws.com (be patient)
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


Server Software:        openresty/1.15.8.1
Server Hostname:        abc-xyz.us-east-1.elb.amazonaws.com
Server Port:            80

Document Path:          /svc1/health
Document Length:        83 bytes

Concurrency Level:      100
Time taken for tests:   115.227 seconds
Complete requests:      100000
Failed requests:        0
Keep-Alive requests:    93392
Total transferred:      27634800 bytes
HTML transferred:       8300000 bytes
Requests per second:    867.85 [#/sec] (mean)
Time per request:       115.227 [ms] (mean)
Time per request:       1.152 [ms] (mean, across all concurrent requests)
Transfer rate:          234.21 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    7  31.8      0    1285
Processing:    85  108  22.9    104    1461
Waiting:       85  107  22.7    103    1461
Total:         85  115  41.5    104    1486

Percentage of the requests served within a certain time (ms)
  50%    104
  66%    108
  75%    111
  80%    115
  90%    137
  95%    195
  98%    218
  99%    273
 100%   1486 (longest request)
```

2 - 50K requests
----------------

on AWS VM, 

```bash
ab -n 50000 -c 100 -k abc-xyz.us-east-1.elb.amazonaws.com/svc1/health
This is ApacheBench, Version 2.3 <$Revision: 1826891 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking abc-xyz.us-east-1.elb.amazonaws.com (be patient)
Completed 5000 requests
Completed 10000 requests
Completed 15000 requests
Completed 20000 requests
Completed 25000 requests
Completed 30000 requests
Completed 35000 requests
Completed 40000 requests
Completed 45000 requests
Completed 50000 requests
Finished 50000 requests


Server Software:        openresty/1.15.8.1
Server Hostname:        abc-xyz.us-east-1.elb.amazonaws.com
Server Port:            80

Document Path:          /svc1/health
Document Length:        83 bytes

Concurrency Level:      100
Time taken for tests:   56.142 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    47181
Total transferred:      13829525 bytes
HTML transferred:       4150000 bytes
Requests per second:    890.59 [#/sec] (mean)
Time per request:       112.285 [ms] (mean)
Time per request:       1.123 [ms] (mean, across all concurrent requests)
Transfer rate:          240.56 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    6  24.1      0     667
Processing:    86  106  18.7    103     607
Waiting:       86  106  18.4    102     499
Total:         86  112  30.7    103     768

Percentage of the requests served within a certain time (ms)
  50%    103
  66%    107
  75%    110
  80%    113
  90%    130
  95%    190
  98%    206
  99%    226
 100%    768 (longest request)
```


3 - 20K requests
----------------

**nginx backed**

```bash
ab -n 20000 -c 100 -k abc-xyz.us-east-1.elb.amazonaws.com/svc1/health
This is ApacheBench, Version 2.3 <$Revision: 1826891 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking abc-xyz.us-east-1.elb.amazonaws.com (be patient)
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


Server Software:        openresty/1.15.8.1
Server Hostname:        abc-xyz.us-east-1.elb.amazonaws.com
Server Port:            80

Document Path:          /svc1/health
Document Length:        83 bytes

Concurrency Level:      100
Time taken for tests:   23.005 seconds
Complete requests:      20000
Failed requests:        0
Keep-Alive requests:    18904
Total transferred:      5532600 bytes
HTML transferred:       1660000 bytes
Requests per second:    869.38 [#/sec] (mean)
Time per request:       115.025 [ms] (mean)
Time per request:       1.150 [ms] (mean, across all concurrent requests)
Transfer rate:          234.86 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    6  26.2      0     628
Processing:    86  108  30.9    103     763
Waiting:       86  107  30.6    102     763
Total:         86  114  40.9    103     763

Percentage of the requests served within a certain time (ms)
  50%    103
  66%    107
  75%    111
  80%    114
  90%    127
  95%    192
  98%    222
  99%    271
 100%    763 (longest request)
```

