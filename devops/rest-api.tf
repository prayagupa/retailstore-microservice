provider "aws" {
  profile    = "dev"
  region     = "us-east-1"
}

resource "aws_instance" "rest-api" {
  ami           = "ami-098bb5d92c8886ca1"
  instance_type = "t2.micro"
  subnet_id = "subnet-026e5535bec195536"
  security_groups = ["sg-0cbda44f9bc418140"]
  key_name = "dev-key"
  tags          = {
    Name        = "rest-api"
    Environment = "dev"
  }
  provisioner "local-exec" {
      command = "echo hello"
  }
  provisioner "file" {
      source = "../target/restapi.jar"
      destination = "/home/ec2-user/restapi.jar"
      connection {
        agent       = false
        type        = "ssh"
        user        = "ec2-user"
        private_key = "${file("~/.ssh/rest-api-dev-key.pem")}"
        host = "${aws_instance.rest-api.public_ip}"
      }
  }
  provisioner "file" {
      source = "setup.sh"
      destination = "/home/ec2-user/setup.sh"
      connection {
        agent       = false
        type        = "ssh"
        user        = "ec2-user"
        private_key = "${file("~/.ssh/rest-api-dev-key.pem")}"
        host = "${aws_instance.rest-api.public_ip}"
      }
  }

  provisioner "remote-exec" {
      inline = [
          "chmod +x /home/ec2-user/setup.sh",
          "/home/ec2-user/setup.sh",
          "source /etc/profile.d/jdk12.sh && java -jar /home/ec2-user/restapi.jar"
      ]
      connection {
        agent       = false
        type        = "ssh"
        user        = "ec2-user"
        private_key = "${file("~/.ssh/rest-api-dev-key.pem")}"
        host = "${aws_instance.rest-api.public_ip}"
      }
  }

}
