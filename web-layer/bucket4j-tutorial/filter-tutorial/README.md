# Tutoriel : Limitation de Débit avec un Filtre et Bucket4j

Ce tutoriel explique comment mettre en place la limitation de débit (rate limiting) en utilisant un **Filtre de Servlet (Servlet Filter)** dans une application Spring Boot avec Bucket4j.

## Description

L'utilisation d'un filtre est une approche classique et robuste pour appliquer une logique transversale à un ensemble de requêtes HTTP. Le filtre intercepte les requêtes entrantes avant qu'elles n'atteignent le contrôleur, applique les règles de limitation de débit, et bloque la requête si nécessaire.

Cette méthode est idéale pour appliquer des règles de limitation globales (par exemple, par adresse IP) à toute ou partie de votre API.

## Concepts Clés

- **Servlet Filter** : Un composant standard de l'API Servlet pour intercepter et traiter les requêtes.
- **Bucket4j** : La bibliothèque utilisée pour gérer les "seaux" de jetons.
- **Configuration Spring** : Enregistrement du filtre dans le contexte de l'application.

## Fichiers Principaux

- `RateLimitingFilter.java` : L'implémentation du filtre. Il contient la logique pour identifier l'origine de la requête (par exemple, par IP), vérifier le "seau" correspondant et retourner une erreur `429 Too Many Requests` si la limite est dépassée.
- `HelloController.java` : Le contrôleur qui sera protégé par le filtre, sans qu'il n'ait connaissance de la logique de limitation.
