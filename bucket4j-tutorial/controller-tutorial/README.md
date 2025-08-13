# Tutoriel : Limitation de Débit dans un Controller avec Bucket4j

Ce tutoriel montre comment implémenter la limitation de débit (rate limiting) directement au sein d'un **contrôleur Spring REST** en utilisant la bibliothèque Bucket4j.

## Description

Cette méthode est une approche directe et simple pour protéger des endpoints spécifiques. La logique de limitation est gérée dans une classe de service qui est ensuite appelée par le contrôleur avant d'exécuter la logique métier principale.

C'est une bonne approche pour commencer ou lorsque les règles de limitation sont simples et directement liées au endpoint.

## Concepts Clés

- **Bucket4j API** : Utilisation directe de l'API de Bucket4j pour créer et consommer des "seaux" de jetons.
- **Spring `@RestController`** : Le contrôleur qui gère les requêtes HTTP.
- **Service Layer** : La logique de gestion des "seaux" est encapsulée dans un service (`BucketService`).

## Fichiers Principaux

- `HelloController.java` : Le contrôleur REST qui reçoit les requêtes et appelle le `BucketService` pour vérifier si la requête est autorisée.
- `BucketService.java` : Le service qui gère la création et la consommation des "seaux" de jetons Bucket4j.
