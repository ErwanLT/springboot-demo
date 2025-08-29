# Limitation de Débit (Rate Limiting) avec Bucket4j

Ce module regroupe plusieurs approches pour implémenter la **limitation de débit (rate limiting)** dans une application Spring Boot en utilisant la bibliothèque **Bucket4j**.

## [Avec un filtre (Filter)](filter-tutorial)
Approche robuste utilisant un **Filtre de Servlet** pour appliquer des règles de limitation globales (par exemple, par adresse IP) avant que la requête n'atteigne le contrôleur.

## [Avec l'AOP](aop-tutorial)
Méthode déclarative et propre utilisant la **Programmation Orientée Aspect** pour protéger des méthodes spécifiques avec une simple annotation, sans modifier le code métier.

## [Dans le contrôleur (Controller)](controller-tutorial)
Approche directe où la logique de limitation est appelée explicitement depuis le **contrôleur**, idéale pour des règles simples et spécifiques à un endpoint.
