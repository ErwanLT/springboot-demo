# Tutoriel Design Patterns

CRUD de livres mettant en avant plusieurs patterns (Builder, Factory, Strategy) et un aspect de logging.

## Endpoints

- `POST /books` (params `title`, `author`, `type`)
- `GET /books`
- `GET /books/{id}`
- `PUT /books/{id}` (params `title`, `author`, `type`)
- `DELETE /books/{id}`

## Comment l'exécuter

```bash
mvn spring-boot:run
```
