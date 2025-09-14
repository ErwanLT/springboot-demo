# Tutoriel QueryDSL

Ce module montre comment utiliser QueryDSL pour construire des requêtes dynamiques et typées dans une application Spring Boot.

## Comment lancer l'application

Pour lancer l'application, exécutez la commande suivante depuis la racine du module `querydsl-tutorial`:

```bash
./mvnw spring-boot:run
```

L'application démarrera sur le port 8080.

## Endpoints de l'API

### Rechercher des livres

Cet endpoint vous permet de rechercher des livres en utilisant divers critères. Il supporte la pagination et le tri.

- **URL**: `/books/search`
- **Méthode**: `GET`
- **Paramètres de requête**:
    - `title` (String): Filtre par titre de livre (contient, insensible à la casse).
    - `author` (String): Filtre par nom d'auteur (correspondance exacte, insensible à la casse).
    - `minYear` (Integer): Filtre pour les livres publiés à partir de cette année.
    - `maxYear` (Integer): Filtre pour les livres publiés jusqu'à cette année.
    - `publishedAfter` (LocalDate, format: `yyyy-MM-dd`): Filtre pour les livres publiés après cette date.
    - `publishedBefore` (LocalDate, format: `yyyy-MM-dd`): Filtre pour les livres publiés avant cette date.
    - `minPrice` (Double): Filtre pour les livres avec un prix supérieur ou égal à cette valeur.
    - `maxPrice` (Double): Filtre pour les livres avec un prix inférieur ou égal à cette valeur.
    - `page` (Integer, défaut: 0): Le numéro de la page à récupérer.
    - `size` (Integer, défaut: 20): Le nombre de livres par page.
    - `sort` (String, format: `propriété,direction`): Le critère de tri (ex: `year,desc`).

### Exemples

- **Rechercher les livres de l'auteur J.R.R. Tolkien**:

```bash
curl "http://localhost:8080/books/search?author=J.R.R.%20Tolkien"
```

- **Rechercher les livres publiés après 1980, triés par année en ordre décroissant**:

```bash
curl "http://localhost:8080/books/search?minYear=1980&sort=year,desc"
```

- **Rechercher les livres avec "The" dans le titre, avec un prix maximum de 15**:

```bash
curl "http://localhost:8080/books/search?title=The&maxPrice=15"
```