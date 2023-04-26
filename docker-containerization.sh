container_id=$(docker ps -qa --filter ancestor=retailstore-microservice)
if [ $container_id ]; then
  docker stop $container_id
  docker rm $container_id
  docker rmi $(docker images 'retailstore-microservice' -a -q)
fi

./gradlew build

docker build -t retailstore-microservice .
docker run -it -p 8080:8080 --cpus=1 --memory=768m retailstore-microservice

container_id=$(docker ps -qa --filter ancestor=retailstore-microservice)
if [ $container_id ]; then
  docker stop $container_id
  docker rm $container_id
  docker rmi $(docker images 'retailstore-microservice' -a -q)
fi
