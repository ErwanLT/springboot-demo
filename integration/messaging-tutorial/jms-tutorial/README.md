# Tutoriel JMS

Tutoriel JMS avec ActiveMQ pour envoyer des messages via une API REST.

## Prérequis

Démarrer ActiveMQ via Docker :

```bash
docker compose up
```

- Broker : `tcp://localhost:61616`
- Console : `http://localhost:8161`

## Endpoint

- `POST /api/messages` avec un JSON du type :

```json
{ "sender": "...", "content": "..." }
```

## Comment l'exécuter

```bash
mvn spring-boot:run
```
