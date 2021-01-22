FROM maven:3.6.3-openjdk-11 as maven

COPY ./pom.xml ./pom.xml

RUN mvn dependency:go-offline -B

COPY ./src ./src

RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /my-project

COPY --from=maven target/internetlibrary-1.0.jar ./

RUN apk add --no-cache bash

COPY wait-for.sh /wait-for.sh

RUN chmod +x /wait-for.sh

ENTRYPOINT ["/wait-for.sh", "mysql:3306", "--", "java", "-jar", "./internetlibrary-1.0.jar"]
#ENTRYPOINT ["/wait-for.sh", "mysql:3306", "--timeout=70", "--", "java", "-jar", "./internetlibrary-1.0.jar"]
