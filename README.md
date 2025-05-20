# Prueba Técnica Spring Boot API

## Descripción
API REST para gestionar personas y sus películas favoritas.  
Cumple con los requisitos técnicos usando Spring Boot, OpenAPI/Swagger, validaciones y almacenamiento en memoria.

## 🧰 Tecnologías usadas
- Java 21
- Spring Boot 3.x
- Lombok
- SpringDoc OpenAPI (Swagger UI)
- Validaciones con JSR 380
- Almacenamiento en memoria

## Cómo correr

1. Clona este repo
2. Ejecuta: `mvn spring-boot:run`
3. Accede a: `http://localhost:8080/swagger-ui/index.html`


## 📁 Estructura del Proyecto
    
    src/
    └── main/
        ├── java/
        │   └── com/ejemplo/personas_api/
        │       ├── controller/
        │       ├── DTOs/
        │       ├── mapper/
        │       ├── model/
        │       ├── repository/
        │       ├── service/
        │       └── PersonasApiApplication.java
        └── resources/
            └── application.properties


## ✅ Funcionalidades Implementadas

### Personas

- `GET /personas`: Listar todas las personas (ordenadas por apellido, nombre)
- `GET /personas/{id}`: Buscar una persona por ID
- `GET /personas/search?name=nombre`: Buscar personas por nombre
- `POST /personas`: Crear una nueva persona
- `PATCH /personas/{id}`: Modificar una persona (solo campos enviados)
- `DELETE /personas/{id}`: Eliminar una persona

### Películas favoritas de una persona

- `GET /personas/{id}/movies`: Mostrar las películas de una persona
- `POST /personas/{id}/movies`: Agregar una película a una persona (respetando el límite configurado)
- `DELETE /personas/{id}/movies?title=nombre`: Quitar una película por título

---


#### Configuración:
    ```properties
    person.max-movies=5


## 🧾 Autor
Miguel

GitHub: @punklodawn

