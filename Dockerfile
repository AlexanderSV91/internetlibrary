FROM maven:3.6.3-openjdk-11 as maven

COPY ./pom.xml ./pom.xml

RUN mvn dependency:go-offline -B

COPY ./src ./src

RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /my-project

COPY --from=maven target/internetlibrary-1.0.jar ./

#CMD ["java", "-jar", "./internetlibrary-1.0.jar"]
ENTRYPOINT ["java", "-jar", "./internetlibrary-1.0.jar"]