
```
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
