# Colombiando

Plataforma web para descubrir lugares turísticos de Colombia, consultar guías,
crear reservas y gestionar pagos y reseñas.

El proyecto está compuesto por:

- **Frontend:** React + Vite, servido con Nginx en el despliegue.
- **Backend:** Spring Boot, Spring MVC, Spring Data JPA y Spring Security.
- **Base de datos:** MySQL 8.4.
- **Proxy:** Apache para exponer la aplicación completa mediante un único punto
  de entrada.

## Requisitos

### Ejecución con Docker Compose

- Docker Desktop o Docker Engine con Docker Compose.

### Desarrollo local

- Java 25.
- Maven Wrapper incluido en `backend/`.
- Node.js 24 o compatible.
- pnpm.
- MySQL 8.x ejecutándose localmente.

## Inicio rápido con Docker Compose

Desde la raíz del repositorio:

```bash
docker compose up --build
```

Cuando los servicios estén listos, abre:

- Aplicación web: <http://localhost:8080>
- Aplicación web a través de Apache: <http://localhost:8081>
- API desde la aplicación: <http://localhost:8080/api>
- Documentación OpenAPI: <http://localhost:8080/api/swagger-ui/index.html>
- MySQL: `localhost:3306`

El servicio `backend` no publica directamente su puerto al host; la API se
consume a través del proxy de Nginx del frontend. Apache está disponible como
un punto de entrada alternativo.

Para detener los servicios:

```bash
docker compose down
```

Para detenerlos y eliminar también los datos persistidos de MySQL:

```bash
docker compose down -v
```

> `down -v` elimina el volumen `mysql_data`. Úsalo únicamente cuando quieras
> reiniciar la base de datos desde cero.

## Configuración de desarrollo

### Base de datos de Compose

Los valores utilizados por `docker-compose.yml` son:

| Propiedad | Valor de desarrollo |
| --- | --- |
| Base de datos | `colombiando` |
| Usuario | `colombiando` |
| Contraseña | `colombiando` |
| Contraseña de root | `root` |
| Puerto | `3306` |

Estos valores son adecuados únicamente para el entorno local. En producción
deben configurarse mediante secretos o variables de entorno seguras.

### Backend

El backend utiliza las siguientes variables para conectarse a MySQL:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
SERVER_PORT
```

El archivo `backend/src/main/resources/application.properties` contiene los
valores predeterminados para desarrollo y activa el perfil `local`. El
`docker-compose.yml` reemplaza la conexión para utilizar el servicio `db`
desde la red interna de Docker.

### Frontend

En desarrollo, Vite envía las solicitudes `/api` a
`http://localhost:8081`, según `frontend/vite.config.js`.

Para iniciar únicamente el frontend:

```bash
cd frontend
pnpm install
pnpm dev
```

La aplicación quedará disponible normalmente en
<http://localhost:5173>. Para que las solicitudes funcionen, el backend debe
estar disponible en el puerto `8081`.

## Ejecución local del backend

Con MySQL iniciado y la configuración local preparada:

```bash
cd backend
./mvnw spring-boot:run
```

En Windows:

```bat
cd backend
mvnw.cmd spring-boot:run
```

El backend local usa el contexto `/api`, por lo que sus endpoints se
encuentran en `http://localhost:8081/api`.

## Comandos útiles

### Frontend

```bash
cd frontend
pnpm lint       # Ejecutar ESLint
pnpm build      # Crear el build de producción
pnpm preview    # Servir el build localmente
```

### Backend

```bash
cd backend
./mvnw test
./mvnw package
```

## API

La API REST utiliza el prefijo `/api` y está documentada mediante OpenAPI.
Los principales recursos son:

- `/auth`: registro e inicio de sesión.
- `/users`: usuarios.
- `/addresses`: direcciones.
- `/places`: lugares turísticos.
- `/guides`: guías turísticos.
- `/languages`: idiomas.
- `/guide-languages`: idiomas asociados a guías.
- `/schedules`: horarios.
- `/schedule-instances`: instancias de horario.
- `/bookings`: reservas.
- `/booking-escorts`: acompañantes de reservas.
- `/payments`: pagos.
- `/reviews`: reseñas y promedio de calificación.

Ejemplos:

```text
POST  /api/auth/register
POST  /api/auth/login
GET   /api/places
GET   /api/places/{id}
POST  /api/bookings
GET   /api/reviews/average-rating/{id}
```

La especificación y la interfaz interactiva están disponibles en:

<http://localhost:8080/api/swagger-ui/index.html>

## Estructura del proyecto

```text
.
├── apache/       # Configuración y contenedor del proxy Apache
├── backend/      # API Spring Boot y acceso a MySQL
├── frontend/     # Aplicación React/Vite
└── docker-compose.yml
```

## Persistencia

MySQL utiliza el volumen Docker `mysql_data`, por lo que los datos sobreviven
a `docker compose down`. Para borrar los datos y recrear la base, utiliza
`docker compose down -v`.

## Solución de problemas

- Si el puerto `3306`, `8080` o `8081` está ocupado, detén el proceso que lo
  utiliza o cambia el mapeo correspondiente en `docker-compose.yml`.
- Si el backend no inicia en Compose, comprueba que el healthcheck de MySQL
  haya terminado correctamente con `docker compose ps`.
- Si el frontend devuelve errores de API en desarrollo local, verifica que el
  backend esté ejecutándose en `http://localhost:8081` y que la ruta incluya
  el prefijo `/api`.
- Para reconstruir imágenes después de cambiar dependencias o Dockerfiles:

  ```bash
  docker compose up --build
  ```

