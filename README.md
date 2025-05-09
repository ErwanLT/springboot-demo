[![Java CI with Maven](https://github.com/ErwanLT/springboot-demo/actions/workflows/maven.yml/badge.svg)](https://github.com/ErwanLT/springboot-demo/actions/workflows/maven.yml)

# Springboot Démo
Projet démo pour Spring Boot et ses possibilités.
## [Customisation bannière](banner-tutorial)
Comment customiser la bannière de votre application Spring Boot.

[article](https://www.sfeir.dev/back/comment-avoir-une-banniere-spring-personnalisee/)

## [Système de gestion de règles métier](brms-tutorial)
### [Drools](brms-tutorial/drools-tutorial)
Tutoriel sur comment intégrer Drools à votre application Spring Boot

### [Easy Rules](brms-tutorial/easyrules-tutorial)
Tutoriel sur la bibliothèque Easy Rules, qui est un moteur de règles Java simple qui vous permet de définir et d'exécuter des règles métier de manière déclarative.

### [RuleBook](brms-tutorial/rulebook-tutorial)
Tutoriel sur comment intégrer RuleBook à votre application Spring Boot

## [Génération de code](code-generation-tutorial)
### [Génération de client à partir de la spécification OpenAPI](code-generation-tutorial/client-generation-tutorial)
Tutoriel sur comment générer un client à partir de la spécification OpenAPI

[article](https://www.sfeir.dev/back/generer-vos-client-dapi-a-partir-de-leur-specification-openapi/)

### [Génération de mappers](code-generation-tutorial/mapper-tutorial)
Tutoriel sur la génération de mappers avec MapStruct

[article](https://www.sfeir.dev/back/mapstruct-dites-adieu-au-code-repetitif/)

## [Database versioning](database-versioning-tutorial)
Tutoriel sur comment versionner votre base de données
### [Flyway](database-versioning-tutorial/flyway-tutorial)
Tutoriel sur comment utiliser Flyway pour versionner votre base de données
### [Liquibase](database-versioning-tutorial/liquibase-tutorial)
Tutoriel sur comment utiliser Liquibase pour versionner votre base de données

[article](https://www.sfeir.dev/back/migration-versioning-de-base-de-donnees-dans-une-application-spring-boot/)

## [GraphQL](graphql-tutorial)
Tutoriel sur comment intégrer GraphQL à votre application Spring Boot
### [partie 1 : Mise en place](https://www.sfeir.dev/partie-1-mise-en-place/)
Apprenez à initialiser GraphQL dans une application Spring Boot.<br>
En quelques étapes, configurez votre projet avec les dépendances nécessaires et préparez-vous à explorer les avantages de GraphQL pour une gestion de données efficace et ciblée.
### [partie 2 : Le schéma](https://www.sfeir.dev/partie-2-le-schema/)
Plongez au cœur du schéma GraphQL et découvrez comment il définit :
- la structure
- les règles
- la sécurité

De vos données dans une API. <br>
Apprenez pourquoi ce contrat entre client et serveur est essentiel, et préparez-vous à configurer votre propre schéma dans Spring Boot.
### [partie 3 : Controllers](https://www.sfeir.dev/partie-3-controllers/)
Dans cette section, nous allons donner vie à notre schéma GraphQL en créant les entités Java correspondantes. <br>
Nous mettrons en place des contrôleurs adaptés pour gérer les requêtes et mutations via les annotations de Spring for GraphQL.
### [partie 4 : Tests unitaires](https://www.sfeir.dev/partie-4-tests-unitaires/)
Il est maintenant l'heure de tester notre API via des tests unitaires, mais comment les mettre en place dans Spring Boot ?
### [partie 5 : Documentation](https://www.sfeir.dev/partie-5-documentation/)
Mais comment documenter mon API GraphQL ? <br>
Et surtout comment puis-je la tester via l'équivalent d'un swagger ?<br>
<br>C'est ce que nous allons voir dans cet article.
### [partie 6 : Gestion des erreurs](https://www.sfeir.dev/partie-6-gestion-des-erreurs/)
Comment gérer efficacement les erreurs dans une API GraphQL avec Spring Boot ?<br>
En utilisant `DataFetcherExceptionResolver`, qui centralise la gestion des erreurs, et c'est ce que nous allons mettre en place dans cet article.


## [Monitoring](monitoring-tutorial)
### [Prometheus et Grafana](monitoring-tutorial/prometheus-tutorial)
Utiliser Prometheus et Grafana afin d'exposer des metric et monitorer votre application.

[article](https://www.sfeir.dev/back/superviser-votre-application-spring-boot/)

### [Spring Boot Admin](monitoring-tutorial/admin-tutorial)
Utiliser Spring Boot Admin pour monitorer vos applications Spring Boot

## [Security](security-tutorial)
Tutoriels sur comment sécuriser vos API avec Spring Security
### [Basic Auth](security-tutorial/basic-auth-tutorial)
Implémentation de Basic Auth dans une application Spring Boot

[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-basic-auth/)
### [JWT](security-tutorial/jwt-tutorial)
Implémentation de JWT dans une application Spring Boot

[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-jwt/)
### [RBAC](security-tutorial/jwt-rbac-tutorial)
Implementation d'une sécurité basée sur le contrôle de role avec JWT

[article](https://www.sfeir.dev/back/securisez-vos-api-avec-spring-security-acces-par-role/)

## [Spring batch](spring-batch-tutorial)
Implementer Spring Batch et apprenez à mettre en place des traitement périodic dans votre application Spring Boot

[article](https://www.sfeir.dev/back/planifier-des-taches-avec-spring-batch/)

## [Templating](templating-tutorial)
### [Jasper](templating-tutorial/jasper-tutorial)

## [Testing tutorial](testing-tutorial)
### [ArchUnit](testing-tutorial/archunit-tutorial)
Tutoriel sur comment utiliser ArchUnit pour tester l'architecture de votre application Spring Boot

### [Chaos Monkey](testing-tutorial/chaos-monkey-tutorial)
Tutoriel sur comment utiliser Chaos Monkey pour tester la résilience de votre application

[article](https://www.sfeir.dev/back/introduisez-du-chaos-dans-votre-application-spring-boot/)

### [Gatling](testing-tutorial/gatling-tutorial)
Tutoriel sur comment utiliser Gatling pour tester la performance de votre application

### [Parameterized tests](testing-tutorial/parametrize-test-tutorial)
Tutoriel sur comment utiliser les tests paramétrés avec pytest pour tester votre application Java