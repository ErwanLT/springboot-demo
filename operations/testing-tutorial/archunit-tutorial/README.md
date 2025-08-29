# ArchUnit Tutorial

Ce module démontre l'utilisation d'ArchUnit pour tester l'architecture de votre application Spring Boot.

## Description

Ce tutoriel présente une application Spring Boot simple de gestion d'auteurs avec une architecture en couches (Controller -> Service -> Repository) et montre comment utiliser ArchUnit pour vérifier que cette architecture est respectée.

## Structure du Projet

- `controller/` : Contient les contrôleurs REST
- `service/` : Contient la logique métier
- `repository/` : Contient les interfaces d'accès aux données
- `model/` : Contient les entités JPA
- `exception/` : Contient les classes d'exception personnalisées

## Tests d'Architecture

Les tests ArchUnit vérifient les règles d'architecture suivantes :

1. Les contrôleurs ne peuvent dépendre que des services, des modèles et des exceptions
2. Les services ne peuvent dépendre que des repositories, des modèles et des exceptions
3. Les repositories ne peuvent pas dépendre des services ou des contrôleurs
4. Aucune classe ne peut accéder directement aux contrôleurs

## Comment Exécuter

```bash
mvn test
```

## Points Clés

- Utilisation de `@ArchTest` pour définir les règles d'architecture
- Vérification des dépendances entre les couches
- Respect des principes de l'architecture en couches
- Gestion des exceptions personnalisées