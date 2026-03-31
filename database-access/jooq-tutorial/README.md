# Tutoriel jOOQ

Tutoriel Spring Boot + jOOQ avec base H2 et migrations Flyway.

## Description

- H2 en mode fichier : `jdbc:h2:file:./target/jooqdb`
- Migrations Flyway dans `src/main/resources/db/migration`
- jOOQ configuré en dialecte H2

## Endpoints

- `GET /books` : liste des livres
- `GET /books/{id}` : détail d'un livre
- `POST /books` : création d'un livre
- `GET /books/search?author=...` : recherche par auteur

## Comment l'exécuter

```bash
mvn spring-boot:run
```
