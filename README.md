# Springboot Démo

Projet de démonstration des fonctionnalités et des intégrations possibles avec le framework Spring Boot, organisé par catégories.

## Core Concepts

Modules explorant les concepts fondamentaux de Spring et du développement d'applications.

- **[Programmation Orientée Aspect (AOP)](core-concepts/aspect-tutorial)**: Séparez les préoccupations transversales de votre logique métier.
- **[Audit](core-concepts/audit-tutorial)**: Tracez les modifications de vos données.
- **[Programmation Concurrente](core-concepts/concurrency-tutorial)**: Gérez la concurrence avec `@Async` et WebFlux.
- **[Sécurité](core-concepts/security-tutorial)**: Sécurisez vos applications avec Basic Auth, JWT, RBAC, et LDAP.
- **[Customisation Bannière](core-concepts/banner-tutorial)**: Personnalisez la bannière de démarrage de Spring Boot.

## Data Access

Modules dédiés à la persistance, à la recherche et à la mise en cache des données.

- **[Cache](data-access/cache-tutorial)**: Améliorez les performances avec un système de cache.
- **[Database Versioning](data-access/database-versioning-tutorial)**: Gérez les migrations de votre base de données avec Flyway et Liquibase.
- **[Elasticsearch](data-access/elasticsearch-tutorial)**: Intégrez Elasticsearch pour la recherche plein texte.

## Developer Tools

Outils et techniques pour améliorer le processus de développement.

- **[Génération de code](developer-tools/code-generation-tutorial)**: Automatisez la création de clients d'API et de mappers.

## Integration

Modules montrant l'intégration avec des systèmes et services externes.

- **[BRMS (Moteurs de règles)](integration/brms-tutorial)**: Externalisez votre logique métier avec Drools, Easy Rules, et RuleBook.
- **[Messaging](integration/messaging-tutorial)**: Construisez des systèmes de messagerie asynchrones avec JMS et Kafka.
- **[Notification](integration/notification-tutorial)**: Envoyez des notifications (email, SMS).

## Operations

Modules liés au déploiement, à la supervision et à la maintenance des applications.

- **[Monitoring](operations/monitoring-tutorial)**: Supervisez vos applications avec Prometheus, Grafana, et Spring Boot Admin.
- **[Tâches Planifiées](operations/scheduled-tutorial)**: Exécutez des tâches en arrière-plan avec Quartz et Spring Batch.
- **[Testing](operations/testing-tutorial)**: Testez votre architecture, votre résilience, vos performances et utilisez des tests paramétrés.

## Web Layer

Modules centrés sur la couche de présentation et les API web.

- **[Limitation de Débit (Rate Limiting)](web-layer/bucket4j-tutorial)**: Protégez vos API contre les surcharges avec Bucket4j.
- **[GraphQL](web-layer/graphql-tutorial)**: Créez des API flexibles et performantes avec GraphQL.
- **[HATEOAS](web-layer/hateoas-tutorial)**: Enrichissez vos API REST avec des liens de navigation.
- **[Internationalization (i18n)](web-layer/i18n-tutorial)**: Supportez plusieurs langues dans votre application.
- **[Templating](web-layer/templating-tutorial)**: Générez du contenu dynamique avec Jasper et Thymeleaf.
- **[Vaadin](web-layer/vaadin-tutorial)**: Créez des applications web riches entièrement en Java.