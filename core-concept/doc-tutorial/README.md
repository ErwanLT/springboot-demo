# Tutoriel : Documentation d'API avec SpringDoc OpenAPI

Ce tutoriel montre comment documenter une API REST dans une application Spring Boot en utilisant la bibliothèque **springdoc-openapi**. Il génère automatiquement une spécification OpenAPI 3 et une interface utilisateur interactive (Swagger UI).

## Concepts Clés

- **SpringDoc OpenAPI**: Une bibliothèque qui inspecte une application Spring Boot à l'exécution pour inférer la spécification de l'API à partir des configurations Spring, des annotations de classe et des annotations OpenAPI 3. La dépendance clé est `springdoc-openapi-starter-webmvc-ui`.

- **Swagger UI**: `springdoc-openapi` intègre automatiquement Swagger UI, qui fournit une interface web interactive permettant aux développeurs et aux consommateurs de l'API de visualiser et d'interagir avec les endpoints de l'API sans avoir besoin d'implémenter la logique côté client.

- **Annotations OpenAPI 3 (`io.swagger.v3.oas.annotations`)**: Des annotations standard pour enrichir la documentation générée avec des détails sémantiques.
    - `@Tag`: Pour regrouper des opérations (endpoints) sous une même catégorie (ex: "Book management").
    - `@Operation`: Pour décrire une opération spécifique (ex: "Récupérer tous les livres").
    - `@ApiResponses` & `@ApiResponse`: Pour documenter les différentes réponses HTTP possibles (succès, erreurs) avec leurs schémas de données.
    - `@Parameter`: Pour décrire un paramètre d'une opération (ex: un ID dans le path).

## Fonctionnalités Démontrées

- Intégration de la dépendance `springdoc-openapi-starter-webmvc-ui` (déclarée dans le `pom.xml` racine du projet).
- Documentation complète d'un `RestController` pour les opérations CRUD sur une entité `Book`.
- Annotation détaillée de chaque endpoint pour spécifier son résumé, sa description, ses paramètres et ses réponses possibles (y compris les codes d'erreur comme 404 ou 500).
- Utilisation de `@Schema` pour lier les réponses d'erreur à la classe `ProblemDetail` pour une documentation précise des formats d'erreur.

## Comment l'exécuter et le tester

1.  Démarrez l'application `DocTutorial`. Le serveur tourne sur le port `8080`.

2.  Ouvrez votre navigateur et accédez à l'interface Swagger UI pour explorer l'API de manière interactive. Vous pourrez y voir tous vos endpoints, leurs descriptions, et même les tester directement.
    ```
    http://localhost:8080/swagger-ui.html
    ```

3.  Vous pouvez également consulter la spécification OpenAPI 3 brute au format JSON, qui est la source de données pour Swagger UI, à l'adresse suivante :
    ```
    http://localhost:8080/v3/api-docs
    ```
