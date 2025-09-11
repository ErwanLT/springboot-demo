# R2DBC Tutorial

Ce tutoriel démontre comment utiliser R2DBC (Reactive Relational Database Connectivity) avec Spring Data R2DBC pour interagir avec une base de données relationnelle de manière réactive.

## Fonctionnalités

-   **Programmation Réactive** : Utilise le projet Reactor (`Mono`, `Flux`) pour des opérations de base de données non-bloquantes.
-   **Spring Data R2DBC** : Le `PersonRepository` étend `ReactiveCrudRepository` pour fournir des méthodes de CRUD réactives.
-   **Base de données en mémoire H2** : Le projet est configuré pour utiliser une base de données H2 en mémoire avec R2DBC.
-   **Endpoints fonctionnels avec WebFlux** : L'application expose des endpoints REST en utilisant les `RouterFunctions` et `HandlerFunctions` de Spring WebFlux, une approche fonctionnelle pour définir des routes.
-   **Tests d'intégration réactifs** : La classe `PersonRouterTest` utilise `@WebFluxTest` et `WebTestClient` pour tester les endpoints de manière non-bloquante.

## Comment l'exécuter

Vous pouvez lancer l'application en exécutant la méthode `main` de la classe `R2dbcApplication`.

L'application expose les endpoints suivants :
-   `GET /api/persons` : Récupère toutes les personnes.
-   `GET /api/persons/{id}` : Récupère une personne par son ID.
-   `POST /api/persons` : Crée une nouvelle personne.

**Exemple de requête POST :**
```json
{
  "name": "John Doe",
  "age": 30
}
```
