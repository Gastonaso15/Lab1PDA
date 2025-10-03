#!/bin/bash

echo "=== Prueba de Funcionalidad de Imagen de Propuestas ==="
echo ""

BASE_URL="http://localhost:8080/api"

echo "Verificando que la API esté funcionando..."
if curl -s "$BASE_URL/propuestas/test" > /dev/null; then
    echo "API funcionando correctamente"
else
    echo "API no disponible"
    exit 1
fi
echo ""

echo "1. Obteniendo lista de propuestas para ver qué IDs están disponibles..."
echo "Primeras 3 propuestas:"
curl -s "$BASE_URL/propuestas" | jq '.[0:3] | .[] | {id: .id, titulo: .titulo, imagen: .imagen}' 2>/dev/null || echo "Respuesta sin formato JSON"
echo ""

echo "2. Probando funcionalidad de imagen con diferentes endpoints..."
echo ""

echo "Método 1: Usando curl directo"
echo "GET /api/propuestas/2 (propuesta completa):"
curl -s -w "Status: %{http_code}\n" "$BASE_URL/propuestas/1" | head -c 200
echo ""
echo ""

echo "GET /api/propuestas/2?image=true (imagen):"
curl -s -w "Status: %{http_code}\n" "$BASE_URL/propuestas/1?image=true" | head -c 100
echo ""
echo ""

echo "Método 2: Usando endpoint alternativo"
echo "GET /api/propuestas/by-id/2 (propuesta completa):"
curl -s -w "Status: %{http_code}\n" "$BASE_URL/propuestas/by-id/1" | head -c 200
echo ""
echo ""

echo "GET /api/propuestas/by-id/2?image=true (imagen):"
curl -s -w "Status: %{http_code}\n" "$BASE_URL/propuestas/by-id/1?image=true" | head -c 100
echo ""
echo ""

echo "3. Información para debugging:"
echo "Verificando logs de la aplicación..."
docker-compose logs app | tail -5
echo ""

echo "4. Resumen de funcionalidades implementadas:"
echo "Endpoint GET /api/propuestas/{id} - Obtener propuesta completa"
echo "Endpoint GET /api/propuestas/{id}?image=true - Obtener imagen"
echo "Endpoint GET /api/propuestas/{id}?image=false - Obtener propuesta (explícito)"
echo "Método getContentType() - Determinar tipo de contenido"
echo "Manejo de archivos de imagen desde uploads/propuestas/"
echo ""

echo "5. Instrucciones para Postman/Insomnia:"
echo "URL Base: $BASE_URL"
echo ""
echo "Para probar en Postman:"
echo "1. GET $BASE_URL/propuestas/2"
echo "2. GET $BASE_URL/propuestas/2?image=true"
echo "3. GET $BASE_URL/propuestas/2?image=false"
echo ""

echo "=== Pruebas completadas ==="
