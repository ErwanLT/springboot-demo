[![Java CI with Maven](https://github.com/ErwanLT/springboot-demo/actions/workflows/maven.yml/badge.svg)](https://github.com/ErwanLT/springboot-demo/actions/workflows/maven.yml)

# Springboot Démo
Projet de démonstration des fonctionnalités et des intégrations possibles avec le framework Spring Boot.

## [Programmation Orientée Aspect (AOP)](aspect-tutorial)
Tutoriel sur la **Programmation Orientée Aspect** avec Spring Boot. Apprenez à séparer les préoccupations transversales (comme la journalisation ou la sécurité) de votre logique métier pour un code plus propre et modulaire.

## [Audit](audit-tutorial)
Tutoriel sur la mise en place de l'audit pour tracer les modifications de vos données.

## [Customisation bannière](banner-tutorial)
Découvrez comment customiser la bannière affichée au démarrage de votre application Spring Boot pour y ajouter votre touche personnelle ou des informations utiles.
[article](https://www.sfeir.dev/back/comment-avoir-une-banniere-spring-personnalisee/)

## [Système de gestion de règles métier (BRMS)](brms-tutorial)
Explorez différents moteurs de règles pour externaliser et gérer dynamiquement votre logique métier.
### [Drools](brms-tutorial/drools-tutorial)
Intégrez **Drools**, un moteur de règles robuste, pour gérer des règles métier complexes de manière déclarative.
### [Easy Rules](brms-tutorial/easyrules-tutorial)
Utilisez **Easy Rules**, une bibliothèque légère, pour créer des règles métier simples et faciles à maintenir.
### [RuleBook](brms-tutorial/rulebook-tutorial)
Découvrez **RuleBook** et son DSL intuitif pour construire des règles métier lisibles et chaînées en Java.

## [Limitation de Débit (Rate Limiting) avec Bucket4j](bucket4j-tutorial)
Tutoriel sur la mise en place de la **limitation de débit (rate limiting)** pour vos API en utilisant la bibliothèque Bucket4j, afin de protéger vos services contre les surcharges.

## [Cache](cache-tutorial)
Tutoriel sur la mise en place d'un système de cache pour améliorer les performances de vos applications.

## [Génération de code](code-generation-tutorial)
Automatisez la création de code répétitif pour accélérer votre développement.
### [Génération de client à partir de la spécification OpenAPI](code-generation-tutorial/client-generation-tutorial)
Apprenez à générer automatiquement un client d'API REST à partir d'une spécification **OpenAPI (Swagger)**.
[article](https://www.sfeir.dev/back/generer-vos-client-dapi-a-partir-de-leur-specification-openapi/)
### [Génération de mappers](code-generation-tutorial/mapper-tutorial)
Utilisez **MapStruct** pour générer automatiquement les mappers entre vos objets (par exemple, DTO et entités) et dire adieu au code de mapping manuel.
[article](https://www.sfeir.dev/back/mapstruct-dites-adieu-au-code-repetitif/)

## [Database versioning](database-versioning-tutorial)
Gérez les évolutions de votre schéma de base de données de manière rigoureuse et automatisée.
[article](https://www.sfeir.dev/back/migration-versioning-de-base-de-donnees-dans-une-application-spring-boot/)
### [Flyway](database-versioning-tutorial/flyway-tutorial)
Utilisez **Flyway** pour versionner votre base de données avec des migrations basées sur des scripts SQL.
### [Liquibase](database-versioning-tutorial/liquibase-tutorial)
Découvrez **Liquibase**, une alternative puissante pour le versioning de base de données avec des changements suivis via des fichiers XML, YAML ou SQL.

## [Elasticsearch](elasticsearch-tutorial)
Tutoriel sur l'intégration d'**Elasticsearch** dans une application Spring Boot en utilisant Spring Data Elasticsearch pour l'indexation, la recherche plein texte et l'analyse de données.

## [GraphQL](graphql-tutorial)
Tutoriel complet sur l'intégration de **GraphQL** pour créer des API flexibles et performantes.
- **Partie 1 : Mise en place** : Initialisez GraphQL dans votre projet Spring Boot.
- **Partie 2 : Le schéma** : Définissez la structure de votre API avec le schéma GraphQL.
- **Partie 3 : Controllers** : Implémentez les résolveurs pour vos requêtes et mutations.
- **Partie 4 : Tests unitaires** : Apprenez à tester efficacement votre API GraphQL.
- **Partie 5 : Documentation** : Découvrez des outils pour documenter et tester votre API.
- **Partie 6 : Gestion des erreurs** : Mettez en place une gestion d'erreurs centralisée et robuste.

## [HATEOAS](hateoas-tutorial)
Tutoriel sur comment implémenter **HATEOAS** (Hypermedia as the Engine of Application State) pour enrichir vos API REST avec des liens de navigation, les rendant plus découvrables.

## [Internationalization](i18n-tutorial)
Mise en place de l'i18n et du l10n dans un projet springboot.

## [Messaging](messaging-tutorial)
### [Kafka](messaging-tutorial/kafka-tutorial)
Tutoriel sur l'intégration d'**Apache Kafka** pour construire des systèmes de messagerie asynchrones, distribués et réactifs.

## [Monitoring](monitoring-tutorial)
Supervisez et monitorez l'état de santé de vos applications.
### [Prometheus et Grafana](monitoring-tutorial/prometheus-tutorial)
Utilisez **Prometheus** pour collecter des métriques et **Grafana** pour créer des tableaux de bord de visualisation.
[article](https://www.sfeir.dev/back/superviser-votre-application-spring-boot/)
### [Spring Boot Admin](monitoring-tutorial/admin-tutorial)
Déployez **Spring Boot Admin** pour une interface de monitoring et de gestion dédiée à vos applications Spring.

## [Notification](notification-tutorial)
Tutoriel sur l'envoi de notifications (par exemple, email, SMS) depuis une application Spring Boot.

## [Programmation Concurrente](concurrency-tutorial)
Découvrez différentes approches pour gérer la concurrence et le parallélisme dans vos applications Spring Boot.
### [Programmation Asynchrone](concurrency-tutorial/async-tutorial)
Tutoriel sur la programmation asynchrone avec Spring Boot pour améliorer la performance et la scalabilité de vos applications.
### [Programmation Réactive (WebFlux)](concurrency-tutorial/reactive-tutorial)
Tutoriel sur la création d'applications web réactives et non-bloquantes avec **Spring WebFlux**.

## [Security](security-tutorial)
Apprenez à sécuriser vos applications avec Spring Security.
### [Basic Auth](security-tutorial/basic-auth-tutorial)
Implémentez l'authentification **Basic Auth**, une méthode simple et standard pour protéger vos endpoints.
[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-basic-auth/)
### [JWT](security-tutorial/jwt-tutorial)
Sécurisez vos API de manière stateless avec les **JSON Web Tokens (JWT)**.
[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-jwt/)
### [RBAC](security-tutorial/jwt-rbac-tutorial)
Mettez en place un **contrôle d'accès basé sur les rôles (RBAC)** pour gérer finement les permissions de vos utilisateurs.
[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-acces-par-role/)

## [Scheduled Tutorial](scheduled-tutorial)
### [Quartz tutorial](scheduled-tutorial/quartz-tutorial)
Démontre comment utiliser le planificateur Quartz pour une planification de jobs robuste et persistante.

### [Spring batch](spring-batch-tutorial)
Implémentez **Spring Batch** pour créer des traitements par lots (batch) robustes et performants pour des tâches comme l'import/export de données ou des traitements périodiques.
[article](https://www.sfeir.dev/back/planifier-des-taches-avec-spring-batch/)

## [Templating](templating-tutorial)
### [Jasper](templating-tutorial/jasper-tutorial)
Tutoriel sur la génération de documents, comme des rapports PDF, en utilisant la bibliothèque **JasperReports**.

## [Testing tutorial](testing-tutorial)
Découvrez un éventail d'outils et de techniques pour tester vos applications.
### [ArchUnit](testing-tutorial/archunit-tutorial)
Utilisez **ArchUnit** pour écrire des tests qui valident et maintiennent les contraintes architecturales de votre code (ex: "les services ne doivent pas accéder directement aux contrôleurs").
### [Chaos Monkey](testing-tutorial/chaos-monkey-tutorial)
Introduisez du chaos dans votre application avec **Chaos Monkey** pour tester sa résilience en simulant des pannes et des latences de manière contrôlée.
[article](https://www.sfeir.dev/back/introduisez-du-chaos-dans-votre-application-spring-boot/)
### [Gatling](testing-tutorial/gatling-tutorial)
Réalisez des **tests de performance** et de charge sur votre application avec Gatling pour identifier les goulots d'étranglement.
### [Parameterized tests](testing-tutorial/parametrize-test-tutorial)
Écrivez des **tests paramétrés** avec JUnit 5 pour exécuter le même test avec différents jeux de données, rendant vos tests plus concis et efficaces.

## [Vaadin](vaadin-tutorial)
Tutoriel sur la création d'applications web riches et interactives entièrement en Java, sans écrire de HTML ou de JavaScript, en utilisant le framework **Vaadin** avec Spring Boot.