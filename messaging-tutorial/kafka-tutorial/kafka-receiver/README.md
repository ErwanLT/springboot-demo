# Application Consommateur Kafka (kafka-receiver)

Cette application Spring Boot agit comme un **consommateur** de messages pour Apache Kafka.

## Description

Son unique rôle est d'écouter en continu un topic Kafka spécifique. Lorsqu'un message est publié dans ce topic (par l'application `kafka-sender`), cette application le consomme et l'affiche dans la console.

Elle démontre la manière la plus simple de recevoir des messages avec Spring Kafka.

## Concepts Clés

- **Spring Kafka `@KafkaListener`** : L'annotation qui transforme une simple méthode en un consommateur de messages Kafka puissant et managé par Spring.
- **Consommateur Kafka** : Le rôle de cette application dans l'écosystème Kafka.

## Fichiers Principaux

- `KafkaConsumerService.java` : Contient la méthode annotée avec `@KafkaListener` qui est automatiquement invoquée pour chaque nouveau message dans le topic.

## Comment l'exécuter

Assurez-vous que Kafka est démarré (via `docker-compose up -d` dans le dossier parent).

1.  Naviguez dans ce dossier.
2.  Exécutez `mvn spring-boot:run`.
3.  L'application va démarrer et commencer à écouter les messages. Envoyez des messages via l'application `kafka-sender` pour les voir apparaître ici.
