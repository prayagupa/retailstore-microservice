FROM tomcat:8.0.20-jre8

ENV spring.profiles.active=staging

COPY /target/restapi.war /usr/local/tomcat/webapps/

EXPOSE 8080
