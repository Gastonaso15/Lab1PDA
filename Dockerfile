FROM eclipse-temurin:21-jdk

WORKDIR /app

# Librerías necesarias para Swing/AWT
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
 && rm -rf /var/lib/apt/lists/*

# Copiamos el JAR ya compilado
COPY target/culturarte-app-1.0.0-jar-with-dependencies.jar app.jar

# Script de inicio
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

CMD ["/app/start.sh"]

