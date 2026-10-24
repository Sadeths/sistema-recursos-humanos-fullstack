# Sistema de Recursos Humanos

Aplicación web full stack para administrar empleados. Permite listar, registrar, editar y eliminar empleados, con persistencia en MySQL mediante una API REST desarrollada con Spring Boot y una interfaz desarrollada con React.

## Funcionalidades comprobadas en el código

- Listado de empleados.
- Registro de empleados.
- Consulta de un empleado por identificador.
- Edición de nombre, departamento y sueldo.
- Eliminación de empleados.
- Formato monetario del sueldo en la tabla.
- Persistencia mediante Spring Data JPA y MySQL.
- Navegación entre listado, alta y edición con React Router.

## Arquitectura

```text
RecursosHumanos/
├── recursos-humanos-app/        # Frontend React
│   ├── public/                  # Plantilla HTML y recursos públicos
│   ├── src/
│   │   ├── empleados/           # Listado, alta y edición
│   │   ├── plantilla/           # Barra de navegación
│   │   ├── App.js               # Rutas de la interfaz
│   │   └── index.js             # Punto de entrada de React
│   ├── package.json
│   └── package-lock.json
└── recursos-humanos-spring/     # Backend Spring Boot
    ├── .mvn/                    # Configuración de Maven Wrapper
    ├── src/main/java/st/rh/
    │   ├── controlador/         # API REST de empleados
    │   ├── excepcion/           # Respuesta 404 para recursos inexistentes
    │   ├── modelo/              # Entidad JPA Empleado
    │   ├── repositorio/         # Repositorio Spring Data JPA
    │   ├── servicio/            # Lógica de acceso a empleados
    │   └── RhApplication.java   # Punto de entrada de Spring Boot
    ├── src/main/resources/      # Configuración local y logging
    ├── src/test/                # Prueba de carga del contexto
    ├── mvnw / mvnw.cmd
    └── pom.xml
```

## Tecnologías declaradas

### Backend

- Java 25.
- Spring Boot 4.1.1.
- Spring Web MVC.
- Spring Data JPA.
- MySQL Connector/J.
- Lombok 1.18.48.
- Maven Wrapper con Maven 3.9.16.

### Frontend

- React 19.3.0 y React DOM 19.3.0.
- React Router DOM 7.18.4.
- Axios 1.20.0.
- React Number Format 5.4.5.
- React Scripts 5.0.1.
- Bootstrap 5.3.8 cargado desde CDN.

> Las versiones anteriores son las que están declaradas actualmente en `pom.xml`, `package.json` y la plantilla HTML. `package.json` no fija una versión concreta de Node.js.

## Requisitos

- JDK 25, porque `pom.xml` compila con `java.version=25`.
- MySQL disponible en el puerto configurado localmente (normalmente `3306`).
- Node.js y npm.
- Conexión a Internet la primera vez que Maven o npm necesiten descargar dependencias.

Comprueba las herramientas antes de iniciar:

```bash
java -version
node --version
npm --version
```

El comando `java -version` debe mostrar Java 25. Con una versión inferior, Maven no podrá compilar el nivel de lenguaje solicitado por el proyecto.

## Configuración de MySQL

El archivo real `recursos-humanos-spring/src/main/resources/application.properties` está excluido de Git porque contiene credenciales locales. Debe existir en cada equipo, pero no debe publicarse.

Crea o ajusta ese archivo con tus propios datos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recursos_humanos_db?createDatabaseIfNotExist=true
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASENA
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

La opción `createDatabaseIfNotExist=true` permite que MySQL cree la base indicada si el usuario tiene permisos. Hibernate está configurado para actualizar el esquema a partir de la entidad `Empleado`.

## Ejecución local

### 1. Iniciar MySQL

Asegúrate de que el servidor MySQL esté activo y que las credenciales de `application.properties` sean válidas.

### 2. Iniciar el backend

Desde la raíz del proyecto:

```bash
cd recursos-humanos-spring
./mvnw spring-boot:run
```

En Windows:

```powershell
cd recursos-humanos-spring
.\mvnw.cmd spring-boot:run
```

La API usa el puerto predeterminado de Spring Boot: `http://localhost:8080`.

### 3. Iniciar el frontend

En otra terminal, desde la raíz del proyecto:

```bash
cd recursos-humanos-app
npm ci
npm start
```

La interfaz se abre en `http://localhost:3000`.

## Integración entre frontend y backend

El frontend consume directamente `http://localhost:8080/rh-app/empleados`. El controlador del backend permite CORS únicamente para `http://localhost:3000`. Para que la aplicación funcione sin cambios, deben mantenerse esos puertos durante el desarrollo local.

Flujo de ejecución:

```text
Navegador (React :3000)
        │ Axios / JSON
        ▼
API REST (Spring Boot :8080)
        │ Spring Data JPA
        ▼
MySQL (:3306)
```

## Rutas de la interfaz

| Ruta | Vista |
| --- | --- |
| `/` | Listado de empleados |
| `/agregar` | Formulario de alta |
| `/editar/:id` | Formulario de edición |

## API REST

URL base: `http://localhost:8080/rh-app/empleados`

| Método | Ruta | Acción |
| --- | --- | --- |
| `GET` | `/rh-app/empleados` | Lista todos los empleados |
| `POST` | `/rh-app/empleados` | Crea un empleado |
| `GET` | `/rh-app/empleados/{id}` | Obtiene un empleado por ID |
| `PUT` | `/rh-app/empleados/{id}` | Actualiza un empleado |
| `DELETE` | `/rh-app/empleados/{id}` | Elimina un empleado |

Ejemplo de cuerpo JSON para crear o actualizar:

```json
{
  "nombre": "Nombre del empleado",
  "departamento": "Departamento",
  "sueldo": 1000.00
}
```

La entidad usa `idEmpleado` como identificador autogenerado. Las consultas o modificaciones de un ID inexistente responden con HTTP `404`. La eliminación exitosa devuelve `{"eliminado": true}`.

## Verificación

Backend:

```bash
cd recursos-humanos-spring
./mvnw test
```

La prueba existente carga el contexto completo de Spring, por lo que necesita una configuración de MySQL válida.

Frontend:

```bash
cd recursos-humanos-app
npm test -- --watchAll=false
npm run build
```

