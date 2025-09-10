# Diagrama de la Solución - API RESTful de Usuarios

## Arquitectura General

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENTE (HTTP/JSON)                      │
└─────────────────────────┬───────────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                         │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │  UserController │  │ GlobalException │  │   Swagger UI    │ │
│  │   (REST API)    │  │    Handler      │  │ Documentation   │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
└─────────────────────────┬───────────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────────┐
│                     CAPA DE NEGOCIO                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │   UserService   │  │  ValidationSvc  │  │   JwtService    │ │
│  │ (Lógica Core)   │  │ (Validaciones)  │  │ (Autenticación) │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
└─────────────────────────┬───────────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────────┐
│                    CAPA DE MAPEO                                │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │   UserMapper    │  │   DTOs Request  │  │  DTOs Response  │ │
│  │  (MapStruct)    │  │   & Validation  │  │   & Error       │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
└─────────────────────────┬───────────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────────┐
│                   CAPA DE PERSISTENCIA                          │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │ UserRepository  │  │   User Entity   │  │  Phone Entity   │ │
│  │   (Spring JPA)  │  │   (JPA/UUID)    │  │   (JPA/Long)    │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
└─────────────────────────┬───────────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────────┐
│                    BASE DE DATOS H2                             │
│  ┌─────────────────┐  ┌─────────────────┐                      │
│  │  TABLA: users   │  │ TABLA: phones   │                      │
│  │  - id (UUID)    │  │ - id (BIGINT)   │                      │
│  │  - name         │  │ - number        │                      │
│  │  - email        │  │ - citycode      │                      │
│  │  - password     │  │ - contrycode    │                      │
│  │  - created      │  │ - user_id (FK)  │                      │
│  │  - modified     │  │                 │                      │
│  │  - last_login   │  │                 │                      │
│  │  - token        │  │                 │                      │
│  │  - isactive     │  │                 │                      │
│  └─────────────────┘  └─────────────────┘                      │
└─────────────────────────────────────────────────────────────────┘
```

## Flujo de Datos - Crear Usuario

```
Cliente                Controller              Service                Repository           BD
  │                        │                     │                        │              │
  │ POST /api/users        │                     │                        │              │
  │ ──────────────────────▶│                     │                        │              │
  │                        │ createUser(dto)     │                        │              │
  │                        │ ───────────────────▶│                        │              │
  │                        │                     │ validateEmail()        │              │
  │                        │                     │ validatePassword()     │              │
  │                        │                     │ existsByEmail()        │              │
  │                        │                     │ ──────────────────────▶│              │
  │                        │                     │                        │ SELECT...    │
  │                        │                     │                        │ ────────────▶│
  │                        │                     │                        │ ◀────────────│
  │                        │                     │ ◀──────────────────────│              │
  │                        │                     │ generateToken()        │              │
  │                        │                     │ save(user)             │              │
  │                        │                     │ ──────────────────────▶│              │
  │                        │                     │                        │ INSERT...    │
  │                        │                     │                        │ ────────────▶│
  │                        │                     │                        │ ◀────────────│
  │                        │                     │ ◀──────────────────────│              │
  │                        │ ◀───────────────────│                        │              │
  │ ◀──────────────────────│                     │                        │              │
  │ 201 Created + JSON     │                     │                        │              │
```

## Endpoints Implementados

| Método | Endpoint        | Descripción                    | Status Codes        |
|--------|----------------|--------------------------------|---------------------|
| POST   | /api/users     | Crear nuevo usuario            | 201, 400, 409      |
| GET    | /api/users     | Obtener todos los usuarios     | 200                 |
| GET    | /api/users/{id}| Obtener usuario por ID         | 200, 404            |
| PUT    | /api/users/{id}| Actualizar usuario completo    | 200, 400, 404, 409 |
| PATCH  | /api/users/{id}| Actualizar usuario parcial     | 200, 400, 404, 409 |
| DELETE | /api/users/{id}| Eliminar usuario               | 204, 404            |

## Patrones de Diseño Implementados

### 1. Repository Pattern
- **UserRepository**: Abstrae el acceso a datos
- **Beneficio**: Desacopla la lógica de negocio de la persistencia

### 2. Service Layer Pattern
- **UserService**: Contiene la lógica de negocio
- **ValidationService**: Maneja validaciones
- **JwtService**: Gestiona tokens JWT

### 3. DTO Pattern
- **UserRequestDto**: Para datos de entrada
- **UserResponseDto**: Para datos de salida
- **ErrorResponseDto**: Para respuestas de error

### 4. Exception Handler Pattern
- **GlobalExceptionHandler**: Manejo centralizado de errores
- **Excepciones personalizadas**: EmailAlreadyExistsException, UserNotFoundException

### 5. Dependency Injection
- **@Autowired**: Inyección de dependencias con Spring
- **@Service, @Repository, @Controller**: Estereotipos de Spring

### 6. Builder Pattern (Implícito)
- **MapStruct**: Genera builders automáticamente
- **JWT Builder**: Para construcción de tokens

## Validaciones Implementadas

### Email
- **Regex configurable**: `^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$`
- **Unicidad**: Verificación en base de datos

### Password
- **Regex configurable**: `^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}$`
- **Requisitos**: Mínimo 8 caracteres, mayúscula, minúscula, dígito

### Campos Obligatorios
- **@NotBlank**: name, email, password
- **@NotEmpty**: phones (al menos un teléfono)
- **@Valid**: Validación anidada de teléfonos

## Tecnologías y Librerías

- **Spring Boot 3.2.0**: Framework principal
- **Spring Data JPA**: Persistencia
- **H2 Database**: Base de datos en memoria
- **JWT (jjwt)**: Autenticación y autorización
- **MapStruct**: Mapeo automático de objetos
- **Swagger/OpenAPI**: Documentación de API
- **JUnit 5 + Mockito**: Pruebas unitarias
- **Maven**: Gestión de dependencias y build

## Configuración

### Base de Datos
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: 
```

### JWT
```yaml
app:
  jwt:
    secret: 'secret_key'
    expiration-ms: 86400000
```

### Validaciones
```yaml
app:
  validation:
    email-regex: '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'
    password-regex: '^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}$'
```