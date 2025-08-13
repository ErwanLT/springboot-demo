# Tutoriels Bucket4j

Ce module parent regroupe plusieurs approches pour implémenter la **limitation de débit (rate limiting)** dans une application Spring Boot en utilisant la bibliothèque **Bucket4j**.

## Description

La limitation de débit est une technique essentielle pour protéger vos API contre les surcharges, les abus et garantir une qualité de service équitable pour tous les utilisateurs. Bucket4j est une bibliothèque Java puissante et efficace pour implémenter cette fonctionnalité.

Ce projet explore trois méthodes distinctes pour intégrer Bucket4j.

## Sous-modules

### [Controller-based (controller-tutorial)](controller-tutorial)
Une approche directe où la logique de limitation de débit est implémentée directement au sein du contrôleur Spring. Simple et efficace pour des cas d'usage ciblés.

### [Filter-based (filter-tutorial)](filter-tutorial)
Une méthode classique utilisant un **Servlet Filter** pour intercepter les requêtes en amont et appliquer une limitation de débit globale ou basée sur des règles spécifiques.

### [AOP-based (aop-tutorial)](aop-tutorial)
Une approche élégante et déclarative utilisant la **Programmation Orientée Aspect (AOP)**. Une simple annotation (`@RateLimited`) sur une méthode suffit pour activer la limitation de débit, séparant ainsi complètement cette préoccupation technique du code métier.
