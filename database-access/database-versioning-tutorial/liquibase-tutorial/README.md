# Versioning de base de données avec Liquibase

Ce tutoriel montre comment utiliser **Liquibase** pour gérer les migrations de schéma de base de données dans une application Spring Boot.

## Description

Liquibase est un outil de migration de base de données indépendant de la base de données qui vous aide à suivre, versionner et déployer les changements de schéma de base de données. Il utilise des "changesets" qui peuvent être définis en XML, YAML ou SQL.

## Concepts Clés

- **Changelog** : Le fichier principal (ex: `db.changelog-master.yaml`) qui référence tous les "changesets".
- **Changeset** : Une unité de changement atomique, identifiée par un auteur et un id.
- **Indépendance de la base de données** : Les changements définis en XML ou YAML sont traduits en SQL spécifique à la base de données cible.
- **Intégration Spring Boot** : L'intégration est automatique. Il suffit d'ajouter la dépendance et de fournir un fichier de changelog.
