# Proyecto Spring Eva - API RESTful de Usuarios

API RESTful para gestión de usuarios desarrollada con Spring Boot, que implementa todos los verbos HTTP (GET, POST, PUT, PATCH, DELETE) con validaciones, JWT y documentación Swagger.

## Características

- **Framework**: Spring Boot 3.2.0
- **Java**: 17
- **Base de datos**: H2 (en memoria)
- **Persistencia**: JPA/Hibernate
- **Autenticación**: JWT
- **Documentación**: Swagger/OpenAPI
- **Validaciones**: Regex configurables
- **Mapeo**: MapStruct
- **Pruebas**: JUnit 5 + Mockito

## Estructura del Proyecto

```
src/main/java/com/proyect/eva/
├── controller/     # Controladores REST
├── dto/           # DTOs de request/response
├── entity/        # Entidades JPA
├── exception/     # Excepciones personalizadas
├── mapper/        # Mappers MapStruct
├── repository/    # Repositorios JPA
└── service/       # Lógica de negocio
```

## Cómo ejecutar

### Prerrequisitos
- Java 17+
- Maven 3.6+

### Pasos

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd eva
```

2. **Compilar el proyecto**
```bash
mvn clean compile
```

3. **Ejecutar las pruebas**
```bash
mvn test
```

4. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## Endpoints de la API

### Crear Usuario
```http
POST /api/users
Content-Type: application/json

{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}
```

### Obtener Todos los Usuarios
```http
GET /api/users
```

### Obtener Usuario por ID
```http
GET /api/users/{id}
```

### Actualizar Usuario Completo
```http
PUT /api/users/{id}
Content-Type: application/json

{
  "name": "Juan Rodriguez Updated",
  "email": "juan.updated@rodriguez.org",
  "password": "newPassword123",
  "phones": [...]
}
```

### Actualizar Usuario Parcial
```http
PATCH /api/users/{id}
Content-Type: application/json

{
  "name": "Nuevo Nombre"
}
```

### Eliminar Usuario
```http
DELETE /api/users/{id}
```

## Documentación Swagger

Accede a la documentación interactiva en:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Base de Datos H2

Consola H2 disponible en: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Usuario**: `sa`
- **Contraseña**: (vacía)

## Configuración

Las validaciones son configurables en `application.yml`:

```yaml
app:
  validation:
    email-regex: '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'
    password-regex: '^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}$'
  jwt:
    secret: 'cambiar_este_secreto_por_un_valor_mas_seguro_2025'
    expiration-ms: 86400000
```

## Respuestas de Error

Todos los errores siguen el formato:
```json
{
  "mensaje": "Descripción del error"
}
```

## Códigos de Estado HTTP

- **201**: Usuario creado exitosamente
- **200**: Operación exitosa
- **204**: Usuario eliminado
- **400**: Error de validación
- **404**: Usuario no encontrado
- **409**: Email ya registrado
- **500**: Error interno del servidor

## Patrones de Diseño Implementados

- **Repository Pattern**: Para acceso a datos
- **Service Layer**: Para lógica de negocio
- **DTO Pattern**: Para transferencia de datos
- **Exception Handler**: Para manejo centralizado de errores
- **Dependency Injection**: Con Spring Framework

## Pruebas Implementadas

### Tipos de Pruebas

#### **🧪 Pruebas Unitarias**
- **UserServiceTest**: Tests de lógica de negocio
  - ✅ `createUser_Success()` - Creación exitosa de usuario
  - ✅ `createUser_EmailAlreadyExists()` - Validación email duplicado
  - ✅ `getUserById_Success()` - Obtención de usuario por ID
  - ✅ `getUserById_NotFound()` - Usuario no encontrado
  - ✅ `deleteUser_Success()` - Eliminación exitosa
  - ✅ `deleteUser_NotFound()` - Error al eliminar usuario inexistente

#### **🏗️ Pruebas de Integración**
- **EvaApplicationTests**: Test de contexto Spring
  - ✅ `contextLoads()` - Verificación de carga de contexto
  - ✅ Configuración de beans
  - ✅ Conexión a base de datos H2
  - ✅ Inicialización de componentes

### Herramientas de Testing
- **JUnit 5**: Framework de testing moderno
- **Mockito**: Mocking de dependencias
- **Spring Boot Test**: Tests de integración
- **@ExtendWith(MockitoExtension.class)**: Integración Mockito-JUnit

### Cobertura de Pruebas
- ✅ **Capa de Servicio**: Lógica de negocio completa
- ✅ **Manejo de Excepciones**: Casos de error
- ✅ **Validaciones**: Email y password
- ✅ **Contexto Spring**: Configuración correcta

### Ejecutar Pruebas
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar con reporte detallado
mvn test -Dtest.verbose=true

# Ejecutar pruebas específicas
mvn test -Dtest=UserServiceTest
```

### Verificar Funcionamiento

Una vez levantada la aplicación con `mvn spring-boot:run`, puedes verificar que todo funciona correctamente accediendo a:

**📋 Swagger UI (Documentación Interactiva)**
```
http://localhost:8080/swagger-ui.html
```

En Swagger podrás:
- ✅ **Ver todos los endpoints** disponibles
- ✅ **Probar la API** directamente desde el navegador
- ✅ **Ver ejemplos** de request/response
- ✅ **Validar** que todos los métodos HTTP funcionan
- ✅ **Revisar** la documentación automática

**🔧 Otras URLs útiles:**
- **API Base**: `http://localhost:8080/api/users`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **H2 Console**: `http://localhost:8080/h2-console`

## Tecnologías Utilizadas

- Spring Boot 3.2.0
- Spring Data JPA
- Spring Web
- Spring Validation
- H2 Database
- JWT (jjwt)
- MapStruct
- Swagger/OpenAPI
- JUnit 5
- Mockito
- Maven
