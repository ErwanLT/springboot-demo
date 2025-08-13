# Application Consommateur Kafka (kafka-receiver)

Cette application Spring Boot agit comme un **consommateur** de messages pour Apache Kafka.

## Description

Son unique rôle est d'écouter en continu un topic Kafka spécifique. Lorsqu'un message est publié dans ce topic (par l'application `kafka-sender`), cette application le consomme et l'affiche dans la console.

Elle démontre la manière la plus simple de recevoir des messages avec Spring Kafka.

## Concepts Clés

-   **Spring Kafka `@KafkaListener`** : L'annotation qui transforme une simple méthode en un consommateur de messages Kafka puissant et managé par Spring.
-   **Consommateur Kafka** : Le rôle de cette application dans l'écosystème Kafka.
-   **Dead Letter Queue (DLQ)** : Un mécanisme pour gérer les messages en erreur, les redirigeant vers un topic dédié (`my-topic.DLT`).

## Fichiers Principaux

-   `KafkaConsumerService.java` : Contient la méthode annotée avec `@KafkaListener` qui est automatiquement invoquée pour chaque nouveau message dans le topic.
-   `KafkaConsumerConfig.java` : Configure le `ConcurrentKafkaListenerContainerFactory` et le `DefaultErrorHandler` pour la gestion des DLQ.

## Comment l'exécuter

Assurez-vous que Kafka est démarré (via `docker-compose up -d` dans le dossier parent).

1.  Naviguez dans ce dossier.
2.  Exécutez `mvn spring-boot:run`.
3.  L'application va démarrer et commencer à écouter les messages. Envoyez des messages via l'application `kafka-sender` pour les voir apparaître ici.
    *   Envoyez un message normal (ex: `{"message": "Hello Kafka!"}`) : Il sera traité normalement.
    *   Envoyez un message contenant "erreur" (ex: `{"message": "Ceci est une erreur"}`) : Il sera loggé comme une erreur par le consommateur principal et **vous verrez un log indiquant qu'il a été reçu par le listener de la DLQ**.
