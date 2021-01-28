FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8080

ARG JAR_FILE=target/internetlibrary-1.0.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

RUN apk add --no-cache bash

COPY wait-for.sh /wait-for.sh

RUN chmod +x /wait-for.sh

ENTRYPOINT ["/wait-for.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
