# API Culturarte - Guía de Uso

## 🚀 Inicio Rápido

### 1. Levantar la API con Docker

```bash
# Levantar los servicios
docker-compose up -d

# Verificar que estén funcionando
docker-compose ps
```

### 2. Probar que la API funciona

```bash
# Test básico
curl "http://localhost:8080/api/propuestas/test"

# Test completo con Docker
./test-api-final.sh

# Test de funcionalidad de imágenes
./test-image-functionality.sh
```

## 📋 Endpoints Disponibles

### Propuestas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/propuestas` | Lista todas las propuestas |
| GET | `/api/propuestas/{id}` | Obtiene una propuesta específica |
| GET | `/api/propuestas/{id}?image=true` | Obtiene la imagen de una propuesta |

### Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/auth/test` | Test de autenticación |

### Colaboraciones

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/colaboraciones` | Lista todas las colaboraciones |

## 🖼️ Ejemplos de Uso

### Obtener todas las propuestas
```bash
curl "http://localhost:8080/api/propuestas"
```

### Obtener una propuesta específica
```bash
curl "http://localhost:8080/api/propuestas/1"
```

### Obtener la imagen de una propuesta
```bash
curl "http://localhost:8080/api/propuestas/2?image=true" --output imagen.jpg
```

### Guardar imagen en archivo
```bash
curl "http://localhost:8080/api/propuestas/2?image=true" -o imagen_propuesta.jpg
```

## 🧪 Tests Automatizados

### Test completo de la API
```bash
chmod +x test-api-final.sh
./test-api-final.sh
```

### Test de funcionalidad de imágenes
```bash
chmod +x test-image-functionality.sh
./test-image-functionality.sh
```

## 🛠️ Comandos Útiles

### Ver logs de la aplicación
```bash
docker-compose logs app
```

### Reiniciar los servicios
```bash
docker-compose down
docker-compose up -d
```

### Verificar estado de contenedores
```bash
docker-compose ps
```

## 📝 Notas

- **URL Base**: `http://localhost:8080/api`
- **Propuestas con imagen**: IDs 2, 3, 4, 5 tienen imágenes
- **Propuesta sin imagen**: ID 1 no tiene imagen (devuelve 404)
- **Formato de imágenes**: JPG, PNG soportados

---

**¡La API está lista para usar!** 🎉