# Tutoriel Thymeleaf avec Spring Boot

Ce tutoriel montre comment utiliser Thymeleaf comme moteur de templates pour construire une application web simple de gestion de bibliothèque avec Spring Boot.

## Description

L'application permet de gérer une liste d'auteurs et de livres. Elle démontre les fonctionnalités clés de l'intégration de Thymeleaf avec Spring, notamment :

-   L'affichage de listes d'objets.
-   La gestion de formulaires pour la création et la mise à jour d'entités.
-   La validation des données de formulaire.
-   La navigation entre les pages.
-   L'utilisation d'expressions Thymeleaf pour la logique de présentation.

## Fonctionnalités

-   Lister tous les auteurs et leurs livres.
-   Rechercher des auteurs par nom.
-   Voir les détails d'un auteur.
-   Ajouter et supprimer des auteurs.
-   Ajouter et supprimer des livres pour un auteur spécifique.

## Implémentation

-   **`spring-boot-starter-thymeleaf`**: La dépendance clé qui auto-configure Thymeleaf.
-   **`LibraryController`**: Un contrôleur Spring MVC qui gère les requêtes web, prépare les données dans un `Model` et retourne le nom de la vue (le template Thymeleaf) à afficher.
-   **Templates HTML**: Les fichiers HTML dans `src/main/resources/templates` utilisent des attributs Thymeleaf (comme `th:each`, `th:text`, `th:href`, `th:object`) pour interagir avec les données du modèle.

## Comment l'exécuter

1.  Lancez l'application Spring Boot.
2.  Ouvrez votre navigateur et accédez à `http://localhost:8080`.
3.  Vous serez redirigé vers la page listant les auteurs, qui est le point d'entrée de l'application.