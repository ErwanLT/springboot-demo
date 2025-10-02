# Operations

Ce module regroupe les tutoriels sur les aspects opérationnels des applications Spring Boot.

### [Monitoring](monitoring-tutorial)
Supervisez et monitorez l'état de santé de vos applications.
#### [Prometheus et Grafana](monitoring-tutorial/prometheus-tutorial)
Utilisez **Prometheus** pour collecter des métriques et **Grafana** pour créer des tableaux de bord de visualisation.
[article](https://www.sfeir.dev/back/superviser-votre-application-spring-boot/)
#### [Spring Boot Admin](monitoring-tutorial/admin-tutorial)
Déployez **Spring Boot Admin** pour une interface de monitoring et de gestion dédiée à vos applications Spring.

### [Resilience4j](resilience4j-tutorial)
Tutoriel sur l'implémentation de patterns de résilience (disjoncteur, réessai, etc.) avec **Resilience4j** pour rendre vos applications plus robustes face aux pannes.

### [Scheduled Tutorial](scheduled-tutorial)
#### [Quartz tutorial](scheduled-tutorial/quartz-tutorial)
Démontre comment utiliser le planificateur Quartz pour une planification de jobs robuste et persistante.
#### [Spring Batch](scheduled-tutorial/spring-batch-tutorial)
Implémentez **Spring Batch** pour créer des traitements par lots (batch) robustes et performants pour des tâches comme l'import/export de données ou des traitements périodiques.
[article](https://www.sfeir.dev/back/planifier-des-taches-avec-spring-batch/)

### [Testing tutorial](testing-tutorial)
Découvrez un éventail d'outils et de techniques pour tester vos applications.
#### [ArchUnit](testing-tutorial/archunit-tutorial)
Utilisez **ArchUnit** pour écrire des tests qui valident et maintiennent les contraintes architecturales de votre code (ex: "les services ne doivent pas accéder directement aux contrôleurs").
#### [Chaos Monkey](testing-tutorial/chaos-monkey-tutorial)
Introduisez du chaos dans votre application avec **Chaos Monkey** pour tester sa résilience en simulant des pannes et des latences de manière contrôlée.
[article](https://www.sfeir.dev/back/introduisez-du-chaos-dans-votre-application-spring-boot/)
#### [Gatling](testing-tutorial/gatling-tutorial)
Réalisez des **tests de performance** et de charge sur votre application avec Gatling pour identifier les goulots d'étranglement.
#### [Parameterized tests](testing-tutorial/parametrize-test-tutorial)
Écrivez des **tests paramétrés** avec JUnit 5 pour exécuter le même test avec différents jeux de données, rendant vos tests plus concis et efficaces.