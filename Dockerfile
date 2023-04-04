#FROM java-microservice-base-image:latest
FROM openjdk:latest

RUN mkdir -p /usr/local/app

RUN useradd retailuser -d /home/retailuser && chown -R retailuser.retailuser /usr/bin/ && chown -R retailuser.retailuser /usr/lib64/ && chown -R retailuser.retailuser /usr/lib/ && chown -R retailuser.retailuser /usr/local/ && chown -R retailuser.retailuser /var/log/

USER retailuser

COPY retailstore-rest-server/build/libs/retailstore-rest-server-1.0-SNAPSHOT.jar /usr/local/app/retailstore.jar

EXPOSE 8080
EXPOSE 9090

CMD ["java", "-jar", "/usr/local/app/retailstore.jar"]
