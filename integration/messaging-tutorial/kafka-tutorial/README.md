# Tutoriel Apache Kafka

Ce tutoriel montre comment construire un système de messagerie simple avec **Apache Kafka** et Spring Boot, composé de deux applications distinctes : un producteur et un consommateur.

## Description

Ce projet illustre un cas d'usage classique de Kafka :

1.  Une application **`kafka-sender`** expose une API REST pour envoyer des messages.
2.  Ces messages sont publiés dans un topic Kafka.
3.  Une seconde application, **`kafka-receiver`**, écoute ce topic, consomme les messages et les affiche dans les logs.

Un fichier `docker-compose.yml` est inclus pour démarrer facilement une instance de Kafka et de Zookeeper.

## Sous-modules

- **[kafka-sender](kafka-sender)** : L'application Spring Boot qui agit comme producteur de messages Kafka.
- **[kafka-receiver](kafka-receiver)** : L'application Spring Boot qui agit comme consommateur de messages Kafka.

## Comment l'exécuter

1.  **Démarrez Kafka** : À la racine de ce dossier, exécutez `docker-compose up -d`.
2.  **Lancez le consommateur** : Naviguez dans le dossier `kafka-receiver` et lancez l'application (`mvn spring-boot:run`).
3.  **Lancez le producteur** : Naviguez dans le dossier `kafka-sender` et lancez l'application (`mvn spring-boot:run`).
4.  **Envoyez un message** : Utilisez un client HTTP (comme `curl`) pour envoyer une requête POST à l'application `sender` :
    ```bash
    curl -X POST http://localhost:8081/send -H "Content-Type: application/json" -d '{"message": "Hello Kafka!"}'
    ```
5.  **Vérifiez les logs** : Observez les logs de l'application `receiver` pour voir le message consommé.
