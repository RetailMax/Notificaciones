# Configuración de Oracle Cloud Database

## Archivos necesarios para la conexión

Para conectar a Oracle Cloud Database, necesitas descargar y colocar los siguientes archivos en este directorio:

1. **tnsnames.ora** - Configuración de nombres de servicio
2. **cwallet.sso** - Wallet de conexión segura
3. **ewallet.p12** - Wallet de conexión segura (formato PKCS12)
4. **keystore.jks** - Keystore Java (opcional)
5. **ojdbc.properties** - Propiedades del driver JDBC (opcional)

## Pasos para obtener los archivos:

1. Ve a Oracle Cloud Console
2. Navega a Database > Autonomous Database
3. Selecciona tu base de datos "notificaciones"
4. Ve a "Database Connection"
5. Descarga el "Wallet" (archivo ZIP)
6. Extrae el contenido en este directorio

## Estructura esperada:
```
wallet/
├── tnsnames.ora
├── cwallet.sso
├── ewallet.p12
├── keystore.jks
├── ojdbc.properties
└── README.md
```

## Configuración en application.properties

La configuración actual en `application.properties` está configurada para usar:
- URL: `jdbc:oracle:thin:@notificaciones_high?TNS_ADMIN=C:/wallet`
- Usuario: `ADMIN`
- Contraseña: `FullStack-001`

## Verificación de conexión

Una vez que tengas los archivos, puedes probar la conexión ejecutando:
```bash
./mvnw spring-boot:run
```

## Notas importantes:

- Asegúrate de que la ruta `C:/wallet` en la URL de conexión coincida con la ubicación real de este directorio
- Los archivos del wallet son sensibles, no los subas a control de versiones
- Para desarrollo local, considera usar H2 en memoria modificando el perfil activo 