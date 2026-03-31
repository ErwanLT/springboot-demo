# Greeting Starter

Starter Spring Boot personnalisé qui expose un service de salutation et une auto-configuration.

## Description

- `GreetingAutoConfiguration` enregistre un bean `GreetingService`.
- `GreetingProperties` lit la propriété `greeting.message`.
- `GreetingService#greet()` renvoie le message configuré.

## Utilisation

1. Construire le starter :

```bash
mvn clean install
```

2. Ajouter la dépendance du starter dans une application Spring Boot.
3. Configurer `greeting.message` dans les propriétés de l'application consommatrice.
