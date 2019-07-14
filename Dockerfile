FROM centos

ENV APP_ENVIRONMENT=dev
ENV spring.profiles.active=dev
ENV JAVA_HOME=/opt/jdk-12.0.1
ENV PATH=$PATH:$JAVA_HOME/bin

RUN curl -O https://download.java.net/java/GA/jdk12.0.1/69cfe15208a647278a19ef0990eea691/12/GPL/openjdk-12.0.1_linux-x64_bin.tar.gz
RUN tar xvf openjdk-12.0.1_linux-x64_bin.tar.gz
RUN mv jdk-12.0.1 /opt/
RUN touch /etc/profile.d/jdk12.sh
RUN echo export JAVA_HOME=/opt/jdk-12.0.1 >> /etc/profile.d/jdk12.sh
RUN echo export PATH=\$PATH:\$JAVA_HOME/bin >> /etc/profile.d/jdk12.sh
RUN source /etc/profile.d/jdk12.sh

RUN mkdir -p /usr/local/app
COPY /target/restapi.jar /usr/local/app

EXPOSE 9090

CMD ["java", "-jar", "/usr/local/app/restapi.jar"]
