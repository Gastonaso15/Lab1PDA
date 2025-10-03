#!/bin/bash

# Script completo para probar la API con Docker
# Este script inicia los servicios Docker y ejecuta las pruebas

echo "=== Prueba Completa con Docker - API Culturarte ==="
echo ""

# Verificar si Docker esta ejecutandose
if ! docker info > /dev/null 2>&1; then
    echo "ERROR: Docker no esta ejecutandose."
    echo "Por favor inicia Docker Desktop o el daemon de Docker."
    exit 1
fi

echo "OK: Docker esta ejecutandose"
echo ""

# Configurar variables de entorno
export MYSQL_ROOT_PASSWORD=culturarte123
export JWT_SECRET=aVerySecureAndLongSecretKeyThatIsAtLeast256BitsLongForHMACAlgorithms1234567890

# Construir y iniciar servicios
echo "Iniciando servicios Docker..."
echo ""

# Construir la aplicacion si es necesario
if [ ! -f "target/culturarte-app-1.0.0.jar" ]; then
    echo "Construyendo aplicacion..."
    mvn clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo "ERROR: Fallo al construir la aplicacion"
        exit 1
    fi
fi

# Limpiar servicios anteriores si existen
echo "Limpiando servicios anteriores..."
docker-compose down > /dev/null 2>&1

# Iniciar servicios
echo "Iniciando contenedores..."
docker-compose up -d

if [ $? -ne 0 ]; then
    echo "ERROR: Fallo al iniciar los servicios Docker"
    echo "Intentando limpiar redes y reintentar..."
    docker-compose down
    docker network prune -f
    echo "Reintentando..."
    docker-compose up -d
    
    if [ $? -ne 0 ]; then
        echo "ERROR: Fallo al iniciar los servicios Docker despues del reintento"
        echo "Ejecuta manualmente:"
        echo "  docker-compose down"
        echo "  docker network prune -f"
        echo "  docker-compose up -d"
        exit 1
    fi
fi

echo "OK: Servicios Docker iniciados"
echo ""

# Mostrar estado de los servicios
echo "Estado de los servicios:"
docker-compose ps
echo ""

# Ejecutar pruebas
echo "Ejecutando pruebas de la API..."
echo ""

./test-api.sh

# Mostrar logs si las pruebas fallan
if [ $? -ne 0 ]; then
    echo ""
    echo "=== LOGS DE LA APLICACION (ultimas 20 lineas) ==="
    docker-compose logs --tail=20 app
    echo ""
    echo "Para ver todos los logs: docker-compose logs app"
    echo "Para detener servicios: docker-compose down"
fi

echo ""
echo "Servicios Docker siguen ejecutandose."
echo "Para detenerlos: docker-compose down"
