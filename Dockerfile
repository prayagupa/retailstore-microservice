FROM java-microservice-base-image:latest

ENV APP_ENVIRONMENT=dev
ENV spring.profiles.active=dev

RUN mkdir -p /usr/local/app
COPY eccount-rest-server/target/restapi.jar /usr/local/app

EXPOSE 9090

CMD ["java", "-jar", "/usr/local/app/restapi.jar"]
