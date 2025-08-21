# Application Serveur Spring Boot Admin

Cette application est le **serveur de monitoring** central de Spring Boot Admin.

## Description

C'est une application Spring Boot qui agit comme un agrégateur. Les applications clientes s'enregistrent auprès de lui, et il fournit une interface utilisateur pour visualiser et interagir avec les informations de ces clients.

## Configuration Clé

### 1. Annotation

L'activation du serveur se fait via l'annotation `@EnableAdminServer` dans la classe d'application principale.

```java
@EnableAdminServer
@SpringBootApplication
public class SpringBootAdminServerApplication {
    // ...
}
```

### 2. Fichier `application.properties`

La configuration définit le port du serveur et peut optionnellement configurer des notifications (par exemple, vers Slack).

```properties
# Port sur lequel le serveur sera accessible
server.port=8097

# Exemple de configuration pour les notifications Slack (optionnel)
spring.boot.admin.notify.slack.enabled=true
spring.boot.admin.notify.slack.webhook-url=https://hooks.slack.com/services/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
spring.boot.admin.notify.slack.channel=notifications
```

## Comment l'exécuter

1.  Naviguez dans ce dossier.
2.  Exécutez `mvn spring-boot:run`.
3.  Accédez à `http://localhost:8097` dans votre navigateur.
