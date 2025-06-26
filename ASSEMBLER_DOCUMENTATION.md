# UsuarioModelAssembler - Documentación Técnica

## Descripción General

El `UsuarioModelAssembler` es un componente fundamental del sistema que implementa el patrón HATEOAS (Hypermedia as the Engine of Application State) para la API de RetailMax Notifications. Este assembler se encarga de convertir entidades `Usuario` en modelos `EntityModel` que incluyen enlaces hipermedia automáticos.

## Funcionalidades Principales

### 1. Conversión Automática de Entidades
- Convierte entidades `Usuario` en `EntityModel<Usuario>`
- Agrega enlaces HATEOAS automáticamente
- Mantiene la consistencia en toda la API

### 2. Enlaces HATEOAS Generados
- **self**: Enlace al recurso actual
- **usuarios**: Enlace a la colección de usuarios
- **actualizar**: Enlace para actualizar el usuario
- **eliminar**: Enlace para eliminar el usuario
- **cambiarEstado**: Enlace para cambiar el estado

### 3. Métodos Especializados
- `toModel()`: Conversión estándar con todos los enlaces
- `toModelForCreation()`: Optimizado para recursos recién creados
- `toModelForUpdate()`: Optimizado para recursos actualizados

## Implementación Técnica

### Dependencias
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

### Anotaciones Utilizadas
- `@Component`: Registra el assembler como bean de Spring
- `@Schema`: Documentación para Swagger/OpenAPI
- `RepresentationModelAssembler`: Interfaz de Spring HATEOAS

## Ejemplo de Uso

### En el Controlador
```java
@Autowired
private UsuarioModelAssemblers assembler;

@GetMapping("/{id}")
public ResponseEntity<EntityModel<Usuario>> obtenerPorId(@PathVariable Long id) {
    Usuario usuario = usuarioService.findById(id);
    return ResponseEntity.ok(assembler.toModel(usuario));
}
```

### Respuesta Generada
```json
{
  "id": 1,
  "nroId": "12345678",
  "nombre": "Juan Pérez",
  "correoElectronico": "juan.perez@email.com",
  "estado": "ACTIVO",
  "_links": {
    "self": {
      "href": "http://localhost:8082/api/v1/usuarios/1"
    },
    "usuarios": {
      "href": "http://localhost:8082/api/v1/usuarios"
    },
    "actualizar": {
      "href": "http://localhost:8082/api/v1/usuarios/1"
    },
    "eliminar": {
      "href": "http://localhost:8082/api/v1/usuarios/1"
    },
    "cambiarEstado": {
      "href": "http://localhost:8082/api/v1/usuarios/1/estado?estado=INACTIVO"
    }
  }
}
```

## Métodos Disponibles

### toModel(Usuario usuario)
**Propósito**: Conversión estándar de entidad a EntityModel
**Enlaces incluidos**: Todos los enlaces disponibles
**Uso**: Para operaciones de lectura general

### toModelForCreation(Usuario usuario)
**Propósito**: Optimizado para recursos recién creados
**Enlaces incluidos**: self, usuarios, actualizar, eliminar
**Uso**: Para respuestas POST (201 Created)

### toModelForUpdate(Usuario usuario)
**Propósito**: Optimizado para recursos actualizados
**Enlaces incluidos**: Todos los enlaces con estado inteligente
**Uso**: Para respuestas PUT/PATCH (200 OK)

## Características Avanzadas

### 1. Enlaces Inteligentes
- El enlace `cambiarEstado` se adapta al estado actual del usuario
- Si el usuario está "ACTIVO", sugiere cambiar a "INACTIVO" y viceversa

### 2. Manejo de Estados HTTP
- **201 Created**: Para recursos recién creados
- **200 OK**: Para operaciones exitosas
- **204 No Content**: Para eliminaciones exitosas

### 3. Validación Automática
- Manejo de usuarios con ID null
- Validación de enlaces generados
- Tests unitarios completos

## Tests Unitarios

### Cobertura de Tests
- ✅ Conversión básica con enlaces correctos
- ✅ Enlaces específicos para creación
- ✅ Enlaces específicos para actualización
- ✅ Manejo de estados opuestos
- ✅ Manejo de casos edge (null, sin ID)
- ✅ Validación de URLs generadas

### Ejecutar Tests
```bash
# Test específico del assembler
./mvnw test -Dtest=UsuarioModelAssemblersTest

# Todos los tests
./mvnw test
```

## Beneficios del HATEOAS

### 1. Desacoplamiento
- Los clientes no necesitan conocer URLs hardcodeadas
- La API es auto-documentada
- Facilita la evolución de la API

### 2. Navegabilidad
- Los clientes pueden navegar por la API automáticamente
- Descubrimiento de recursos relacionado
- Mejor experiencia de desarrollo

### 3. Mantenibilidad
- Cambios en URLs no rompen clientes
- Documentación siempre actualizada
- Menos código boilerplate

## Integración con Swagger

El assembler está completamente integrado con Swagger/OpenAPI:
- Documentación automática de enlaces
- Ejemplos de respuestas con HATEOAS
- Interfaz interactiva que muestra enlaces

## Próximas Mejoras

- [ ] Soporte para paginación en colecciones
- [ ] Enlaces condicionales basados en permisos
- [ ] Cache de enlaces para mejor rendimiento
- [ ] Soporte para versionado de API
- [ ] Enlaces para recursos relacionados (pedidos)

## Troubleshooting

### Problemas Comunes

1. **Enlaces no se generan**
   - Verificar que HATEOAS esté en el classpath
   - Comprobar configuración de Spring Boot

2. **URLs incorrectas**
   - Verificar configuración de servidor
   - Comprobar contexto de request

3. **Tests fallan**
   - Ejecutar con contexto de request mock
   - Verificar configuración de MockMvc

### Logs de Debug
```properties
logging.level.org.springframework.hateoas=DEBUG
logging.level.com.retailmax.notifications.assemblers=DEBUG
```

---

**Versión**: 1.0.0  
**Última actualización**: Diciembre 2024  
**Desarrollado por**: RetailMax Development Team 