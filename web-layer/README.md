# Web Layer

Ce module regroupe les tutoriels sur la couche web des applications Spring Boot.

### [API Versioning](api-versioning-tutorial)
Découvrez différentes stratégies pour versionner vos API REST afin de gérer les évolutions sans casser les clients existants.

### [Limitation de Débit (Rate Limiting) avec Bucket4j](bucket4j-tutorial)
Tutoriel sur la mise en place de la **limitation de débit (rate limiting)** pour vos API en utilisant la bibliothèque Bucket4j, afin de protéger vos services contre les surcharges.
#### [Avec un filtre (Filter)](bucket4j-tutorial/filter-tutorial)
Approche robuste utilisant un **Filtre de Servlet** pour appliquer des règles de limitation globales (par exemple, par adresse IP) avant que la requête n'atteigne le contrôleur.
#### [Avec l'AOP](bucket4j-tutorial/aop-tutorial)
Méthode déclarative et propre utilisant la **Programmation Orientée Aspect** pour protéger des méthodes spécifiques avec une simple annotation, sans modifier le code métier.
#### [Dans le contrôleur (Controller)](bucket4j-tutorial/controller-tutorial)
Approche directe où la logique de limitation est appelée explicitement depuis le **contrôleur**, idéale pour des règles simples et spécifiques à un endpoint.

### [GraphQL](graphql-tutorial)
Tutoriel complet sur l'intégration de **GraphQL** pour créer des API flexibles et performantes.
**Partie 1 : Mise en place** : Initialisez GraphQL dans votre projet Spring Boot.
**Partie 2 : Le schéma** : Définissez la structure de votre API avec le schéma GraphQL.
**Partie 3 : Controllers** : Implémentez les résolveurs pour vos requêtes et mutations.
**Partie 4 : Tests unitaires** : Apprenez à tester efficacement votre API GraphQL.
**Partie 5 : Documentation** : Découvrez des outils pour documenter et tester votre API.
**Partie 6 : Gestion des erreurs** : Mettez en place une gestion d'erreurs centralisée et robuste.

### [HATEOAS](hateoas-tutorial)
Tutoriel sur comment implémenter **HATEOAS** (Hypermedia as the Engine of Application State) pour enrichir vos API REST avec des liens de navigation, les rendant plus découvrables.

### [Internationalization](i18n-tutorial)
Mise en place de l'i18n et du l10n dans un projet springboot.

### [Templating](templating-tutorial)
#### [Jasper](templating-tutorial/jasper-tutorial)
Tutoriel sur la génération de documents, comme des rapports PDF, en utilisant la bibliothèque **JasperReports**.
#### [Thymeleaf](templating-tutorial/thymeleaf-tutorial)
Utilisez **Thymeleaf** comme moteur de templates pour construire des applications web côté serveur.

### [Vaadin](vaadin-tutorial)
Tutoriel sur la création d'applications web riches et interactives entièrement en Java, sans écrire de HTML ou de JavaScript, en utilisant le framework **Vaadin** avec Spring Boot.
#### [Application CRUD](vaadin-tutorial/crud-tutorial)
Un exemple d'application **CRUD (Create, Read, Update, Delete)** complète pour gérer des produits.
#### [Pokédex](vaadin-tutorial/pokedex)
Une application **Pokédex** plus riche pour démontrer la navigation, les vues dynamiques et le data binding.

### [File Upload/Download](fileUploadDownload-tutorial)
Implémentez un service pour téléverser et télécharger des fichiers avec Spring Boot.