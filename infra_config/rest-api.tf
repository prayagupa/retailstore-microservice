provider "aws" {
  profile    = "upd-dev"
  region     = "us-east-1"
}

resource "aws_instance" "rest-api" {
  ami           = "ami-098bb5d92c8886ca1"
  instance_type = "t2.micro"
  subnet_id = "subnet-026e5535bec195536"
  security_groups = ["sg-0cbda44f9bc418140"]
  key_name = "rest-api-dev-key"
  tags          = {
    Name        = "rest-api"
    Environment = "dev"
  }
}
