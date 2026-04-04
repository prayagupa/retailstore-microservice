## FROM java-microservice-base-image:latest
FROM eclipse-temurin:21-jre-alpine
## FROM openjdk:21
LABEL name=retailstore-microservice

RUN mkdir -p /usr/local/app

RUN addgroup -S retailuser && adduser -S -D -h /home/retailuser -G retailuser retailuser \
    && chown -R retailuser:retailuser /usr/bin/ \
    && chown -R retailuser:retailuser /usr/lib/ \
    && chown -R retailuser:retailuser /usr/local/ \
    && chown -R retailuser:retailuser /var/log/

USER retailuser

COPY retailstore-rest-server/build/libs/retailstore-rest-server-1.0-SNAPSHOT.jar /usr/local/app/retailstore.jar

EXPOSE 8080
EXPOSE 9090

CMD ["java", "-jar", "/usr/local/app/retailstore.jar"]
