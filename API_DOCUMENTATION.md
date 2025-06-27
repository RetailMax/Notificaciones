# RetailMax Notifications API - Documentación

## Descripción General

La API de Notificaciones de RetailMax es un sistema RESTful diseñado para la gestión de usuarios y notificaciones en el ecosistema de retail. Esta API proporciona endpoints seguros y escalables para operaciones CRUD de usuarios.

## Acceso a la Documentación Swagger

### URLs de Acceso

- **Desarrollo Local**: http://localhost:8082/swagger-ui.html
- **API Docs JSON**: http://localhost:8082/api-docs
- **API Docs YAML**: http://localhost:8082/api-docs.yaml

### Características de la Documentación

- **Interfaz Interactiva**: Prueba los endpoints directamente desde el navegador
- **Ejemplos de Código**: Genera automáticamente ejemplos en diferentes lenguajes
- **Validación en Tiempo Real**: Valida los datos de entrada antes de enviar
- **Respuestas Detalladas**: Muestra todos los códigos de respuesta posibles

## Endpoints Disponibles

### Usuarios (`/api/v1/usuarios`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/usuarios` | Listar todos los usuarios |
| GET | `/api/v1/usuarios/{id}` | Obtener usuario por ID |
| POST | `/api/v1/usuarios` | Crear nuevo usuario |
| PUT | `/api/v1/usuarios/{id}` | Actualizar usuario existente |
| DELETE | `/api/v1/usuarios/{id}` | Eliminar usuario |
| PATCH | `/api/v1/usuarios/{id}/estado` | Cambiar estado del usuario |

## Modelo de Datos

### Usuario

```json
{
  "id": 1,
  "nroId": "12345678",
  "nombre": "Juan Pérez",
  "correoElectronico": "juan.perez@email.com",
  "estado": "ACTIVO"
}
```

### Validaciones

- **nroId**: Obligatorio, único, 5-20 caracteres
- **nombre**: Obligatorio, 2-50 caracteres
- **correoElectronico**: Obligatorio, formato válido de email, único
- **estado**: Opcional, valores permitidos: "ACTIVO", "INACTIVO"

## Códigos de Respuesta

| Código | Descripción |
|--------|-------------|
| 200 | Operación exitosa |
| 201 | Recurso creado exitosamente |
| 204 | Operación exitosa sin contenido |
| 400 | Datos de entrada inválidos |
| 404 | Recurso no encontrado |
| 409 | Conflicto (ej: duplicado) |
| 500 | Error interno del servidor |

## Autenticación

Actualmente la API no requiere autenticación, pero está preparada para implementar JWT en el futuro.

## Ejemplos de Uso

### Crear un Usuario

```bash
curl -X POST "http://localhost:8082/api/v1/usuarios" \
     -H "Content-Type: application/json" \
     -d '{
       "nroId": "12345678",
       "nombre": "Juan Pérez",
       "correoElectronico": "juan.perez@email.com"
     }'
```

### Obtener Usuario por ID

```bash
curl -X GET "http://localhost:8082/api/v1/usuarios/1"
```

### Actualizar Estado de Usuario

```bash
curl -X PATCH "http://localhost:8082/api/v1/usuarios/1/estado?estado=INACTIVO"
```

## Configuración del Entorno

### Requisitos

- Java 21+
- Spring Boot 3.3.11
- Oracle Database 19c+
- Maven 3.6+

### Variables de Entorno

```properties
# Base de Datos
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=retailmax
spring.datasource.password=retailmax123

# Servidor
server.port=8082
```

## Desarrollo

### Ejecutar la Aplicación

```bash
# Compilar y ejecutar
mvn spring-boot:run

# O ejecutar el JAR
mvn clean package
java -jar target/notifications-0.0.1-SNAPSHOT.jar
```

### Ejecutar Tests

```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=UsuarioControllerTest
```

## Próximas Funcionalidades

- [ ] Sistema de autenticación JWT
- [ ] Gestión de pedidos
- [ ] Sistema de notificaciones push
- [ ] Filtros y paginación
- [ ] Logs de auditoría
- [ ] Rate limiting
- [ ] Caché Redis

## Soporte

Para soporte técnico o consultas sobre la API:

- **Email**: desarrollo@retailmax.com
- **Documentación**: https://www.retailmax.com/developers
- **Issues**: Repositorio del proyecto

---

**Versión**: 1.0.0  
**Última actualización**: Diciembre 2024  
**Desarrollado por**: RetailMax Development Team 