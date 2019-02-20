FROM openjdk:13-ea-7-jdk-alpine3.9
RUN apk --no-cache add curl
ENV VERSION 1.0.0
COPY target/github-repository-connector*.jar github-repository-connector.jar
CMD java ${JAVA_OPTS} -jar github-repository-connector.jar