# Script para ejecutar el proyecto Notifications con Docker en Windows

Write-Host "🐳 Iniciando proyecto Notifications con Docker..." -ForegroundColor Green

# Verificar si Docker está ejecutándose
try {
    docker info | Out-Null
} catch {
    Write-Host "❌ Error: Docker no está ejecutándose. Por favor inicia Docker Desktop." -ForegroundColor Red
    exit 1
}

# Verificar si existe la carpeta wallet
if (-not (Test-Path "wallet")) {
    Write-Host "❌ Error: No se encontró la carpeta 'wallet'. Asegúrate de tener configurada la conexión a Oracle Cloud." -ForegroundColor Red
    exit 1
}

# Compilar el proyecto
Write-Host "📦 Compilando el proyecto..." -ForegroundColor Yellow
./mvnw clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Error: Falló la compilación del proyecto." -ForegroundColor Red
    exit 1
}

# Construir la imagen Docker
Write-Host "🔨 Construyendo imagen Docker..." -ForegroundColor Yellow
docker build -t notifications-app .

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Error: Falló la construcción de la imagen Docker." -ForegroundColor Red
    exit 1
}

# Ejecutar con docker-compose
Write-Host "🚀 Iniciando contenedor..." -ForegroundColor Yellow
docker-compose -f docker-compose-notifications.yaml up -d

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ ¡Proyecto iniciado exitosamente!" -ForegroundColor Green
    Write-Host "🌐 Aplicación disponible en: http://localhost:8080" -ForegroundColor Cyan
    Write-Host "📚 Swagger UI disponible en: http://localhost:8080/doc/swagger-ui.html" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "📋 Comandos útiles:" -ForegroundColor Yellow
    Write-Host "  - Ver logs: docker-compose -f docker-compose-notifications.yaml logs -f" -ForegroundColor White
    Write-Host "  - Detener: docker-compose -f docker-compose-notifications.yaml down" -ForegroundColor White
    Write-Host "  - Reiniciar: docker-compose -f docker-compose-notifications.yaml restart" -ForegroundColor White
} else {
    Write-Host "❌ Error: Falló al iniciar el contenedor." -ForegroundColor Red
    exit 1
} 