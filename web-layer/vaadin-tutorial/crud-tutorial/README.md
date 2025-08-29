# Tutoriel : Application CRUD avec Vaadin

Ce projet est une démonstration d'une application **CRUD (Create, Read, Update, Delete)** complète, construite avec **Vaadin** et Spring Boot.

## Description

L'application permet de gérer une liste de produits. Elle met en œuvre les fonctionnalités de base que l'on retrouve dans la plupart des applications de gestion :

- Affichage des données dans une grille (Grid).
- Création, édition et suppression d'enregistrements.
- Un formulaire de saisie avec validation.
- Le filtrage des données affichées.

C'est un excellent point de départ pour comprendre les bases du développement d'applications web en Java avec Vaadin.

## Concepts Clés

- **Vaadin Grid** : Le composant principal pour afficher des données tabulaires.
- **Vaadin FormLayout & Binder** : Pour construire des formulaires de saisie et les lier à des objets de données.
- **Spring Data JPA** : Pour la persistance des données dans une base de données (H2 en mémoire ici).
- **Architecture Vue / Service** : Séparation des responsabilités entre l'interface utilisateur et la logique métier.

## Fichiers Principaux

- `ProductView.java` : La vue principale de l'application, qui contient la grille des produits et le formulaire.
- `ProductForm.java` : Le composant réutilisable qui définit le formulaire d'édition des produits.
- `Product.java` : L'entité JPA qui représente un produit.
- `ProductRepository.java` : L'interface Spring Data pour l'accès à la base de données.

## Comment l'exécuter

1.  Naviguez dans le dossier `crud-tutorial`.
2.  Exécutez `mvn spring-boot:run`.
3.  Ouvrez votre navigateur à l'adresse `http://localhost:8080`.
