# Tutoriel HATEOAS avec Spring Boot

Ce tutoriel montre comment implémenter HATEOAS (Hypermedia as the Engine of Application State) dans une application Spring Boot pour créer des API REST auto-découvrables.

## Description

HATEOAS enrichit les réponses de l'API avec des liens hypermédia qui guident le client sur les actions possibles suivantes. Par exemple, une réponse pour un "auteur" pourrait contenir des liens pour voir tous ses "articles" ou pour supprimer cet auteur.

Cette approche découple le client du serveur en évitant au client d'avoir à coder en dur les URI de l'API.

## Implémentation

L'implémentation repose sur plusieurs composants clés de Spring HATEOAS :

1.  **`RepresentationModel`**: Les classes de modèle (`Article`, `Author`) étendent `RepresentationModel<T>` pour pouvoir y attacher des liens.

2.  **`WebMvcLinkBuilder`**: Une classe utilitaire qui permet de créer des liens de manière sûre en se basant sur les méthodes des contrôleurs. Cela évite de coder en dur les URI.
    ```java
    linkTo(methodOn(AuthorController.class).getAuthorById(authorId)).withSelfRel();
    ```

3.  **Service et Contrôleur**: Le service est responsable d'ajouter les liens aux objets de modèle avant de les retourner. Le contrôleur reste simple et se concentre sur la gestion des requêtes HTTP.

## Exemple de Réponse JSON

Voici à quoi ressemble une réponse pour un auteur. Notez la présence du champ `_links` qui contient les actions possibles.

```json
{
  "id": 1,
  "name": "J.K. Rowling",
  "bio": "Auteure de la série Harry Potter.",
  "articles": [
    {
      "id": 1,
      "title": "Harry Potter à l'école des sorciers",
      "content": "Le premier livre de la série.",
      "_links": {
        "self": {
          "href": "http://localhost:8080/articles/1"
        },
        "articles": {
          "href": "http://localhost:8080/articles"
        },
        "delete": {
          "href": "http://localhost:8080/articles/1",
          "type": "DELETE"
        }
      }
    }
  ],
  "_links": {
    "self": {
      "href": "http://localhost:8080/authors/1"
    },
    "authors": {
      "href": "http://localhost:8080/authors"
    },
    "delete": {
      "href": "http://localhost:8080/authors/1",
      "type": "DELETE"
    }
  }
}
```

## Comment l'exécuter

1.  Lancez l'application Spring Boot.
2.  Utilisez un client REST comme `curl` ou Postman pour interroger les endpoints `/articles` et `/authors`.

```bash
# Obtenir tous les auteurs avec les liens HATEOAS
curl http://localhost:8080/authors

# Obtenir un auteur spécifique
curl http://localhost:8080/authors/1
```