# Greeting App

Application Spring Boot de démonstration qui consomme le **custom starter** `greeting-starter`.

## Description

- Démarre une application Spring Boot et exécute un `CommandLineRunner`.
- Utilise `GreetingService` fourni par le starter pour afficher un message au démarrage.
- Le message est configuré via `greeting.message`.

## Configuration

- `greeting.message` dans `src/main/resources/application.properties`.

## Comment l'exécuter

```bash
mvn spring-boot:run
```

Au démarrage, le message configuré est loggué par l'application.
