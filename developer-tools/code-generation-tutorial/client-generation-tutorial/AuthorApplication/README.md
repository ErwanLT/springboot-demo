# Author Application

Application Spring Boot qui expose une API d'auteurs et consomme l'API des livres via un client généré OpenAPI.

## Endpoints

- `GET /authors`
- `GET /authors/{id}`
- `POST /authors`
- `PUT /authors/{id}`
- `DELETE /authors/{id}`

## Configuration clé

- Port : `8091`
- URL du service livres : `bookManagement.url=http://localhost:8092`
- Choix du client : `books.client=feign` ou `books.client=http-exchange`

## Comment l'exécuter

```bash
mvn spring-boot:run
```
