# Application Client Spring Boot Admin

Cette application est un **client** qui s'enregistre auprès d'un serveur Spring Boot Admin.

## Description

C'est une application Spring Boot standard (qui pourrait contenir votre logique métier) à laquelle on a ajouté la dépendance `spring-boot-admin-starter-client`. Grâce à la configuration, elle s'enregistre automatiquement auprès du serveur de monitoring au démarrage.

## Configuration Clé

La connexion au serveur se configure entièrement dans le fichier `application.properties`.

```properties
# Port de l'application cliente
server.port=8098

# Nom qui apparaîtra dans l'interface du serveur
spring.application.name=admin-client

# URL du serveur Spring Boot Admin
spring.boot.admin.client.url=http://localhost:8097

# Exposer tous les endpoints de l'actuator pour le monitoring
management.endpoints.web.exposure.include=*
management.endpoint.env.show-values=ALWAYS
```

- `spring.boot.admin.client.url` : Indique au client où se trouve le serveur pour s'enregistrer.
- `management.endpoints.web.exposure.include=*` : Rend les informations de l'actuator (métriques, santé, etc.) accessibles au serveur.

## Comment l'exécuter

Assurez-vous que le `springboot-admin-server` est déjà en cours d'exécution.

1.  Naviguez dans ce dossier.
2.  Exécutez `mvn spring-boot:run`.
3.  L'application démarrera et s'enregistrera auprès du serveur. Vous pourrez alors la voir et la gérer depuis l'interface de Spring Boot Admin.
