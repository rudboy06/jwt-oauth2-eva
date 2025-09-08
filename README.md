# jwt-oauth2-eval

Proyecto base **Spring Boot 3** con **JWT**, **Spring Security**, **H2**, **JPA** y endpoints de ejemplo.

## Requisitos
- Java 17+
- Maven 3.8+

## Cómo ejecutar
```bash
mvn clean spring-boot:run
# o
mvn -U clean package
java -jar target/jwt-oauth2-eval-0.0.1-SNAPSHOT.jar
```

La app levanta en `http://localhost:8080`.

## Login (obtener JWT)
- **POST** `/api/auth/login`
- Body:
```json
{ "username": "user", "password": "password" }
```
- Respuesta:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresInSeconds": 900
}
```

Usa el token en los demás endpoints con:
```
Authorization: Bearer <accessToken>
```

## Endpoints
### 1) Items (H2 + JPA)
- **POST** `/api/items/search`
- Body (filtrar por nombre):
```json
{ "nombre": "Israel" }
```
- Body (todo el catálogo):
```json
{ "nombre": "" }
```

### 2) Pokémon (proxy a API externa)
- **GET** `/api/pokemon/ditto`

### 3) Cripto (AES/CBC/PKCS5Padding)
- **POST** `/api/crypto/encrypt`
- Body:
```json
{ "text": "hola mundo" }
```

## Configuración (`src/main/resources/application.yml`)
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create
    defer-datasource-initialization: true
  sql:
    init:
      mode: always
  h2:
    console:
      enabled: true
      path: /h2-console

security:
  jwt:
    secret: "mysupersecretkeymysupersecretkey"
    expiration-minutes: 15
```

## cURL rápido
```bash
# 1) Login
curl -s -X POST http://localhost:8080/api/auth/login   -H "Content-Type: application/json"   -d "{"username":"user","password":"password"}"

# 2) Items
curl -s -X POST http://localhost:8080/api/items/search   -H "Content-Type: application/json"   -H "Authorization: Bearer <TOKEN>"   -d "{"nombre":"Israel"}"
```

## H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC: `jdbc:h2:mem:testdb`
- User: `sa` (sin password)
