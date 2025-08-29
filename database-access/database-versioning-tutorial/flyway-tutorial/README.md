# Versioning de base de données avec Flyway

Ce tutoriel montre comment utiliser **Flyway** pour gérer les migrations de schéma de base de données dans une application Spring Boot.

## Description

Flyway est un outil de migration de base de données qui favorise la simplicité et la convention plutôt que la configuration. Il utilise des scripts SQL pour versionner l'état de votre base de données.

## Concepts Clés

- **Migrations SQL** : Les changements de schéma sont écrits dans des fichiers SQL nommés selon une convention précise (ex: `V1__Create_person_table.sql`).
- **Table de métadonnées** : Flyway crée une table dans votre base de données pour suivre les migrations qui ont déjà été appliquées.
- **Intégration Spring Boot** : L'intégration est automatique. Il suffit d'ajouter la dépendance et de placer les scripts de migration dans `src/main/resources/db/migration`.
