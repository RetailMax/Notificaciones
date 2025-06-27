# 🐳 Docker - Proyecto Notifications

Este documento explica cómo ejecutar el proyecto Notifications usando Docker Desktop.

## 📋 Prerrequisitos

1. **Docker Desktop** instalado y ejecutándose
2. **Carpeta wallet** configurada para Oracle Cloud Database
3. **Java 21** instalado (para compilación local)

## 🚀 Ejecución Rápida

### Opción 1: Script Automático (Recomendado)

#### Windows (PowerShell):
```powershell
.\docker-run.ps1
```

#### Linux/Mac (Bash):
```bash
chmod +x docker-run.sh
./docker-run.sh
```

### Opción 2: Comandos Manuales

1. **Compilar el proyecto:**
   ```bash
   ./mvnw clean package -DskipTests
   ```

2. **Construir la imagen Docker:**
   ```bash
   docker build -t notifications-app .
   ```

3. **Ejecutar con docker-compose:**
   ```bash
   docker-compose -f docker-compose-notifications.yaml up -d
   ```

## 📁 Estructura de Archivos Docker

```
Notificaciones/
├── Dockerfile                          # Configuración de la imagen
├── docker-compose-notifications.yaml   # Orquestación del contenedor
├── .dockerignore                       # Archivos a excluir del build
├── docker-run.sh                       # Script de ejecución (Linux/Mac)
├── docker-run.ps1                      # Script de ejecución (Windows)
└── wallet/                             # Configuración Oracle Cloud
```

## 🔧 Configuración

### Variables de Entorno
- `SPRING_PROFILES_ACTIVE=dev` - Perfil de Spring Boot
- `TNS_ADMIN=/app/wallet` - Ruta a la configuración Oracle
- `JAVA_OPTS="-Xmx512m -Xms256m"` - Configuración de memoria JVM

### Puertos
- **8080** - Puerto de la aplicación

### Volúmenes
- `./wallet:/app/wallet:ro` - Configuración Oracle (solo lectura)

## 📊 Comandos Útiles

### Gestión del Contenedor
```bash
# Ver logs en tiempo real
docker-compose -f docker-compose-notifications.yaml logs -f

# Detener el contenedor
docker-compose -f docker-compose-notifications.yaml down

# Reiniciar el contenedor
docker-compose -f docker-compose-notifications.yaml restart

# Ver estado del contenedor
docker-compose -f docker-compose-notifications.yaml ps
```

### Gestión de Imágenes
```bash
# Ver imágenes disponibles
docker images

# Eliminar imagen
docker rmi notifications-app

# Reconstruir imagen
docker build --no-cache -t notifications-app .
```

### Acceso al Contenedor
```bash
# Entrar al contenedor
docker exec -it notifications-app /bin/bash

# Ver logs del contenedor
docker logs notifications-app

# Ver estadísticas del contenedor
docker stats notifications-app
```

## 🌐 Acceso a la Aplicación

Una vez ejecutado, la aplicación estará disponible en:

- **API REST:** http://localhost:8080/api/v1/usuarios
- **Swagger UI:** http://localhost:8080/doc/swagger-ui.html
- **Health Check:** http://localhost:8080/api/v1/usuarios

## 🔍 Troubleshooting

### Error: "Docker no está ejecutándose"
- Inicia Docker Desktop
- Verifica que Docker esté funcionando con `docker info`

### Error: "No se encontró la carpeta wallet"
- Asegúrate de tener configurada la conexión a Oracle Cloud
- La carpeta `wallet` debe estar en el directorio raíz del proyecto

### Error: "Falló la compilación"
- Verifica que Java 21 esté instalado
- Ejecuta `./mvnw clean` antes de intentar nuevamente

### Error: "Puerto 8080 ya está en uso"
- Detén la aplicación local: `./mvnw spring-boot:stop`
- O cambia el puerto en `docker-compose-notifications.yaml`

### Error: "Conexión a base de datos fallida"
- Verifica que la carpeta `wallet` esté correctamente configurada
- Comprueba las credenciales de Oracle Cloud en `application.properties`

## 🧹 Limpieza

Para limpiar completamente Docker:

```bash
# Detener y eliminar contenedores
docker-compose -f docker-compose-notifications.yaml down

# Eliminar imagen
docker rmi notifications-app

# Limpiar recursos no utilizados
docker system prune -f
```

## 📝 Notas Importantes

1. **Primera ejecución:** El build puede tardar varios minutos
2. **Memoria:** El contenedor usa 512MB máximo de RAM
3. **Persistencia:** Los datos se mantienen en Oracle Cloud
4. **Logs:** Se pueden ver en tiempo real con el comando de logs
5. **Reinicio:** El contenedor se reinicia automáticamente si falla

## 🔗 Enlaces Útiles

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Oracle Cloud Database](https://www.oracle.com/cloud/database/) 