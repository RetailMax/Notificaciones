FROM openjdk:17-jdk-slim

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

COPY wallet /app/wallet

ENV TNS_ADMIN=/app/wallet

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]