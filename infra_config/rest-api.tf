provider "aws" {
  profile    = "upd-dev"
  region     = "us-east-1"
}

resource "aws_instance" "rest-api" {
  ami           = "ami-2757f631"
  instance_type = "t2.micro"
  key_name = "rest-api-dev-key"
  tags          = {
    Name        = "rest-api"
    Environment = "dev"
  }
}
