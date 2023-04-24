./gradlew build
docker build -t retailstore-microservice .
docker run -it -p 8080:8080 --cpus=1 --memory=768m retailstore-microservice
docker rm --force $(docker images 'retailstore-microservice' -a -q)
docker rmi --force $(docker images 'retailstore-microservice' -a -q)
