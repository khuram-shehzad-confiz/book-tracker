FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app-test.jar
ENTRYPOINT ["java","-jar","/app-test.jar"]
