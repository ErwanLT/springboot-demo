# Résumé des Tutoriels par Catégorie

## Core Concept (`core-concept`)
- Actuator
- Programmation Orientée Aspect (AOP)
- Audit (JPA)
- Customisation bannière
- Custom starter
- Documentation (Spring REST Docs)
- Programmation Concurrente
  - Asynchrone
  - Réactive (WebFlux)
  - Virtual Threads
- Sécurité
  - Basic Auth
  - JWT
  - JWT avec RBAC
  - LDAP
  - Serveur d'autorisation OAuth2
- SpEL
- Validation
- Gestion centralisée des exceptions
  - Exception Handler
  - ProblemDetail (RFC 9457)

## Database Access (`database-access`)
- Mise en cache (Caching)
  - Cache in-memory
  - Cache avec Caffeine
  - Cache avec Redis
- Versioning de base de données
  - Flyway
  - Liquibase
- Elasticsearch
  - Indexation et recherche de documents (Spring Data Elasticsearch)
  - Logging structuré (ECS) avec Logback
  - Collecte et expédition de logs (Logstash)
- R2DBC
- Query DSL
- jOOQ
- Configuration multi-bases de données

## Developer Tools (`developer-tools`)
- Génération de code
  - Génération de client (OpenAPI Codegen)
  - MapStruct (Mapper Tutorial)
- Hotswap
- Spring Modulith
- Spring Shell
  - Création d'applications CLI interactives
  - Définition de commandes (`@ShellMethod`, `@ShellOption`)
  - Composants d'interface textuelle (TUI) : Tableaux, barres de progression (`ProgressView`)
  - Gestion de tâches asynchrones (`@Async`)
  - Contrôle avancé du terminal (ex: mini-jeu Fallout-style)

## Integration (`integration`)
- Intelligence Artificielle (IA)
  - Langchain4j
    - Chat et génération de texte
    - Streaming des réponses
    - RAG (Retrieval-Augmented Generation) avec des documents locaux
    - Extraction de données structurées (JSON)
    - Personnalisation de l'IA (Assistants)
    - Analyse de sentiment
    - Intégration d'outils (Tools)
- Blockchain
- Design Patterns
  - Strategy
  - Factory
  - AOP (Aspect) pour le logging
- Gestion de corps de requête dynamiques
- Système de gestion de règles métier (BRMS)
  - Drools
  - Easy Rules
  - RuleBook
- Messaging
  - JMS
  - Kafka
- Notification
  - Envoi d'email
- Génération de QR Code
- WebSockets
- gRPC

## Operations (`operations`)
- Supervision (Monitoring)
  - Prometheus et Grafana
  - Spring Boot Admin
- Resilience4j
  - Circuit Breaker
  - Retry
  - Time Limiter
  - Rate Limiter
  - Bulkhead
- Tâches planifiées (Scheduling)
    - Quartz
    - Spring Batch
- Stratégies de test
  - ArchUnit
  - Chaos Monkey
  - Gatling
  - Tests paramétrés (JUnit 5)

## Web Layer (`web-layer`)
- Versioning d'API
  - url
  - param
  - header
- Limitation de débit (Rate Limiting)
    - Avec un filtre (Filter)
    - Avec l'AOP
    - Dans le contrôleur (Controller)
- GraphQL
- HATEOAS
- Internationalisation (i18n)
- Localisation (l10n)
- Moteurs de templates
  - Jasper
  - Thymeleaf
- Vaadin
    - Application CRUD
    - Pokédex
- Gestion d'upload/download de fichiers

# Sujets non encore traités (Roadmap)

## Core Concept
- Spring Native (GraalVM)
- Gestion de la configuration (Spring Cloud Config)

## Database Access
- JPA / Hibernate Avancé
- MongoDB
- Multi-tenancy

## Integration
- Clients REST Déclaratifs (OpenFeign)
- WebClient
- Apache Camel

## Operations
- Distributed Tracing
- Dockerisation
- Configuration de CI/CD
- Déploiement sur Kubernetes

## Web Layer
- CORS