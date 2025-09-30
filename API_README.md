# API Culturarte - Documentación Completa

Esta es la implementación de la API REST para la gestión de Propuestas y Colaboraciones en la aplicación Culturarte, desarrollada con Spring Boot.

## 🚀 Inicio Rápido

### Opción 1: Ejecución Local (Recomendado para desarrollo)
```bash
# 1. Compilar y ejecutar
mvn clean package -DskipTests
mvn spring-boot:run

# 2. Verificar que funciona
curl -s http://localhost:8080/api/v3/api-docs | jq '.openapi'
```

### Opción 2: Con Docker (Recomendado para producción)
```bash
# 1. Construir y levantar servicios
docker compose build
docker compose up -d

# 2. Verificar que funciona
curl -s http://localhost:8082/api/v3/api-docs | jq '.openapi'
```

## 📋 Configuración

### Base de Datos
- **Local**: MySQL en `localhost:3307` (puerto 3307 para evitar conflictos)
- **Docker**: MySQL en `db:3306` (servicio interno)
- **Base de datos**: `culturarte`
- **Usuario**: `root` / **Contraseña**: `password`

### Puertos
- **API Local**: `http://localhost:8080`
- **API Docker**: `http://localhost:8082`
- **phpMyAdmin**: `http://localhost:8081`

## 🔐 Autenticación

### Obtener Token JWT

#### Con Docker (Puerto 8082)
```bash
# Login con usuario admin
curl -X POST http://localhost:8082/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}'

# Respuesta esperada:
# {"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", "type": "Bearer"}
```

#### Con Ejecución Local (Puerto 8080)
```bash
# Login con usuario admin
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}'
```

### Usar Token en Requests

#### Con Docker
```bash
# Guardar token en variable
TOKEN=$(curl -s -X POST http://localhost:8082/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}' | jq -r .token)

# Usar token en requests protegidos
curl -X GET http://localhost:8082/api/propuestas \
  -H "Authorization: Bearer $TOKEN"
```

#### Con Ejecución Local
```bash
# Guardar token en variable
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}' | jq -r .token)

# Usar token en requests protegidos
curl -X GET http://localhost:8080/api/propuestas \
  -H "Authorization: Bearer $TOKEN"
```

## 📚 Endpoints Disponibles

### 🔑 Autenticación
| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| `POST` | `/api/auth/login` | Iniciar sesión | No |
| `POST` | `/api/auth/register` | Registrar usuario | No |

### 📝 Propuestas
| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| `GET` | `/api/propuestas` | Listar todas las propuestas | ✅ JWT |
| `POST` | `/api/propuestas` | Crear nueva propuesta | ✅ JWT |
| `GET` | `/api/propuestas/{id}` | Obtener propuesta por ID | ✅ JWT |
| `PUT` | `/api/propuestas/{id}` | Actualizar propuesta | ✅ JWT |
| `DELETE` | `/api/propuestas/{id}` | Eliminar propuesta | ✅ JWT |

### 💰 Colaboraciones
| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| `GET` | `/api/colaboraciones` | Listar todas las colaboraciones | ✅ JWT |
| `POST` | `/api/colaboraciones` | Crear nueva colaboración | ✅ JWT |
| `GET` | `/api/colaboraciones/total` | Obtener total de aportes | ✅ JWT |

## 🛠️ Ejemplos de Uso

### 1. Crear una Propuesta

#### Con Docker (Puerto 8082)
```bash
TOKEN=$(curl -s -X POST http://localhost:8082/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}' | jq -r .token)

curl -X POST http://localhost:8082/api/propuestas \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Concierto de Jazz",
    "descripcion": "Evento musical en el centro cultural",
    "lugar": "Teatro Municipal",
    "fechaPrevista": "2024-12-15",
    "precioEntrada": 25.0,
    "montoNecesario": 5000.0,
    "imagen": "concierto.jpg",
    "categoria": {"nombre": "Música"},
    "proponente": {"nombre": "admin"},
    "tiposRetorno": [{"tipo": "ENTRADA_GRATUITA"}]
  }'
```

#### Con Ejecución Local (Puerto 8080)
```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}' | jq -r .token)

curl -X POST http://localhost:8080/api/propuestas \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Concierto de Jazz",
    "descripcion": "Evento musical en el centro cultural",
    "lugar": "Teatro Municipal",
    "fechaPrevista": "2024-12-15",
    "precioEntrada": 25.0,
    "montoNecesario": 5000.0,
    "imagen": "concierto.jpg",
    "categoria": {"nombre": "Música"},
    "proponente": {"nombre": "admin"},
    "tiposRetorno": [{"tipo": "ENTRADA_GRATUITA"}]
  }'
```

### 2. Listar Propuestas

#### Con Docker
```bash
curl -X GET http://localhost:8082/api/propuestas \
  -H "Authorization: Bearer $TOKEN" | jq .
```

#### Con Ejecución Local
```bash
curl -X GET http://localhost:8080/api/propuestas \
  -H "Authorization: Bearer $TOKEN" | jq .
```

### 3. Crear Colaboración

#### Con Docker
```bash
curl -X POST http://localhost:8082/api/colaboraciones \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "monto": 100.0,
    "tipoRetorno": {"tipo": "ENTRADA_GRATUITA"},
    "propuesta": {"id": 1},
    "colaborador": {"id": 1}
  }'
```

#### Con Ejecución Local
```bash
curl -X POST http://localhost:8080/api/colaboraciones \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "monto": 100.0,
    "tipoRetorno": {"tipo": "ENTRADA_GRATUITA"},
    "propuesta": {"id": 1},
    "colaborador": {"id": 1}
  }'
```

### 4. Ver Total de Aportes

#### Con Docker
```bash
curl -X GET http://localhost:8082/api/colaboraciones/total \
  -H "Authorization: Bearer $TOKEN"
```

#### Con Ejecución Local
```bash
curl -X GET http://localhost:8080/api/colaboraciones/total \
  -H "Authorization: Bearer $TOKEN"
```

## 📖 Documentación Interactiva

Una vez que la aplicación esté ejecutándose, puedes acceder a:

- **Swagger UI**: 
  - Local: http://localhost:8080/api/swagger-ui.html
  - Docker: http://localhost:8082/api/swagger-ui.html
- **OpenAPI JSON**: 
  - Local: http://localhost:8080/api/v3/api-docs
  - Docker: http://localhost:8082/api/v3/api-docs

##  Docker Commands

### Comandos Útiles
```bash
# Ver logs de todos los servicios
docker compose logs -f

# Ver logs solo de la app
docker compose logs -f app

# Reiniciar solo la app
docker compose restart app

# Parar todos los servicios
docker compose down

# Parar y eliminar volúmenes (CUIDADO: borra la BD)
docker compose down -v
```

### Troubleshooting Docker
```bash
# Si hay problemas de puerto
docker compose down
sudo fuser -k 8082/tcp
docker compose up -d

# Si hay problemas de BD
docker compose down -v
docker compose up -d
```

##  Configuración Avanzada

### Variables de Entorno
Puedes configurar las siguientes propiedades en `application.yaml`:

```yaml
# JWT Configuration
jwt:
  secret: mySecretKey          # Clave secreta para JWT
  expiration: 86400            # Tiempo de expiración (24h)

# Database Configuration
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/culturarte
    username: root
    password: password
```

### Perfiles
- **default**: Usa `localhost:3307` (desarrollo local)
- **docker**: Usa `db:3306` (contenedor Docker)

## ️ Estructura del Proyecto

```
src/main/java/culturarte/api/
├── CulturarteApiApplication.java          # Aplicación principal
├── config/
│   ├── BeanConfig.java                    # Configuración de beans
│   └── OpenApiConfig.java                 # Configuración OpenAPI
├── controllers/
│   ├── AuthController.java                # Autenticación
│   ├── ColaboracionRestController.java    # Colaboraciones
│   └── PropuestaRestController.java      # Propuestas
├── dto/
│   ├── ColaboracionDto.java               # DTO Colaboración
│   ├── PropuestaCategoriaDto.java         # DTO Categoría
│   ├── PropuestaDto.java                  # DTO Propuesta
│   ├── PropuestaEstadoDto.java            # DTO Estado
│   ├── TipoRetornoDto.java                # DTO Tipo Retorno
│   └── TotalAportesDto.java               # DTO Total Aportes
└── security/
    ├── CustomUserDetailsService.java      # Servicio de usuarios
    ├── JwtAuthenticationFilter.java       # Filtro JWT
    ├── JwtUtil.java                       # Utilidades JWT
    └── SecurityConfig.java                # Configuración seguridad
```

## ️ Notas Importantes

1. **Rutas corregidas**: Las rutas ahora son `/api/auth`, `/api/propuestas`, `/api/colaboraciones` (sin doble `/api`)

2. **Puerto Docker**: La API en Docker corre en puerto `8082` para evitar conflictos

3. **Base de datos**: Asegúrate de que MySQL esté corriendo antes de iniciar la aplicación

4. **Autenticación**: Usa `admin/admin` para login inicial

5. **CORS**: Configurado para permitir todas las orígenes en desarrollo

## 🚨 Troubleshooting

### Error: "Failed to connect to localhost port 8080"
**Causa**: Estás intentando conectarte al puerto 8080 pero la API no está corriendo ahí.

**Solución**:
```bash
# Verificar qué puerto está usando la API
docker compose ps

# Si usas Docker, usar puerto 8082
curl -X POST http://localhost:8082/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}'

# Si usas ejecución local, usar puerto 8080
mvn spring-boot:run
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}'
```

### Error: "La dirección ya se está usando"
```bash
# Liberar puerto 8080
sudo fuser -k 8080/tcp

# O usar puerto diferente
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"
```

### Error: "Could not obtain connection to query metadata"
- Verificar que MySQL esté corriendo
- Verificar credenciales en `application.yaml`
- Verificar que la base de datos `culturarte` exista

### Error: "401 Unauthorized" o "403 Forbidden"
- Verificar que el token JWT sea válido
- Verificar que el header `Authorization: Bearer TOKEN` esté presente
- Verificar que estés usando el puerto correcto (8080 local, 8082 Docker)

### Error: "Empty reply from server"
- Verificar que el contenedor esté corriendo: `docker compose ps`
- Verificar logs: `docker compose logs app`
- Verificar que la aplicación esté escuchando en el puerto correcto

## 📞 Soporte

Si encuentras problemas:

### Con Docker
1. Verifica los logs: `docker compose logs -f app`
2. Verifica la conectividad: `curl -s http://localhost:8082/api/v3/api-docs`
3. Verifica la autenticación: `curl -X POST http://localhost:8082/api/auth/login -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}'`

### Con Ejecución Local
1. Verifica que esté corriendo: `ps aux | grep java`
2. Verifica la conectividad: `curl -s http://localhost:8080/api/v3/api-docs`
3. Verifica la autenticación: `curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username": "admin", "password": "admin"}'`