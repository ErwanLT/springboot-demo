# Tutoriel Resilience4j

Ce module de démonstration présente l'intégration de plusieurs patrons de conception de résilience avec Resilience4j dans une application Spring Boot.

## Prérequis

- Java 21
- Maven

## Lancement de l'application

Pour lancer l'application, exécutez la commande suivante à la racine du projet :

```bash
./mvnw spring-boot:run -pl operations/resilience4j-tutorial
```

L'application démarrera sur le port `8081`.

## Endpoints de Démonstration

Chaque endpoint ci-dessous est conçu pour illustrer un patron de résilience spécifique.

### 1. Circuit Breaker (Disjoncteur)

Cet endpoint simule un service qui échoue périodiquement. Le disjoncteur s'ouvrira après un certain nombre d'échecs pour protéger le système.

- **URL :** `GET /api/data`
- **Commande de test :**
  ```bash
  curl http://localhost:8081/api/data
  ```
- **Comportement attendu :** Les premiers appels échouent et retournent une réponse de secours. Après suffisamment d'échecs, le disjoncteur s'ouvre et les appels suivants échouent instantanément avec la réponse de secours.

### 2. Retry (Nouvelle tentative)

Cet endpoint simule un service qui échoue de manière passagère. Resilience4j réessaie automatiquement l'appel jusqu'à ce qu'il réussisse (dans la limite configurée).

- **URL :** `GET /api/retry-data`
- **Commande de test :**
  ```bash
  curl http://localhost:8081/api/retry-data
  ```
- **Comportement attendu :** La requête réussit du premier coup pour le client. Dans les logs du serveur, on peut voir que l'opération a été tentée plusieurs fois avant de réussir.

### 3. Rate Limiter (Limiteur de débit)

Cet endpoint limite le nombre d'appels à 2 toutes les 10 secondes.

- **URL :** `GET /api/ratelimit-data`
- **Commande de test :**
  ```bash
  # Appelez cette commande plus de 2 fois en 10 secondes
  curl http://localhost:8081/api/ratelimit-data; echo
  ```
- **Comportement attendu :** Les 2 premiers appels réussissent. Les suivants sont immédiatement rejetés avec une réponse de secours jusqu'à la fin de la période de 10 secondes.

### 4. Bulkhead (Cloisonnement)

Cet endpoint simule un service lent et limite les appels concurrents à 1 seul.

- **URL :** `GET /api/bulkhead-data`
- **Commande de test :**
  1. Ouvrez un premier terminal et lancez : `curl http://localhost:8081/api/bulkhead-data` (cet appel prendra 3 secondes).
  2. Pendant ce temps, ouvrez un second terminal et lancez la même commande.
- **Comportement attendu :** Le premier appel réussit après 3 secondes. Le second est instantanément rejeté avec une réponse de secours car le "cloisonnement" est plein.

### 5. Time Limiter (Limiteur de temps)

Cet endpoint simule un appel asynchrone très long (3 secondes) qui est interrompu par un timeout après 1 seconde.

- **URL :** `GET /api/timelimit-data`
- **Commande de test :**
  ```bash
  curl http://localhost:8081/api/timelimit-data
  ```
- **Comportement attendu :** La réponse est quasi instantanée et retourne un message de secours indiquant que le délai d'attente a été dépassé.

## Monitoring via Actuator

L'état de chaque module Resilience4j est exposé via Spring Boot Actuator :

- **Circuit Breakers :** `/actuator/circuitbreakers`
- **Retries :** `/actuator/retries`
- **Rate Limiters :** `/actuator/ratelimiters`
- **Bulkheads :** `/actuator/bulkheads`
- **Time Limiters :** `/actuator/timelimiters`

## Lancement des Tests

Une suite de tests d'intégration a été créée pour valider le comportement de chaque endpoint. Pour l'exécuter :

```bash
./mvnw test -pl operations/resilience4j-tutorial
```
