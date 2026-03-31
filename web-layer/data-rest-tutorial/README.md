# Tutoriel Spring Data REST

Expose automatiquement des repositories JPA en REST via Spring Data REST.

## Endpoints (base path `/api`)

- `GET /api/books`
- `GET /api/authors`
- Recherche : `GET /api/books/search/findByTitleContainingIgnoreCase?title=...`

## Outils

- H2 Console : `/h2-console`
- OpenAPI : `/api-docs`
- Swagger UI : `/swagger-ui`

## Configuration clé

- Port : `8094`

## Comment l'exécuter

```bash
mvn spring-boot:run
```
