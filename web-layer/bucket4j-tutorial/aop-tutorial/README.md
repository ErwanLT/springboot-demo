# Tutoriel : Limitation de Débit avec AOP et Bucket4j

Ce tutoriel démontre comment implémenter la limitation de débit (rate limiting) de manière déclarative en utilisant la **Programmation Orientée Aspect (AOP)** avec Spring Boot et Bucket4j.

## Description

Cette approche permet de découpler totalement la logique de limitation de débit du code métier. En créant une annotation personnalisée (`@RateLimited`), on peut facilement protéger n'importe quelle méthode sans modifier son code interne.

C'est une méthode très propre et maintenable pour appliquer des règles transversales.

## Concepts Clés

- **Spring AOP** : Utilisation d'un `Aspect` pour intercepter les appels de méthodes.
- **Annotation Personnalisée** : Création de `@RateLimited` pour marquer les méthodes à protéger.
- **Bucket4j** : Bibliothèque utilisée pour gérer les "seaux" de jetons qui déterminent si une requête est autorisée.

## Fichiers Principaux

- `RateLimitingAspect.java` : Le cœur de l'implémentation. Cet aspect contient la logique qui vérifie le "seau" de jetons avant d'autoriser l'exécution de la méthode annotée.
- `RateLimited.java` : La définition de l'annotation personnalisée `@RateLimited`.
- `HelloController.java` : Un exemple de contrôleur avec une méthode annotée pour démontrer l'utilisation.
