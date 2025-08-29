# Application Producer Kafka (kafka-sender)

Cette application Spring Boot agit comme un **producteur** de messages pour Apache Kafka.

## Description

Elle expose une API REST simple avec un endpoint qui accepte des messages. Lorsqu'un message est reçu via une requête HTTP, il est publié dans un topic Kafka en utilisant `KafkaTemplate` de Spring Kafka.

C'est le point d'entrée de notre flux de données.

## Concepts Clés

- **Spring REST Controller** : Pour exposer une API web.
- **Spring Kafka `KafkaTemplate`** : L'outil principal pour envoyer des messages à Kafka de manière simple et efficace.
- **Producteur Kafka** : Le rôle de cette application dans l'écosystème Kafka.

## Fichiers Principaux

- `MessageController.java` : Définit le endpoint REST `/send` qui déclenche l'envoi de messages.
- `KafkaProducerService.java` : Contient la logique métier, utilisant `KafkaTemplate` pour publier le message dans le topic.

## Comment l'exécuter

Assurez-vous que le `kafka-receiver` est en cours d'exécution et que Kafka est démarré (via `docker-compose up -d` dans le dossier parent).

1.  Naviguez dans ce dossier.
2.  Exécutez `mvn spring-boot:run`.
