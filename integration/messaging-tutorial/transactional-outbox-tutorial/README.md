# Transactional Outbox Tutorial

Exemple Spring Boot + Kafka pour implémenter le pattern **Transactional Outbox** et garantir la cohérence entre l'écriture en base et la publication d'événements.

## Objectif

Quand une commande est créée:
1. on persiste la commande en base,
2. on persiste un événement dans une table `outbox_events`,
3. un publisher planifié lit les événements `PENDING`,
4. publie vers Kafka,
5. puis marque l'événement en `SENT`.

## Endpoints

- `POST /api/orders`
- `GET /api/outbox?status=PENDING|SENT`

Exemple de payload:

```json
{
  "customerName": "Erwan",
  "amount": 42.50
}
```

## Prérequis

- Kafka accessible sur `localhost:9092`

Tu peux réutiliser le `docker-compose.yml` du module `kafka-tutorial`.
