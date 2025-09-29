# API PDA - LabP.D.A.

Esta es la implementación de la API REST para la gestión de Propuestas y Colaboraciones en la aplicación PDA, desarrollada con Spring Boot.

## Características

- **Spring Boot 3.2.0** con Java 21
- **Autenticación JWT** (Bearer Token)
- **Documentación OpenAPI/Swagger** automática
- **Validación de datos** con Jakarta Validation
- **Integración con Hibernate/JPA** existente

## Endpoints Disponibles

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar usuario (no implementado)

### Propuestas
- `GET /api/propuestas` - Listar todas las propuestas
- `POST /api/propuestas` - Crear nueva propuesta
- `GET /api/propuestas/{id}` - Obtener propuesta por ID
- `PUT /api/propuestas/{id}` - Actualizar propuesta
- `DELETE /api/propuestas/{id}` - Eliminar propuesta

### Colaboraciones
- `GET /api/colaboraciones` - Listar todas las colaboraciones
- `POST /api/colaboraciones` - Crear nueva colaboración
- `GET /api/colaboraciones/total` - Obtener total de aportes

## Configuración

### Base de Datos
La aplicación está configurada para usar MySQL. Asegúrate de tener:
- MySQL ejecutándose en `localhost:3306`
- Base de datos `culturarte` creada
- Usuario `root` con contraseña `password`

### Variables de Entorno
Puedes configurar las siguientes propiedades en `application.yml`:
- `jwt.secret`: Clave secreta para JWT (default: "mySecretKey")
- `jwt.expiration`: Tiempo de expiración en segundos (default: 86400)
- `spring.datasource.*`: Configuración de base de datos

## Ejecución

### Con Maven
```bash
# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run
```

### Con JAR
```bash
# Compilar y empaquetar
mvn clean package

# Ejecutar
java -jar target/culturarte-app-1.0.0.jar
```

## Documentación API

Una vez que la aplicación esté ejecutándose, puedes acceder a:
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

## Autenticación

### Obtener Token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}'
```

### Usar Token
```bash
curl -X GET http://localhost:8080/api/propuestas \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Estructura del Proyecto

```
src/main/java/culturarte/api/
├── CulturarteApiApplication.java          # Aplicación principal
├── config/
│   └── OpenApiConfig.java                 # Configuración OpenAPI
├── controllers/
│   ├── AuthController.java                # Autenticación
│   ├── ColaboracionController.java        # Colaboraciones
│   └── PropuestaController.java           # Propuestas
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

## Notas Importantes

1. **Integración con código existente**: La API utiliza los controladores y manejadores existentes del proyecto original.

2. **Autenticación**: El sistema de autenticación está configurado pero puede necesitar ajustes según los usuarios existentes en la base de datos.

3. **Validación**: Los DTOs incluyen validaciones básicas que pueden ser extendidas según las necesidades.

4. **CORS**: Configurado para permitir todas las orígenes en desarrollo. Ajustar para producción.

5. **Manejo de errores**: Implementación básica que puede ser mejorada con manejo de excepciones más específico.

## Próximos Pasos

1. Implementar el registro de usuarios
2. Mejorar el manejo de errores
3. Agregar más validaciones
4. Implementar paginación en los endpoints de listado
5. Agregar logs de auditoría
6. Configurar HTTPS para producción
