# Tutoriel Spring Modulith

## Introduction

Ce projet est un exemple d'application Spring Boot structurée en utilisant [Spring Modulith](https://spring.io/projects/spring-modulith). Il a pour but de démontrer comment construire un "modulithe" : une application monolithique avec des frontières de modules claires et vérifiables.

Cette approche combine la simplicité de développement et de déploiement d'un monolithe avec les avantages de la modularité, en s'inspirant fortement des principes du Domain-Driven Design (DDD).

## Modules

L'application est divisée en deux modules principaux, représentant des "Bounded Contexts" distincts :

-   **`product`**: Ce module est responsable de la logique métier liée aux produits.
-   **`notification`**: Ce module gère l'envoi de notifications.

## Communication Inter-Modules

La communication entre les modules `product` et `notification` est asynchrone et se fait via le mécanisme d'événements de Spring.

1.  Le `ProductService` (dans le module `product`) ne dépend pas directement du module de notification.
2.  Lorsqu'un produit est créé, `ProductService` publie un événement (`NotificationDTO`) en utilisant `ApplicationEventPublisher`.
3.  Le `NotificationService` (dans le module `notification`) écoute cet événement grâce à l'annotation `@EventListener` et traite la notification.

Ce découplage fort est l'un des principaux avantages de l'approche Modulith.

## Fonctionnalités de Spring Modulith

Ce projet met en évidence plusieurs fonctionnalités clés de Spring Modulith :

### 1. Vérification de la Structure Modulaire

Le fichier `ModulithTest.java` contient des tests qui valident l'architecture modulaire :
-   `verifiesModularStructure()`: Ce test vérifie que les dépendances entre les modules sont conformes aux règles établies (par exemple, pas de dépendances cycliques ou de dépendances non autorisées). Si une dépendance non souhaitée est introduite dans le code, ce test échouera.

Pour lancer la vérification, exécutez les tests Maven :
```bash
mvn clean verify
```

### 2. Documentation des Modules

Spring Modulith peut générer automatiquement de la documentation sur la structure de l'application.
-   `createModuleDocumentation()`: Ce test génère des diagrammes PlantUML décrivant les modules et leurs relations.

Après avoir exécuté les tests, la documentation est disponible dans le répertoire `target/spring-modulith-docs`. Vous y trouverez :
-   Un diagramme C4 global.
-   Des diagrammes PlantUML pour chaque module.

## Comment lancer l'application

Vous pouvez lancer l'application de plusieurs manières :

-   Via la classe `Application.java` dans votre IDE.
-   En utilisant Maven :
    ```bash
    mvn spring-boot:run
    ```

Au démarrage, l'application crée un produit, ce qui déclenche l'envoi d'une notification que vous pouvez voir dans les logs.
