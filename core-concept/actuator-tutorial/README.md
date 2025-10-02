# Tutoriel sur Spring Boot Actuator

Ce tutoriel montre comment utiliser **Spring Boot Actuator** pour surveiller et gérer une application Spring Boot. Il couvre l'activation des endpoints par défaut, leur configuration, et la création d'un endpoint personnalisé.

## Concepts Clés

- **Spring Boot Actuator**: Fournit des fonctionnalités prêtes à l'emploi pour la production (monitoring, métriques, état de santé, etc.) en exposant des endpoints spécifiques.

- **Endpoints**: Des points d'accès (généralement REST) qui exposent des informations sur l'application. Les plus courants sont `health`, `info`, `metrics`, `beans`, etc.

- **Configuration (`application.properties`)**: Permet de contrôler finement les endpoints à exposer, leur chemin d'accès, et leur comportement. La propriété `management.endpoints.web.exposure.include` est essentielle pour choisir les endpoints à activer.

- **Endpoint Personnalisé**: Actuator peut être étendu avec vos propres endpoints pour exposer des informations ou des opérations spécifiques à votre domaine métier.
    - `@Endpoint(id = "...")`: Définit un nouvel endpoint.
    - `@ReadOperation`: Mappe une méthode à une requête `GET` sur l'endpoint.
    - `@WriteOperation`: Mappe une méthode à une requête `POST` sur l'endpoint.

## Fonctionnalités Démontrées

- **Exposition des Endpoints**: Activation de tous les endpoints Actuator via `management.endpoints.web.exposure.include=*`.
- **Chemin de Base Personnalisé**: Le chemin d'accès aux endpoints Actuator est configuré sur `/manage`.
- **Endpoint `info`**: Personnalisation de l'endpoint `info` avec des informations sur l'application via les propriétés `info.app.*`.
- **Endpoint `health`**: Configuration pour toujours afficher les détails complets de l'état de santé.
- **Endpoint Personnalisé `libraryStat`**: Création d'un endpoint sur `/manage/libraryStat` qui :
    - En lecture (`GET`), retourne des statistiques sur une bibliothèque (nombre de livres, etc.).
    - En écriture (`POST`), simule une action comme la purge d'un cache.

## Comment Tester

1.  Démarrez l'application. Le serveur tourne sur le port `8080`.

2.  Utilisez un outil comme `curl` pour interroger les différents endpoints.

    - **Consulter l'état de santé détaillé :**
      ```bash
      curl http://localhost:8080/manage/health
      ```

    - **Consulter les informations de l'application :**
      ```bash
      curl http://localhost:8080/manage/info
      ```

    - **Consulter le endpoint personnalisé (lecture) :**
      ```bash
      curl http://localhost:8080/manage/libraryStat
      ```

    - **Utiliser le endpoint personnalisé (écriture) :**
      ```bash
      curl -X POST -H "Content-Type: application/json" http://localhost:8080/manage/libraryStat
      ```
      *Cette commande retournera un message de confirmation.*

    - **Accéder à l'API principale de l'application pour voir les données de base :**
      ```bash
      curl http://localhost:8080/books
      ```
