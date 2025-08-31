# Tutoriel Spring Boot Quartz

Ce module montre comment intégrer le planificateur Quartz avec une application Spring Boot pour exécuter des tâches de fond planifiées.

## Fonctionnalités

- **Intégration de Quartz**: Montre la configuration de Quartz avec Spring Boot.
- **JDBC JobStore**: Configuré pour utiliser un JobStore JDBC, qui persiste les jobs et les déclencheurs dans une base de données (une base de données en mémoire H2 est utilisée pour ce tutoriel). Cela garantit que les planifications ne sont pas perdues si l'application redémarre.
- **Tâche Planifiée**: Inclut un exemple de job (`InactiveUserCleanupJob`) qui s'exécute toutes les minutes.
- **Injection de Dépendances**: Démontre la manière correcte d'injecter des beans Spring (comme des services et des repositories) dans les jobs Quartz en utilisant une `JobFactory` personnalisée.
- **Jobs Transactionnels**: Montre comment gérer correctement les transactions de base de données pour les opérations effectuées au sein d'un job planifié.

## Logique

Le job d'exemple est conçu pour nettoyer les utilisateurs inactifs de la base de données. Pour ce faire, il supprime tout utilisateur dont le `status` est marqué comme 'inactive'.

## Comment l'exécuter

Ceci est une application Spring Boot standard. Vous pouvez l'exécuter depuis votre IDE en lançant la méthode `main` dans `QuartzApplicationTutorial.java` ou en utilisant le wrapper Maven :

```bash
./mvnw spring-boot:run
```