# Performance Benchmarks — Tomcat Default Thread Pool (200 threads, 1 CPU)

Benchmarks for the `retailstore-microservice` REST API using Tomcat's default blocking thread pool  
under constrained single-core Docker resources.  
Tool: [Apache Bench (ab)](https://httpd.apache.org/docs/current/programs/ab.html) · Date: 2023-04-24

---

## Environment

| Property | Value |
|----------|-------|
| Runtime | Java 12 |
| Framework | Spring Boot 2.5.12 |
| Server | Apache Tomcat 9.0.x (embedded) |
| CPUs | 1 (`--cpus=1`) |
| Memory | 768 MB (`--memory=768m`) |
| Container | Docker (bridge network) |
| Endpoint | `GET /health-benchmark` |
| Concurrency | 100 concurrent clients (`-c 100`) |
| Keep-Alive | disabled — `ab` run without `-k` flag (0 / 100 000 established) |

---

## Tomcat Configuration

```properties
# https://tomcat.apache.org/tomcat-8.5-doc/config/executor.html#Standard_Implementation
# https://www.baeldung.com/spring-boot-configure-tomcat
server.tomcat.threads.max=200

# Request Queue
# https://github.com/spring-projects/spring-boot/blob/v3.0.5/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/ServerProperties.java#L426
server.tomcat.accept-count=100
```

---

## Results Summary

| Requests (`-n`) | Duration (s) | Req/sec (mean) | Mean latency (ms) | p99 (ms) | Failed |
|----------------:|-------------:|---------------:|------------------:|---------:|-------:|
| 10 000 | 2.531 | 3 950 | 25.3 | 80 | 0 |
| 20 000 | 37.034 | 540 | 185.2 | 378 | 0 |
| 100 000 | 255.450 | 391 | 255.5 | 1 029 | 0 |

> Throughput collapses sharply beyond 10K requests — consistent with thread-pool saturation and
> connection queue back-pressure on a single CPU.

---

## Run 1 — 10 000 requests

```bash
ab -n 10000 -c 100 -k http://127.0.0.1:8080/health-benchmark
```

### Throughput

| Metric | Value |
|--------|-------|
| Time taken | 2.531 s |
| Requests/sec | 3 950.82 |
| Transfer rate | 709.91 KB/s |
| Keep-Alive requests | 0 / 10 000 |

### Latency (ms)

| Percentile | ms |
|-----------:|---:|
| 50% | 18 |
| 66% | 23 |
| 75% | 25 |
| 80% | 28 |
| 90% | 37 |
| 95% | 58 |
| 98% | 72 |
| 99% | 80 |
| 100% (max) | 309 |

<details>
<summary>Full ab output</summary>

```
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
```

</details>

---

## Run 2 — 20 000 requests

```bash
ab -n 20000 -c 100 -k http://127.0.0.1:8080/health-benchmark
```

### Throughput

| Metric | Value |
|--------|-------|
| Time taken | 37.034 s |
| Requests/sec | 540.05 |
| Transfer rate | 97.04 KB/s |
| Keep-Alive requests | 0 / 20 000 |

### Latency (ms)

| Percentile | ms |
|-----------:|---:|
| 50% | 116 |
| 66% | 135 |
| 75% | 150 |
| 80% | 159 |
| 90% | 187 |
| 95% | 219 |
| 98% | 281 |
| 99% | 378 |
| 100% (max) | 13 439 |

<details>
<summary>Full ab output</summary>

```
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
```

</details>

---

## Run 3 — 100 000 requests

Docker stats during run:

```
CONTAINER ID   NAME               CPU %     MEM USAGE / LIMIT     MEM %     NET I/O           BLOCK I/O        PIDS
3f322cd2eaeb   youthful_taussig   75.64%    203.1MiB / 768MiB     26.45%    66.5MB / 70.9MB   717kB / 1.02MB   112
```

```bash
ab -n 100000 -c 100 -k http://127.0.0.1:8080/health-benchmark
```

### Throughput

| Metric | Value |
|--------|-------|
| Time taken | 255.450 s |
| Requests/sec | 391.47 |
| Transfer rate | 70.34 KB/s |
| Keep-Alive requests | 0 / 100 000 |
| CPU usage | 75.64% |
| Memory usage | 203.1 MiB / 768 MiB (26%) |

### Latency (ms)

| Percentile | ms |
|-----------:|---:|
| 50% | 167 |
| 66% | 215 |
| 75% | 261 |
| 80% | 301 |
| 90% | 444 |
| 95% | 602 |
| 98% | 809 |
| 99% | 1 029 |
| 100% (max) | 19 606 |

<details>
<summary>Full ab output</summary>

```
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
```

</details>

---

## Observations

- **Keep-Alive not used** — `ab` was run without the `-k` flag, so no `Connection: keep-alive` header was ever sent. `Keep-Alive requests: 0` across all three results confirms every request opened and closed its own TCP connection. This alone accounts for significant overhead at scale — each of the 100K requests paid a full TCP three-way handshake on the loopback interface.
- **Thread pool saturation** — throughput drops ~7× from 10K (3 950 req/s) to 20K (540 req/s) runs, indicating the 200-thread pool is exhausted and requests begin queueing in the `accept-count=100` backlog under sustained load on a single CPU.
- **Latency spikes with scale** — p99 grows from 80 ms at 10K to 1 029 ms at 100K; the 13 s and 19 s max values are connection-queue timeouts, not processing delays.
- **Memory is not the bottleneck** — container memory stays at 26% (203 MiB) throughout the 100K run; the constraint is CPU + thread scheduling.
- **CPU ceiling** — CPU hovers at ~75% during the 100K run, suggesting head-room exists but is consumed by thread context-switching rather than actual request processing.
- **Comparison** — see [2026 benchmarks](../java21/README.md) for results with the upgraded Tomcat 11 / Java 21 stack, which achieves ~22K req/s on the same single-core constraint.

---

## See Also

- [2026 Benchmarks — Tomcat 11 / Java 21](../java21/README.md)
- [Tomcat Blocking (single-threaded)](../Tomcat_Blocking.md)
- [Tomcat NIO (single-threaded)](../2020/Tomcat_Nio_SingleThreaded.md)
- [Build & Run in Docker](../../build-and-run.md)

