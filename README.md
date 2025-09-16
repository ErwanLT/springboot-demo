[![Java CI with Maven](https://github.com/ErwanLT/springboot-demo/actions/workflows/maven.yml/badge.svg)](https://github.com/ErwanLT/springboot-demo/actions/workflows/maven.yml) 
[![Java](https://img.shields.io/badge/Java-21-blue.svg?logo=java)](https://www.java.com) 
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-green.svg?logo=springboot)](https://spring.io/projects/spring-boot) 
[![OpenAPI](https://img.shields.io/badge/OpenAPI-2.8.8-blue.svg?logo=openapiinitiative)](https://springdoc.org/) 
[![Vaadin](https://img.shields.io/badge/Vaadin-24.8.0-lightblue.svg?logo=vaadin)](https://vaadin.com)

# Springboot Démo
Projet de démonstration des fonctionnalités et des intégrations possibles avec le framework Spring Boot.

## Core Concept (`core-concept`)

### [Actuator](core-concept/actuator-tutorial)
Tutoriel sur **Spring Boot Actuator**, qui fournit des fonctionnalités prêtes à l'emploi pour surveiller et gérer votre application (points de terminaison de santé, métriques, etc.).

### [Programmation Orientée Aspect (AOP)](core-concept/aspect-tutorial)
Tutoriel sur la **Programmation Orientée Aspect** avec Spring Boot. Apprenez à séparer les préoccupations transversales (comme la journalisation ou la sécurité) de votre logique métier pour un code plus propre et modulaire.

### [Audit](core-concept/audit-tutorial)
Tutoriel sur la mise en place de l'audit pour tracer les modifications de vos données.

### [Customisation bannière](core-concept/banner-tutorial)
Découvrez comment customiser la bannière affichée au démarrage de votre application Spring Boot pour y ajouter votre touche personnelle ou des informations utiles.
[article](https://www.sfeir.dev/back/comment-avoir-une-banniere-spring-personnalisee/)

### [Programmation Concurrente](core-concept/concurrency-tutorial)
Découvrez différentes approches pour gérer la concurrence et le parallélisme dans vos applications Spring Boot.
#### [Programmation Asynchrone](core-concept/concurrency-tutorial/async-tutorial)
Tutoriel sur la programmation asynchrone avec Spring Boot pour améliorer la performance et la scalabilité de vos applications.
#### [Programmation Réactive (WebFlux)](core-concept/concurrency-tutorial/reactive-tutorial)
Tutoriel sur la création d'applications web réactives et non-bloquantes avec **Spring WebFlux**.
#### [Virtual Threads (Project Loom)](core-concept/concurrency-tutorial/virtualthread-tutorial)
Explorez l'utilisation des **Virtual Threads** pour simplifier l'écriture de code concurrent hautement performant.

### [Security](core-concept/security-tutorial)
Apprenez à sécuriser vos applications avec Spring Security.
#### [Basic Auth](core-concept/security-tutorial/basic-auth-tutorial)
Implémentez l'authentification **Basic Auth**, une méthode simple et standard pour protéger vos endpoints.
[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-basic-auth/)
#### [JWT](core-concept/security-tutorial/jwt-tutorial)
Sécurisez vos API de manière stateless avec les **JSON Web Tokens (JWT)**.
[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-jwt/)
#### [RBAC](core-concept/security-tutorial/jwt-rbac-tutorial)
Mettez en place un **contrôle d'accès basé sur les rôles (RBAC)** pour gérer finement les permissions de vos utilisateurs.
[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-acces-par-role/)
#### [LDAP](core-concept/security-tutorial/ldap-tutorial)
Intégrez une authentification **LDAP** pour déléguer la gestion des utilisateurs à un annuaire d'entreprise.
#### [OAuth2 Authorization Server](core-concept/security-tutorial/oauth2-authorization-server-tutorial)
Configurez un **serveur d'autorisation OAuth2** pour déléguer l'authentification et gérer les accès de manière sécurisée et standardisée.

### [SpEL (Spring Expression Language)](core-concept/spel-tutorial)
Tutoriel sur le **Spring Expression Language (SpEL)**, un langage puissant pour interroger et manipuler un graphe d'objets à l'exécution.

### [Validation](core-concept/validation-tutorial)
Tutoriel sur la validation des données en entrée avec les annotations de Jakarta Bean Validation.

## Database Access (`database-access`)

### [Cache](database-access/cache-tutorial)
Tutoriel sur la mise en place d'un système de cache pour améliorer les performances de vos applications.

### [Database versioning](database-access/database-versioning-tutorial)
Gérez les évolutions de votre schéma de base de données de manière rigoureuse et automatisée.
[article](https://www.sfeir.dev/back/migration-versioning-de-base-de-donnees-dans-une-application-spring-boot/)
#### [Flyway](database-access/database-versioning-tutorial/flyway-tutorial)
Utilisez **Flyway** pour versionner votre base de données avec des migrations basées sur des scripts SQL.
#### [Liquibase](database-access/database-versioning-tutorial/liquibase-tutorial)
Découvrez **Liquibase**, une alternative puissante pour le versioning de base de données avec des changements suivis via des fichiers XML, YAML ou SQL.

### [Elasticsearch](database-access/elasticsearch-tutorial)
Tutoriel sur l'intégration d'**Elasticsearch** dans une application Spring Boot en utilisant Spring Data Elasticsearch pour l'indexation, la recherche plein texte et l'analyse de données.

### [R2DBC](database-access/r2dbc-tutorial)
Tutoriel sur l'utilisation de R2DBC (Reactive Relational Database Connectivity) avec Spring Data R2DBC pour interagir avec une base de données relationnelle de manière réactive.

## Developer Tools (`developer-tools`)

### [Génération de Code](developer-tools/code-generation-tutorial)
Explore différentes techniques pour automatiser les tâches répétitives, notamment la génération de clients d'API (OpenAPI) et de mappers (MapStruct).

### [Spring Modulith](developer-tools/modulith-tutorial)
Démontre comment structurer une application en "modulithe" avec des frontières de modules claires et vérifiables.

## Integration (`integration`)

### [Système de gestion de règles métier (BRMS)](integration/brms-tutorial)
Explorez différents moteurs de règles pour externaliser et gérer dynamiquement votre logique métier.
#### [Drools](integration/brms-tutorial/drools-tutorial)
Intégrez **Drools**, un moteur de règles robuste, pour gérer des règles métier complexes de manière déclarative.
#### [Easy Rules](integration/brms-tutorial/easyrules-tutorial)
Utilisez **Easy Rules**, une bibliothèque légère, pour créer des règles métier simples et faciles à maintenir.
#### [RuleBook](integration/brms-tutorial/rulebook-tutorial)
Découvrez **RuleBook** et son DSL intuitif pour construire des règles métier lisibles et chaînées en Java.

### [Messaging](integration/messaging-tutorial)
#### [JMS](integration/messaging-tutorial/jms-tutorial)
Tutoriel sur l'intégration de **JMS (Java Message Service)** avec ActiveMQ pour une messagerie asynchrone standard et découplée.
#### [Kafka](integration/messaging-tutorial/kafka-tutorial)
Tutoriel sur l'intégration d'**Apache Kafka** pour construire des systèmes de messagerie asynchrones, distribués et réactifs.

### [Notification](integration/notification-tutorial)
Tutoriel sur l'envoi de notifications (par exemple, email, SMS) depuis une application Spring Boot.

### [WebSocket](integration/websocket-tutorial)
Tutoriel sur la création d'une application de chat en temps réel avec Spring Boot et WebSockets pour une communication bidirectionnelle instantanée.

## Operations (`operations`)

### [Monitoring](operations/monitoring-tutorial)
Supervisez et monitorez l'état de santé de vos applications.
#### [Prometheus et Grafana](operations/monitoring-tutorial/prometheus-tutorial)
Utilisez **Prometheus** pour collecter des métriques et **Grafana** pour créer des tableaux de bord de visualisation.
[article](https://www.sfeir.dev/back/superviser-votre-application-spring-boot/)
#### [Spring Boot Admin](operations/monitoring-tutorial/admin-tutorial)
Déployez **Spring Boot Admin** pour une interface de monitoring et de gestion dédiée à vos applications Spring.

### [Resilience4j](operations/resilience4j-tutorial)
Tutoriel sur l'implémentation de patterns de résilience (disjoncteur, réessai, etc.) avec **Resilience4j** pour rendre vos applications plus robustes face aux pannes.

### [Scheduled Tutorial](operations/scheduled-tutorial)
#### [Quartz tutorial](operations/scheduled-tutorial/quartz-tutorial)
Démontre comment utiliser le planificateur Quartz pour une planification de jobs robuste et persistante.
#### [Spring Batch](operations/scheduled-tutorial/spring-batch-tutorial)
Implémentez **Spring Batch** pour créer des traitements par lots (batch) robustes et performants pour des tâches comme l'import/export de données ou des traitements périodiques.
[article](https://www.sfeir.dev/back/planifier-des-taches-avec-spring-batch/)

### [Testing tutorial](operations/testing-tutorial)
Découvrez un éventail d'outils et de techniques pour tester vos applications.
#### [ArchUnit](operations/testing-tutorial/archunit-tutorial)
Utilisez **ArchUnit** pour écrire des tests qui valident et maintiennent les contraintes architecturales de votre code (ex: "les services ne doivent pas accéder directement aux contrôleurs").
#### [Chaos Monkey](operations/testing-tutorial/chaos-monkey-tutorial)
Introduisez du chaos dans votre application avec **Chaos Monkey** pour tester sa résilience en simulant des pannes et des latences de manière contrôlée.
[article](https://www.sfeir.dev/back/introduisez-du-chaos-dans-votre-application-spring-boot/)
#### [Gatling](operations/testing-tutorial/gatling-tutorial)
Réalisez des **tests de performance** et de charge sur votre application avec Gatling pour identifier les goulots d'étranglement.
#### [Parameterized tests](operations/testing-tutorial/parametrize-test-tutorial)
Écrivez des **tests paramétrés** avec JUnit 5 pour exécuter le même test avec différents jeux de données, rendant vos tests plus concis et efficaces.

## Web Layer (`web-layer`)

### [API Versioning](web-layer/api-versioning-tutorial)
Découvrez différentes stratégies pour versionner vos API REST afin de gérer les évolutions sans casser les clients existants.

### [Limitation de Débit (Rate Limiting) avec Bucket4j](web-layer/bucket4j-tutorial)
Tutoriel sur la mise en place de la **limitation de débit (rate limiting)** pour vos API en utilisant la bibliothèque Bucket4j, afin de protéger vos services contre les surcharges.
#### [Avec un filtre (Filter)](web-layer/bucket4j-tutorial/filter-tutorial)
Approche robuste utilisant un **Filtre de Servlet** pour appliquer des règles de limitation globales (par exemple, par adresse IP) avant que la requête n'atteigne le contrôleur.
#### [Avec l'AOP](web-layer/bucket4j-tutorial/aop-tutorial)
Méthode déclarative et propre utilisant la **Programmation Orientée Aspect** pour protéger des méthodes spécifiques avec une simple annotation, sans modifier le code métier.
#### [Dans le contrôleur (Controller)](web-layer/bucket4j-tutorial/controller-tutorial)
Approche directe où la logique de limitation est appelée explicitement depuis le **contrôleur**, idéale pour des règles simples et spécifiques à un endpoint.

### [GraphQL](web-layer/graphql-tutorial)
Tutoriel complet sur l'intégration de **GraphQL** pour créer des API flexibles et performantes.
**Partie 1 : Mise en place** : Initialisez GraphQL dans votre projet Spring Boot.
**Partie 2 : Le schéma** : Définissez la structure de votre API avec le schéma GraphQL.
**Partie 3 : Controllers** : Implémentez les résolveurs pour vos requêtes et mutations.
**Partie 4 : Tests unitaires** : Apprenez à tester efficacement votre API GraphQL.
**Partie 5 : Documentation** : Découvrez des outils pour documenter et tester votre API.
**Partie 6 : Gestion des erreurs** : Mettez en place une gestion d'erreurs centralisée et robuste.

### [HATEOAS](web-layer/hateoas-tutorial)
Tutoriel sur comment implémenter **HATEOAS** (Hypermedia as the Engine of Application State) pour enrichir vos API REST avec des liens de navigation, les rendant plus découvrables.

### [Internationalization](web-layer/i18n-tutorial)
Mise en place de l'i18n et du l10n dans un projet springboot.

### [Templating](web-layer/templating-tutorial)
#### [Jasper](web-layer/templating-tutorial/jasper-tutorial)
Tutoriel sur la génération de documents, comme des rapports PDF, en utilisant la bibliothèque **JasperReports**.
#### [Thymeleaf](web-layer/templating-tutorial/thymeleaf-tutorial)
Utilisez **Thymeleaf** comme moteur de templates pour construire des applications web côté serveur.

### [Vaadin](web-layer/vaadin-tutorial)
Tutoriel sur la création d'applications web riches et interactives entièrement en Java, sans écrire de HTML ou de JavaScript, en utilisant le framework **Vaadin** avec Spring Boot.
#### [Application CRUD](web-layer/vaadin-tutorial/crud-tutorial)
Un exemple d'application **CRUD (Create, Read, Update, Delete)** complète pour gérer des produits.
#### [Pokédex](web-layer/vaadin-tutorial/pokedex)
Une application **Pokédex** plus riche pour démontrer la navigation, les vues dynamiques et le data binding.
