# API REST de Inventario - Spring Boot

Taller practico de Desarrollo Web Back-End.
API REST de productos con arquitectura en capas (Model, Repository, Service, Controller).

## Tecnologias
- Java 17
- Spring Boot 3 (Web, Data JPA)
- MySQL (Reto 3) / H2 (perfil `h2`)
- Lombok
- Maven

## Como correrlo
1. Tener MySQL prendido (XAMPP). La base `inventariodb` se crea sola.
2. Si el usuario root tiene clave, cambiarla en `src/main/resources/application.properties`.
3. Ejecutar `ApiProductosApplication`.
4. La API queda en `http://localhost:8080/api/productos`.

Para usar H2 en vez de MySQL: correr con `-Dspring.profiles.active=h2`
(consola en `http://localhost:8080/h2-console`, JDBC URL `jdbc:h2:mem:inventariodb`, usuario `sa`).

## Endpoints

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | /api/productos | Listar todos |
| GET | /api/productos/{id} | Buscar por id |
| GET | /api/productos/categoria/{categoria} | Filtrar por categoria |
| POST | /api/productos | Crear producto |
| PUT | /api/productos/{id} | Actualizar producto |
| DELETE | /api/productos/{id} | Eliminar producto |
| GET | /api/productos/precio-menor?precio=X | Reto 1: precio menor a X |
| PATCH | /api/productos/{id}/reducir-stock?cantidad=X | Reto 2: reducir stock (400 si no alcanza) |

## Pruebas
Las pruebas estan en `Pruebas_HTTP.http`. Las capturas estan en la carpeta `capturas/`.

## Retos
- **Reto 1:** `findByPrecioLessThan` en el repositorio + endpoint `/precio-menor`.
- **Reto 2:** endpoint PATCH que reduce el stock y devuelve 400 Bad Request si es insuficiente.
- **Reto 3:** se cambio H2 por MySQL en `application.properties`.
