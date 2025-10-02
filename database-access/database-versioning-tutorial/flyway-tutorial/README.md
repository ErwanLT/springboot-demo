# Versioning de base de données avec Flyway

Ce tutoriel montre comment utiliser **Flyway** pour gérer les migrations de schéma de base de données dans une application Spring Boot.

## Description

Flyway est un outil de migration de base de données qui favorise la simplicité et la convention plutôt que la configuration. Il utilise des scripts SQL pour versionner l'état de votre base de données.

## Concepts Clés

- **Migrations SQL** : Les changements de schéma sont écrits dans des fichiers SQL nommés selon une convention précise (ex: `V1__Create_person_table.sql`). Flyway les exécute dans l'ordre.
- **Table de métadonnées (`flyway_schema_history`)** : Flyway crée et gère une table dans votre base de données pour suivre quelles migrations ont déjà été appliquées, quand, et si elles ont réussi.
- **Intégration Spring Boot** : L'intégration est automatique. Il suffit d'ajouter la dépendance `flyway-core` et de placer les scripts de migration dans `src/main/resources/db/migration`. Spring Boot exécute les migrations Flyway au démarrage de l'application.

## Comment l'exécuter et vérifier

1.  **Lancez l'application** en exécutant la méthode `main` de `FlywayApplication.java` ou via Maven à la racine de ce module :
    ```bash
    mvn spring-boot:run
    ```

2.  **Observez les logs de démarrage**. Vous verrez que Flyway détecte et applique les migrations SQL trouvées dans le classpath :
    ```
    ... o.f.c.internal.command.DbMigrate     : Migrating schema "PUBLIC" to version "1 - create book table"
    ... o.f.c.internal.command.DbMigrate     : Migrating schema "PUBLIC" to version "2 - table-user"
    ... o.f.c.internal.command.DbMigrate     : Migrating schema "PUBLIC" to version "3 - ajout colonne email"
    ... o.f.c.internal.command.DbMigrate     : Successfully applied 3 migrations to schema "PUBLIC" (execution time 00:00.0XXs)
    ```

3.  **Vérifiez la base de données** via la console H2, qui est activée pour ce projet.
    -   Accédez à la console dans votre navigateur : [http://localhost:8087/h2-console](http://localhost:8087/h2-console)
    -   Assurez-vous que le champ **JDBC URL** est bien `jdbc:h2:file:~/data/flyway` (le même que dans `application.properties`).
    -   Connectez-vous.

4.  Une fois connecté, vous pouvez constater que les tables `BOOKS` et `USERS` ont été créées et modifiées conformément aux scripts de migration, et que la table `FLYWAY_SCHEMA_HISTORY` contient la trace de ces opérations.