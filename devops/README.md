
[install minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)
---

```
$ sysctl -a | grep -E --color 'machdep.cpu.features|VMX' 
machdep.cpu.features: FPU VME DE PSE TSC MSR PAE MCE CX8 APIC SEP MTRR PGE MCA CMOV PAT PSE36 CLFSH DS ACPI MMX FXSR SSE SSE2 SS HTT TM PBE SSE3 PCLMULQDQ DTES64 MON DSCPL VMX EST TM2 SSSE3 FMA CX16 TPR PDCM SSE4.1 SSE4.2 x2APIC MOVBE POPCNT AES PCID XSAVE OSXSAVE SEGLIM64 TSCTMR AVX1.0 RDRAND F16C

brew install minikube

minikube start --vm-driver=docker
minikube start --vm-driver=hyperkit
😄  minikube v1.7.3 on Darwin 10.15.3
✨  Using the hyperkit driver based on user configuration
⌛  Reconfiguring existing host ...
🏃  Using the running hyperkit "minikube" VM ...
🐳  Preparing Kubernetes v1.17.3 on Docker 19.03.6 ...
🚀  Launching Kubernetes ... 
🌟  Enabling addons: default-storageclass, storage-provisioner
```


```bash
terraform init
terraform apply

```

once applied, 

```bash
terraform show
# aws_instance.rest-api:
resource "aws_instance" "rest-api" {
    ami                          = "ami-2757f631"
    arn                          = "arn:aws:ec2:us-east-1:???:instance/i-07d4cbda6cf167177"
    associate_public_ip_address  = true
    availability_zone            = "us-east-1d"
    cpu_core_count               = 1
    cpu_threads_per_core         = 1
    disable_api_termination      = false
    ebs_optimized                = false
    get_password_data            = false
    id                           = "i-07d4cbda6cf167177"
    instance_state               = "running"
    instance_type                = "t2.micro"
    ipv6_address_count           = 0
    ipv6_addresses               = []
    monitoring                   = false
    primary_network_interface_id = "eni-08f641e076821a75f"
    private_dns                  = "ip-172-31-33-39.ec2.internal"
    private_ip                   = "172.31.33.39"
    public_dns                   = "ec2-3-85-132-181.compute-1.amazonaws.com"
    public_ip                    = "3.85.132.181"
    security_groups              = [
        "default",
    ]
    source_dest_check            = true
    subnet_id                    = "subnet-8becbcd7"
    tenancy                      = "default"
    volume_tags                  = {}
    vpc_security_group_ids       = [
        "sg-d35bb589",
    ]

    credit_specification {
        cpu_credits = "standard"
    }

    root_block_device {
        delete_on_termination = true
        iops                  = 100
        volume_id             = "vol-0516bf00e1179fc21"
        volume_size           = 8
        volume_type           = "gp2"
    }
}
```

after changing any configuration, do `terraform apply`

state
-----

```
λ terraform state list
aws_instance.rest-api
```

provisioning
-----------

```bash
ansible --version
ansible 2.7.8
  config file = None
  configured module search path = [u'/Users/a1353612/.ansible/plugins/modules', u'/usr/share/ansible/plugins/modules']
  ansible python module location = /usr/local/lib/python2.7/site-packages/ansible
  executable location = /usr/local/bin/ansible
  python version = 2.7.15 (default, Dec 27 2018, 11:56:32) [GCC 4.2.1 Compatible Apple LLVM 10.0.0 (clang-1000.11.45.5)]

ansible-playbook rest-api-provisioning.yml 
```


k8s
----

set `~/.kube/duwamish-dev-k8s-config` and [export as `KUBECONFIG`](https://kubernetes.io/docs/tasks/access-application-cluster/configure-access-multiple-clusters/#set-the-kubeconfig-environment-variable), 

```bash
apiVersion: v1
clusters:
- cluster:
    server: https://???.sk1.us-east-1.eks.amazonaws.com
    certificate-authority-data: ???

  name: kubernetes
contexts:
- context:
    cluster: kubernetes
    user: aws
  name: aws
current-context: aws
kind: Config
preferences: {}
users:
- name: aws
  user:
    exec:
      apiVersion: client.authentication.k8s.io/v1alpha1
      command: aws-iam-authenticator
      args:
        - "token"
        - "-i"
        - "duwamish-dev-k8s"
      env:
        - name: AWS_PROFILE
          value: "duwamish-dev"
```

create cluster with nodes
--

```
brew tap weaveworks/tap
brew install weaveworks/tap/eksctl
brew upgrade eksctl && brew link --overwrite eksctl

eksctl create cluster \
--name duwamish-dev-k8s \
--version 1.13 \
--nodegroup-name standard-workers \
--node-type t3.medium \
--nodes 3 \
--nodes-min 1 \
--nodes-max 4 \
--node-ami auto \
--profile duwamish-dev \
--region us-east-1
```

create namespace
-----------------

```
λ kubectl create namespace dev
namespace/dev created
```

set cluster environment prop
----------------------------

```bash
kubectl create configmap cluster-env-config --from-env-file=k8s-cluster_nodes.properties --namespace dev
configmap/cluster-env-config created
```

https://github.com/redhat-developer-demos/spring-boot-configmaps-demo

```bash
gradle build

# instead of pushing your Docker image to a registry, 
# you can simply build the image using the same Docker host as the Minikube VM
eval $(minikube docker-env) 

docker build -t rest-server:v1 .
#docker run -it --rm -p 9090:8080 rest-server:v1
```

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


stop cluster

```
minikube stop
✋  Stopping "minikube" in hyperkit ...
🛑  "minikube" stopped.
```

debugging
---------

```bash
kubectl get nodes
No resources found.

λ kubectl get events
LAST SEEN   TYPE      REASON                       KIND         MESSAGE
24s         Warning   FailedScheduling             Pod          no nodes available to schedule pods
21m         Normal    SuccessfulCreate             ReplicaSet   Created pod: rest-server-7b4bf5bcf-lrnnl
21m         Normal    EnsuringLoadBalancer         Service      Ensuring load balancer
21m         Warning   UnAvailableLoadBalancer      Service      There are no available nodes for LoadBalancer service default/rest-server
22m         Warning   CreatingLoadBalancerFailed   Service      Error creating load balancer (will retry): failed to ensure load balancer for service default/rest-server: AccessDenied: User: arn:aws:sts::???:assumed-role/???-dev-role/??? is not authorized to perform: ec2:DescribeAccountAttributes
            status code: 403, request id: c3e2c253-a5f1-11e9-9119-8b95ed7811a7
22m         Warning   CreatingLoadBalancerFailed   Service   Error creating load balancer (will retry): failed to ensure load balancer for service default/rest-server: AccessDenied: User: arn:aws:sts::??:assumed-role/???-dev-role/??? is not authorized to perform: ec2:DescribeAccountAttributes
            status code: 403, request id: c7326293-a5f1-11e9-bac1-03384bbdb957
21m         Normal    EnsuredLoadBalancer   Service      Ensured load balancer
21m         Normal    ScalingReplicaSet     Deployment   Scaled up replica set rest-server-7b4bf5bcf to 1

kubectl describe pod rest-server-7b4bf5bcf-lrnnl
```
