# Variables de entorno

El backend lee sus secretos y credenciales desde variables de entorno declaradas en `application.properties`.

## Variables necesarias

- `DB_USER`
- `DB_PASSWORD`
- `JWT_SECRET`
- `MAIL_HOST`
- `MAIL_PORT`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `MAIL_SMTP_AUTH`
- `MAIL_SMTP_STARTTLS`

Tienes una plantilla en [.env.example](./.env.example).

## Importante sobre Spring Boot

Spring Boot no carga un fichero `.env` por si solo. Tienes tres opciones sencillas:

1. Declararlas en la configuracion de ejecucion del IDE.
2. Exportarlas en la terminal antes de arrancar la app.
3. Configurarlas en el sistema o en el servidor donde se despliegue.

## Ejemplo en terminal

```bash
export DB_USER="root"
export DB_PASSWORD="tu_password"
export JWT_SECRET="una_clave_larga_y_segura"
export MAIL_HOST="smtp.gmail.com"
export MAIL_PORT="587"
export MAIL_USERNAME="tu_correo@ejemplo.com"
export MAIL_PASSWORD="tu_app_password"
export MAIL_SMTP_AUTH="true"
export MAIL_SMTP_STARTTLS="true"
./mvnw spring-boot:run
```

## IntelliJ o VS Code

Añade estas mismas claves en la configuracion de ejecucion del backend. Asi evitas guardarlas en el repositorio.
