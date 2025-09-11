
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
 && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/culturarte-app-1.0.0.jar app.jar

CMD ["java", "--enable-preview", "-cp", "app.jar", "culturarte.presentacion.EstacionDeTrabajo"]
