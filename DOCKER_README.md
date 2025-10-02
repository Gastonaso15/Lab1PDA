# Culturarte API - Docker Setup

Esta guía te ayudará a configurar y ejecutar la aplicación Culturarte API usando Docker.

## 📋 Prerrequisitos

- Docker Desktop instalado y ejecutándose
- Docker Compose (incluido con Docker Desktop)
- Al menos 2GB de RAM disponible
- Puertos 8080, 8081, y 3307 disponibles

## 🚀 Inicio Rápido

### Opción 1: Script Automático (Recomendado)

```bash
./docker-setup.sh
```

### Opción 2: Manual

1. **Construir la aplicación:**
   ```bash
   mvn clean package -DskipTests
   ```

2. **Iniciar los servicios:**
   ```bash
   docker-compose up -d
   ```

## 🌐 Servicios Disponibles

Una vez iniciados los servicios, estarán disponibles en:

- **API REST**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **Health Check**: http://localhost:8080/api/actuator/health
- **phpMyAdmin**: http://localhost:8081

## 🔧 Configuración

### Variables de Entorno

Puedes personalizar la configuración creando un archivo `.env`:

```bash
# JWT Configuration
JWT_SECRET=tu_clave_secreta_muy_larga_y_segura_de_al_menos_256_bits

# Database Configuration
MYSQL_DATABASE=culturarte
MYSQL_ROOT_PASSWORD=tu_password_seguro
```

### Perfiles de Spring

La aplicación usa el perfil `docker` automáticamente cuando se ejecuta en contenedores.

## 📊 Comandos Útiles

### Ver estado de los servicios
```bash
docker-compose ps
```

### Ver logs de la aplicación
```bash
docker-compose logs -f app
```

### Ver logs de la base de datos
```bash
docker-compose logs -f db
```

### Reiniciar un servicio específico
```bash
docker-compose restart app
```

### Detener todos los servicios
```bash
docker-compose down
```

### Detener y eliminar volúmenes (⚠️ Elimina datos de la BD)
```bash
docker-compose down -v
```

## 🔍 Verificación de la Instalación

### 1. Verificar que los servicios estén ejecutándose:
```bash
docker-compose ps
```

Deberías ver algo como:
```
      Name                    Command               State                    Ports                  
------------------------------------------------------------------------------------------------
culturarte_app    java -jar app.jar --spring ...   Up      0.0.0.0:8080->8080/tcp              
culturarte_db     docker-entrypoint.sh mysqld      Up      0.0.0.0:3307->3306/tcp, 33060/tcp  
culturarte_pma    /docker-entrypoint.sh apac ...   Up      0.0.0.0:8081->80/tcp                
```

### 2. Verificar la salud de la API:
```bash
curl http://localhost:8080/api/actuator/health
```

Debería responder:
```json
{"status":"UP"}
```

### 3. Verificar la documentación de la API:
Visita: http://localhost:8080/api/swagger-ui.html

## 🔐 Autenticación

La API requiere autenticación JWT. Para probar los endpoints:

1. Usa el endpoint `/auth/login` para obtener un token
2. Incluye el token en el header: `Authorization: Bearer <token>`

## 🗄️ Base de Datos

### Acceso a phpMyAdmin
- URL: http://localhost:8081
- Servidor: `db`
- Usuario: `root`
- Contraseña: `password` (o la configurada en `.env`)

### Conexión directa a MySQL
```bash
docker exec -it culturarte_db mysql -u root -p culturarte
```

## 📁 Estructura de Archivos

```
├── docker-compose.yml      # Configuración de servicios
├── Dockerfile             # Imagen de la aplicación
├── docker-setup.sh        # Script de configuración automática
├── .dockerignore          # Archivos ignorados por Docker
└── docker/
    └── init-db/           # Scripts de inicialización de BD
```

## 🐛 Solución de Problemas

### La aplicación no inicia
1. Verificar que Docker esté ejecutándose
2. Verificar que los puertos no estén en uso
3. Revisar los logs: `docker-compose logs app`

### Error de conexión a la base de datos
1. Verificar que el servicio `db` esté saludable: `docker-compose ps`
2. Esperar a que la BD termine de inicializarse
3. Revisar logs de la BD: `docker-compose logs db`

### Error de JWT (clave débil)
1. Configurar una clave más larga en `.env`
2. Reiniciar el servicio: `docker-compose restart app`

### Puerto en uso
```bash
# Cambiar el puerto en docker-compose.yml
ports:
  - "8090:8080"  # Usar puerto 8090 en lugar de 8080
```

## 🔄 Actualización

Para actualizar la aplicación después de cambios en el código:

```bash
# Reconstruir y reiniciar
mvn clean package -DskipTests
docker-compose up -d --build app
```

## 📝 Logs y Monitoreo

### Ver todos los logs
```bash
docker-compose logs -f
```

### Monitorear recursos
```bash
docker stats
```

### Acceder al contenedor de la aplicación
```bash
docker exec -it culturarte_app sh
```

## 🛡️ Seguridad

- La aplicación se ejecuta con un usuario no-root
- Los secretos deben configurarse via variables de entorno
- Los puertos de la BD no están expuestos públicamente en producción
- JWT usa claves de al menos 256 bits

## 📞 Soporte

Si encuentras problemas:

1. Revisa los logs: `docker-compose logs`
2. Verifica la configuración de red: `docker network ls`
3. Reinicia los servicios: `docker-compose restart`
4. Como último recurso: `docker-compose down && docker-compose up -d`
