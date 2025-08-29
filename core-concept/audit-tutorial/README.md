# Tutoriel sur l'Audit avec Spring Data Envers

Ce tutoriel montre comment mettre en place un système d'audit pour les entités JPA en utilisant Spring Data Envers. L'audit permet de conserver un historique des modifications (création, mise à jour, suppression) d'une entité, y compris qui a fait la modification et quand.

## Concepts Clés

- **Hibernate Envers**: Le module de Hibernate qui fournit la fonctionnalité d'audit. Il crée automatiquement des tables d'audit pour les entités annotées avec `@Audited`.
- **Spring Data Envers**: Une extension de Spring Data JPA qui facilite l'intégration avec Hibernate Envers. Elle fournit une interface `RevisionRepository` pour requêter l'historique des entités.
- **`@Audited`**: Annotation pour marquer une entité JPA comme devant être auditée.
- **`RevisionEntity`**: Une entité qui stocke les métadonnées d'une révision (par exemple, l'ID de la révision, le timestamp). On peut la personnaliser pour ajouter des informations supplémentaires, comme le nom de l'utilisateur.
- **`RevisionListener`**: Un listener qui est appelé à chaque fois qu'une nouvelle révision est créée. Utile pour enrichir l'entité de révision avec des informations du contexte applicatif (par exemple, l'utilisateur connecté).

## Fonctionnalités Démontrées

Ce projet met en place un système d'audit pour une entité `Stock`. Il permet de :

-   Créer et mettre à jour des stocks.
-   Conserver un historique de toutes les modifications apportées à chaque stock.
-   Enregistrer l'utilisateur qui a effectué chaque modification.
-   Exposer un endpoint `GET /stocks/{id}/history` pour consulter l'historique d'un stock.

## Structure du Code

-   **`Stock`**: L'entité JPA annotée avec `@Audited`.
-   **`StockRevisionEntity`**: Notre entité de révision personnalisée qui ajoute un champ `username` pour tracer l'auteur de la modification.
-   **`SecurityRevisionListener`**: Un `RevisionListener` qui récupère l'utilisateur connecté depuis le `SecurityContextHolder` de Spring Security et l'enregistre dans la `StockRevisionEntity`.
-   **`StockRepository`**: Une interface qui étend `JpaRepository` et `RevisionRepository` pour bénéficier à la fois des opérations CRUD et des fonctionnalités d'audit de Spring Data Envers.
-   **`StockController`**: Le contrôleur REST pour gérer les stocks et consulter leur historique.
-   **`RevisionService`**: Le service qui interroge l'historique des révisions en utilisant le `StockRepository`.
-   **`SecurityConfig`**: Configuration de base de Spring Security avec un utilisateur en mémoire pour les besoins de la démonstration.

## Comment Tester

1.  Démarrez l'application.
2.  Utilisez un outil comme `curl` ou Postman pour interagir avec l'API. Vous devez vous authentifier (Basic Auth) avec les identifiants définis dans `SecurityConfig` (par exemple, `user` / `password`).
3.  **Créez un stock :**
    ```bash
    curl -u user:password -X POST http://localhost:8080/stocks -H "Content-Type: application/json" -d '{"productName": "Laptop", "quantity": 10}'
    ```
4.  **Mettez à jour le stock :**
    ```bash
    curl -u user:password -X PUT http://localhost:8080/stocks/1/quantity -H "Content-Type: application/json" -d '{"quantity": 5}'
    ```
5.  **Consultez l'historique du stock :**
    ```bash
    curl -u user:password http://localhost:8080/stocks/1/history
    ```
    Vous devriez voir deux entrées dans l'historique, une pour la création et une pour la mise à jour, avec le nom d'utilisateur "user".