FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/*.jar ebankify.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ebankify.jar"]