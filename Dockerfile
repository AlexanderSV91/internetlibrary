#FROM adoptopenjdk/openjdk11:alpine-jre
#
#EXPOSE 8080
#
#ARG JAR_FILE=target/internetlibrary-0.0.1-SNAPSHOT.jar
#
#WORKDIR /opt/app
#
#COPY ${JAR_FILE} app.jar
#
#ENTRYPOINT ["java","-jar","app.jar"]

# our base build image
FROM maven:3.6.3-openjdk-11 as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package -DskipTests

# our final base image
FROM adoptopenjdk/openjdk11:alpine-jre

# set deployment directory
WORKDIR /my-project

# copy over the built artifact from the maven image
COPY --from=maven target/internetlibrary-0.0.1-SNAPSHOT.jar ./

# set the startup command to run your binary
CMD ["java", "-jar", "./internetlibrary-0.0.1-SNAPSHOT.jar"]
