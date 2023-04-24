## ab recognises 127.0.0.1 on docker 
ab -n 10000 -c 10 127.0.0.1:8080/health-benchmark
