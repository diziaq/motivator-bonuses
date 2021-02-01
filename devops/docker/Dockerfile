FROM openjdk:15-jdk-alpine AS openjdk
WORKDIR /app

FROM maven:3.6.3-openjdk-15-slim AS maven
WORKDIR /app

COPY /pom.xml /app/pom.xml
COPY /modules /app/modules
COPY /materials /app/materials

RUN mvn clean package -D result.jar.name=app

FROM openjdk:15-jdk-alpine AS final
EXPOSE 8080
COPY --from=maven /app/modules/face/target/app.jar /app.jar

ENV TERM=xterm

ADD https://github.com/just-containers/s6-overlay/releases/download/v2.2.0.0/s6-overlay-amd64.tar.gz /tmp/

RUN apk add --no-cache tar bash \
    && tar xzf /tmp/s6-overlay-amd64.tar.gz -C /

COPY devops/docker/files /
COPY application.properties /

ENTRYPOINT ["/init"]
