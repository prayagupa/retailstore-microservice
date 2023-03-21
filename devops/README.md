
install minikube
---

https://github.com/lamatola-os/lamatola-devops/wiki/Kubernetes-minikube


publish artifact/ container image
---------------------------

https://github.com/lamatola-os/lamatola-devops/wiki/Private-Container-Registry-(_CR)#push-a-container-image

k8s deployment
--------------

```bash
# kubectl apply -f k8s-nodes.yaml
kubectl create -f k8s-rest-cluster_ip-service.yaml --namespace dev
#kubectl delete service rest-server
```

```bash
λ kubectl get services --namespace dev
NAME          TYPE           CLUSTER-IP      EXTERNAL-IP                                                               PORT(S)        AGE
kubernetes    ClusterIP      10.100.0.1      <none>                                                                    443/TCP        32m
rest-server   LoadBalancer   10.100.214.66   a1d4b08e2a6a211e9888c1233f0bb1c4-1052742191.us-east-1.elb.amazonaws.com   80:30214/TCP   13m

## internal LB only accessible from k8s nodes 
λ kubectl get services --namespace dev
NAME          TYPE           CLUSTER-IP       EXTERNAL-IP                                                                        PORT(S)        AGE
rest-server   LoadBalancer   10.100.183.206   internal-afa3a1c3bb5b011e9ac3512ee202494e-2019297208.us-east-1.elb.amazonaws.com   80:31252/TCP   14s
```

deployment


```bash
kubectl create -f restserver-k8-deployment.yaml --namespace dev
#kubectl delete deployment rest-server

λ kubectl get deployments --output wide
NAME          READY     UP-TO-DATE   AVAILABLE   AGE       CONTAINERS    IMAGES                                                   SELECTOR
rest-server   1/1       1            1           16m       rest-server   ???.dkr.ecr.us-east-1.amazonaws.com/rest-server:latest   app=rest-server

minikube service rest-server #expose your Service outside of the cluster
```

```bash
curl -v a6e2953f6a6a411e9888c1233f0bb1c4-146709191.us-east-1.elb.amazonaws.com/health
*   Trying 52.3.214.190...
* TCP_NODELAY set
* Connected to a6e2953f6a6a411e9888c1233f0bb1c4-146709191.us-east-1.elb.amazonaws.com (52.3.214.190) port 80 (#0)
> GET /health HTTP/1.1
> Host: a6e2953f6a6a411e9888c1233f0bb1c4-146709191.us-east-1.elb.amazonaws.com
> User-Agent: curl/7.54.0
> Accept: */*
> 
< HTTP/1.1 200 
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Mon, 15 Jul 2019 02:05:32 GMT
< 
* Connection #0 to host a6e2953f6a6a411e9888c1233f0bb1c4-146709191.us-east-1.elb.amazonaws.com left intact
{"timestamp":1563156332187,"applicationName":"rest-api","applicationVersion":"1.0"}
```
