# Gatling Tutorial

Ce module démontre l'utilisation de Gatling pour tester les performances de votre application Spring Boot.

## Description

Ce tutoriel présente une application Spring Boot de gestion d'auteurs avec des tests de performance utilisant Gatling. L'application permet de simuler des charges importantes sur votre API et de mesurer ses performances.

## Fonctionnalités

- API REST pour la gestion des auteurs
- Tests de performance avec Gatling
- Simulation de charge avec des données aléatoires
- Configuration des seuils de performance
- Logging personnalisé pour les tests

## Configuration de Gatling

Le projet utilise Gatling avec la configuration suivante :

```properties
# Configuration de la base de données
spring.datasource.url=jdbc:h2:mem:gatling
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
```

## Simulation de Charge

La simulation `AuthorSimulation` définit les scénarios suivants :

1. Création d'auteurs avec des données aléatoires
2. Profil de charge :
   - 50 utilisateurs au total
   - Montée en charge progressive (10 utilisateurs/20s)
   - État stable pendant 60 secondes
   - Seuils de performance :
     - Temps de réponse maximum : 10 secondes
     - Taux de succès minimum : 90%

## Structure du Projet

- `controller/` : Contient les contrôleurs REST
- `service/` : Contient la logique métier
- `repository/` : Contient les repositories JPA
- `model/` : Contient les entités JPA
- `exception/` : Contient les classes d'exception personnalisées
- `simulations/` : Contient les scénarios de test Gatling

## Comment Exécuter

1. Démarrer l'application :
```bash
mvn spring-boot:run
```

2. Exécuter les tests de performance :
```bash
mvn gatling:test
```

3. Consulter les rapports :
   - Les rapports sont générés dans le dossier `target/gatling`
   - Les logs sont disponibles dans le dossier `logs`

## Configuration des Logs

Le projet utilise une configuration de logging personnalisée avec :
- Rotation des logs journalière
- Conservation des logs pendant 30 jours
- Limite de taille totale des logs à 3GB
- Logs spécifiques pour Gatling

## Points Clés

- Utilisation de Gatling pour les tests de performance
- Génération de données aléatoires avec Faker
- Configuration des seuils de performance
- Logging personnalisé
- Tests automatisés