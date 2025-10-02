# Versioning de base de données avec Liquibase

Ce tutoriel montre comment utiliser **Liquibase** pour gérer les migrations de schéma de base de données dans une application Spring Boot.

## Description

Liquibase est un outil de migration de base de données indépendant de la base de données qui vous aide à suivre, versionner et déployer les changements de schéma. Il utilise des "changesets" qui peuvent être définis en XML, YAML, JSON ou SQL.

## Concepts Clés

- **Changelog** : Le fichier principal (ex: `db.changelog-master.xml`) qui référence tous les "changesets" à appliquer.
- **Changeset** : Une unité de changement atomique, identifiée par un auteur et un `id`. Liquibase s'assure qu'un changeset n'est exécuté qu'une seule fois.
- **Tables de métadonnées**: Liquibase crée deux tables (`DATABASECHANGELOG` et `DATABASECHANGELOGLOCK`) pour suivre les changesets appliqués et gérer les accès concurrents à la base de données lors des migrations.
- **Indépendance de la base de données** : Les changements définis en XML, YAML ou JSON sont traduits en SQL spécifique à la base de données cible, offrant une grande portabilité.
- **Intégration Spring Boot** : L'intégration est automatique avec la dépendance `liquibase-core`. Spring Boot exécute les migrations Liquibase au démarrage de l'application.

## Comment l'exécuter et vérifier

1.  **Lancez l'application** en exécutant la méthode `main` de `LiquibaseApplication.java` ou via Maven à la racine de ce module :
    ```bash
    mvn spring-boot:run
    ```

2.  **Observez les logs de démarrage**. Vous verrez que Liquibase lit le `changelog-master.xml` et exécute chaque changeset qu'il inclut (qu'il soit en SQL, XML ou JSON) :
    ```
    ... INFO ... liquibase.changelog.ChangeSet      : ChangeSet db/changelog/changesets/001_create_tables.sql::raw::includeAll ran successfully
    ... INFO ... liquibase.changelog.ChangeSet      : ChangeSet db/changelog/changesets/002_add_users_table.xml::002-create-users-table::admin ran successfully
    ... INFO ... liquibase.changelog.ChangeSet      : ChangeSet db/changelog/changesets/003_add_mail_column.json::003-add-mail-column::admin ran successfully
    ```

3.  **Vérifiez la base de données** via la console H2, qui est activée pour ce projet.
    -   Accédez à la console dans votre navigateur : [http://localhost:8088/h2-console](http://localhost:8088/h2-console)
    -   Assurez-vous que le champ **JDBC URL** est bien `jdbc:h2:file:~/data/liquibase`.
    -   Connectez-vous.

4.  Une fois connecté, vous pouvez observer :
    -   Les tables `DATABASECHANGELOG` et `DATABASECHANGELOGLOCK` créées par Liquibase.
    -   Les tables `BOOK` et `USERS` créées et modifiées par les différents changesets.