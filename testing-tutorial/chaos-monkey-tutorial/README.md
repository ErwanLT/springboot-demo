# Chaos Monkey Tutorial

Ce module démontre l'utilisation de Chaos Monkey pour tester la résilience de votre application Spring Boot.

## Description

Ce tutoriel présente une application Spring Boot de gestion d'articles et d'auteurs avec l'intégration de Chaos Monkey pour simuler des pannes et des latences. L'application permet de tester la résilience de votre système face à différents scénarios de défaillance.

## Fonctionnalités

- Gestion des articles et des auteurs
- API REST avec documentation Swagger
- Tests de résilience avec Chaos Monkey
- Gestion des timeouts et des exceptions
- Tests automatisés avec Postman

## Configuration de Chaos Monkey

L'application est configurée pour utiliser Chaos Monkey avec les paramètres suivants :

```properties
spring.profiles.active=chaos-monkey
springdoc.show-actuator=true
chaos.monkey.apidoc.enabled=true
management.endpoint.chaosmonkey.enabled=true
```

## Endpoints Principaux

- `/articles` : Liste tous les articles
- `/articles/{id}` : Récupère un article par son ID
- `/actuator/chaosmonkey/*` : Endpoints de configuration de Chaos Monkey

## Tests avec Postman

Une collection Postman est fournie pour tester les fonctionnalités suivantes :

1. Activation de Chaos Monkey
2. Configuration des watchers
3. Configuration des assaults
4. Test de latence
5. Test de résilience

## Structure du Projet

- `controller/` : Contient les contrôleurs REST
- `service/` : Contient la logique métier
- `dao/` : Contient les repositories JPA
- `model/` : Contient les entités JPA
- `exception/` : Contient les classes d'exception personnalisées

## Comment Exécuter

1. Démarrer l'application :
```bash
mvn spring-boot:run
```

2. Importer la collection Postman depuis le dossier `newman/`

3. Configurer les variables d'environnement dans Postman :
   - `chaosMonkeyURL` : http://localhost:8089

4. Exécuter les tests dans l'ordre suivant :
   - Activation de Chaos Monkey
   - Configuration des watchers
   - Configuration des assaults
   - Test de latence

## Points Clés

- Utilisation de Chaos Monkey pour simuler des pannes
- Gestion des timeouts avec ExecutorService
- Documentation API avec Swagger
- Tests automatisés avec Postman
- Gestion des exceptions personnalisées
