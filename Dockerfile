FROM java-microservice-base-image:latest

RUN mkdir -p /usr/local/app
COPY eccount-rest-server/build/libs/eccount*.jar /usr/local/app/restapi.jar

EXPOSE 8080
EXPOSE 9090

CMD ["java", "-jar", "/usr/local/app/restapi.jar"]
