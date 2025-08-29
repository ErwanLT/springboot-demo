# Application Pokédex avec Vaadin

Ce projet est une application web de type **Pokédex** interactive, construite avec **Vaadin** et Spring Boot.

## Description

Plus complexe que l'application CRUD, ce Pokédex démontre comment construire une interface utilisateur riche et dynamique avec Vaadin. Il permet aux utilisateurs de :

- Naviguer à travers les différentes générations de Pokémon.
- Filtrer les Pokémon par type.
- Consulter une vue détaillée pour chaque Pokémon, affichant ses statistiques, ses évolutions, et d'autres informations.

Les données des Pokémon sont chargées à partir de fichiers JSON, montrant comment Vaadin peut s'intégrer avec diverses sources de données.

## Concepts Clés

- **Vues et Navigation** : Utilisation du routeur de Vaadin pour naviguer entre différentes vues (`MainView`, `GenView`, `PokemonDetailView`).
- **Composants d'UI dynamiques** : Création d'une interface qui se met à jour dynamiquement en fonction des actions de l'utilisateur.
- **Services** : Encapsulation de la logique de chargement et de traitement des données dans des services Spring (`PokemonService`, `NavigationService`).
- **Data Binding** : Liaison des données des Pokémon aux composants de l'interface utilisateur.

## Fichiers Principaux

- `MainView.java` : Le layout principal de l'application qui gère la navigation.
- `GenView.java` : La vue qui affiche les Pokémon d'une génération spécifique sous forme de cartes.
- `PokemonDetailView.java` : La vue qui affiche les informations détaillées d'un Pokémon sélectionné.
- `PokemonService.java` : Le service responsable du chargement et de l'accès aux données des Pokémon depuis les fichiers JSON.

## Comment l'exécuter

1.  Naviguez dans le dossier `pokedex`.
2.  Exécutez `mvn spring-boot:run`.
3.  Ouvrez votre navigateur à l'adresse `http://localhost:8080`.
