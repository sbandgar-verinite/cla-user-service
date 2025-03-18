FROM maven:3.8.6-openjdk-11 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/my-app.jar /app/my-app.jar

ENTRYPOINT ["java", "-jar", "/app/my-app.jar"]

EXPOSE 8088
