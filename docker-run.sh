#!/bin/bash

echo "🐳 Iniciando proyecto Notifications con Docker..."

# Verificar si Docker está ejecutándose
if ! docker info > /dev/null 2>&1; then
    echo "❌ Error: Docker no está ejecutándose. Por favor inicia Docker Desktop."
    exit 1
fi

# Verificar si existe la carpeta wallet
if [ ! -d "wallet" ]; then
    echo "❌ Error: No se encontró la carpeta 'wallet'. Asegúrate de tener configurada la conexión a Oracle Cloud."
    exit 1
fi

# Compilar el proyecto
echo "📦 Compilando el proyecto..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Error: Falló la compilación del proyecto."
    exit 1
fi

# Construir la imagen Docker
echo "🔨 Construyendo imagen Docker..."
docker build -t notifications-app .

if [ $? -ne 0 ]; then
    echo "❌ Error: Falló la construcción de la imagen Docker."
    exit 1
fi

# Ejecutar con docker-compose
echo "🚀 Iniciando contenedor..."
docker-compose -f docker-compose-notifications.yaml up -d

if [ $? -eq 0 ]; then
    echo "✅ ¡Proyecto iniciado exitosamente!"
    echo "🌐 Aplicación disponible en: http://localhost:8080"
    echo "📚 Swagger UI disponible en: http://localhost:8080/doc/swagger-ui.html"
    echo ""
    echo "📋 Comandos útiles:"
    echo "  - Ver logs: docker-compose -f docker-compose-notifications.yaml logs -f"
    echo "  - Detener: docker-compose -f docker-compose-notifications.yaml down"
    echo "  - Reiniciar: docker-compose -f docker-compose-notifications.yaml restart"
else
    echo "❌ Error: Falló al iniciar el contenedor."
    exit 1
fi 