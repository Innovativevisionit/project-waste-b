#FROM openjdk:17
#ADD target/authentication-0.0.1-SNAPSHOT.jar authentication-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","/authentication-0.0.1-SNAPSHOT.jar"]

FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build target/authentication-0.0.1-SNAPSHOT.jar authentication-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/authentication-0.0.1-SNAPSHOT.jar"]