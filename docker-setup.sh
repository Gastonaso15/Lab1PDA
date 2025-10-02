#!/bin/bash

echo "=== Configuracion Docker para Culturarte API ==="
echo ""

# Verificar si Docker esta ejecutandose
if ! docker info > /dev/null 2>&1; then
    echo "ERROR: Docker no esta ejecutandose. Por favor inicia Docker Desktop o el daemon de Docker primero."
    echo ""
    echo "Para iniciar el daemon de Docker (Linux):"
    echo "  sudo systemctl start docker"
    echo ""
    echo "Para iniciar Docker Desktop:"
    echo "  Abrir la aplicacion Docker Desktop"
    echo ""
    exit 1
fi

echo "OK: Docker esta ejecutandose"

# Construir la aplicacion
echo ""
echo "Construyendo el JAR de la aplicacion..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "ERROR: Fallo al construir la aplicacion"
    exit 1
fi

echo "OK: Aplicacion construida exitosamente"

# Construir imagen Docker
echo ""
echo "Construyendo imagen Docker..."
docker build -t culturarte-app .

if [ $? -ne 0 ]; then
    echo "ERROR: Fallo al construir la imagen Docker"
    exit 1
fi

echo "OK: Imagen Docker construida exitosamente"

# Iniciar servicios
echo ""
echo "Iniciando servicios con Docker Compose..."
docker-compose up -d

if [ $? -ne 0 ]; then
    echo "ERROR: Fallo al iniciar los servicios"
    exit 1
fi

echo ""
echo "OK: Servicios iniciados exitosamente!"
echo ""
echo "Servicios disponibles en:"
echo "  - API: http://localhost:8080/api"
echo "  - Swagger UI: http://localhost:8080/api/swagger-ui.html"
echo "  - Health Check: http://localhost:8080/api/actuator/health"
echo "  - phpMyAdmin: http://localhost:8081"
echo ""
echo "Para verificar el estado de los servicios:"
echo "  docker-compose ps"
echo ""
echo "Para ver los logs:"
echo "  docker-compose logs -f app"
echo ""
echo "Para detener los servicios:"
echo "  docker-compose down"
echo ""
