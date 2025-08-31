
FROM openjdk:21

WORKDIR /app

COPY target/Lab1PDA-1.0.jar app.jar

CMD ["java", "-jar", "app.jar"]