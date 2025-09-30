# Tutoriel sur la Programmation Réactive avec Spring WebFlux

Ce tutoriel montre comment construire une application web réactive et non-bloquante avec **Spring WebFlux**.

## Description

L'application utilise `WebClient` pour interroger de manière non-bloquante une API externe (JSONPlaceholder) et retourner les résultats. Elle démontre les concepts fondamentaux de la programmation réactive avec Spring.

## Concepts Clés

-   **Spring WebFlux**: Le module de Spring pour les applications web réactives. Il fournit un modèle de programmation non-bloquant qui peut gérer un grand nombre de connexions concurrentes avec un nombre limité de threads.

-   **Project Reactor**: La bibliothèque de programmation réactive utilisée par WebFlux. Elle introduit les types `Mono` (pour 0 ou 1 élément) et `Flux` (pour 0 à N éléments).

-   **`WebClient`**: Le client HTTP réactif de Spring, utilisé pour effectuer des requêtes réseau de manière non-bloquante.

-   **`Schedulers`**: Dans la programmation réactive, il est crucial de ne pas bloquer l'event loop. Pour les opérations qui sont intrinsèquement bloquantes (comme des appels à des librairies non-réactives ou des opérations I/O bloquantes), on peut utiliser un `Scheduler` (comme `Schedulers.boundedElastic()`) pour déléguer ce travail à un thread séparé, préservant ainsi la nature non-bloquante de l'application.

## Endpoints

-   `GET /api/posts`: Récupère une liste de posts de manière réactive et la retourne comme un `Flux<Post>`.
-   `GET /api/posts/{id}`: Récupère un seul post par son ID et le retourne comme un `Mono<Post>`.
-   `GET /api/posts/{id}/enriched`: Récupère un post, puis applique une opération de transformation (simulée comme étant bloquante) sur un thread séparé pour ne pas bloquer l'event loop principal.

## Comment l'exécuter

1.  Lancez l'application Spring Boot.
2.  Utilisez un client HTTP pour interroger les endpoints.

```bash
# Récupérer tous les posts
curl http://localhost:8080/api/posts

# Récupérer un post spécifique
curl http://localhost:8080/api/posts/1

# Récupérer un post enrichi (avec une latence simulée)
curl http://localhost:8080/api/posts/1/enriched
```
