# Vaadin Tutorial

Ce module démontre l'utilisation de Vaadin pour créer des applications web modernes avec Spring Boot.

## Description

Ce tutoriel présente deux applications web construites avec Vaadin et Spring Boot :
1. Une application CRUD simple pour la gestion de produits
2. Une application Pokédex interactive pour explorer les Pokémon

## Sous-modules

### 1. CRUD Tutorial

Une application simple de gestion de produits qui démontre les fonctionnalités CRUD (Create, Read, Update, Delete) avec Vaadin.

#### Fonctionnalités
- Liste des produits avec grille
- Formulaire d'édition
- Filtrage des produits
- Validation des données
- Interface utilisateur réactive

#### Structure
- `ProductView` : Vue principale avec la grille et le formulaire
- `ProductForm` : Formulaire d'édition des produits
- `Product` : Entité JPA
- `ProductRepository` : Repository Spring Data

### 2. Pokédex

Une application interactive de Pokédex qui permet d'explorer les Pokémon par génération et par type.

#### Fonctionnalités
- Navigation par génération
- Liste des types de Pokémon
- Détails des Pokémon
- Interface utilisateur moderne
- Données chargées depuis des fichiers JSON

#### Structure
- `MainView` : Layout principal avec navigation
- `GenView` : Vue par génération
- `TypesListView` : Liste des types
- `PokemonDetailView` : Détails d'un Pokémon
- `PokemonService` : Service de gestion des données
- `NavigationService` : Service de navigation

## Configuration

Les deux applications utilisent :
- Spring Boot
- Vaadin Flow
- Base de données H2 (pour CRUD)
- Thème personnalisé (pour Pokédex)

## Comment Exécuter

1. Pour le CRUD Tutorial :
```bash
cd crud-tutorial
mvn spring-boot:run
```

2. Pour le Pokédex :
```bash
cd pokedex
mvn spring-boot:run
```

## Points Clés

- Utilisation de Vaadin Flow pour l'interface utilisateur
- Intégration avec Spring Boot
- Composants Vaadin réutilisables
- Gestion d'état avec les services
- Navigation entre les vues
- Validation des formulaires
- Gestion des données avec Spring Data 