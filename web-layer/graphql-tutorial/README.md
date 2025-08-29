# Tutoriel GraphQL avec Spring Boot

Ce tutoriel présente l'intégration de GraphQL dans une application Spring Boot, en suivant une approche progressive et pratique.

## À propos de GraphQL

GraphQL est un langage de requête et un runtime pour les API qui permet aux clients de demander exactement les données dont ils ont besoin. Il offre :
- Une requête unique pour récupérer plusieurs ressources
- Un typage fort pour les données
- Une documentation automatique de l'API
- Une flexibilité pour les clients

## Contenu du tutoriel

### [Partie 1 : Mise en place](https://www.sfeir.dev/partie-1-mise-en-place/)
- Configuration initiale du projet Spring Boot
- Ajout des dépendances GraphQL
- Structure de base du projet
- Configuration de Spring for GraphQL

### [Partie 2 : Le schéma](https://www.sfeir.dev/partie-2-le-schema/)
- Définition du schéma GraphQL
- Types et champs
- Requêtes et mutations
- Validation et sécurité
- Bonnes pratiques de conception

### [Partie 3 : Controllers](https://www.sfeir.dev/partie-3-controllers/)
- Création des entités Java
- Implémentation des DataFetchers
- Gestion des requêtes et mutations
- Utilisation des annotations Spring for GraphQL
- Exemples de code

### [Partie 4 : Tests unitaires](https://www.sfeir.dev/partie-4-tests-unitaires/)
- Configuration des tests GraphQL
- Tests des requêtes
- Tests des mutations
- Mocks et assertions
- Exemples de tests

### [Partie 5 : Documentation](https://www.sfeir.dev/partie-5-documentation/)
- Documentation automatique du schéma
- Interface GraphiQL
- Exploration de l'API
- Exemples de requêtes
- Bonnes pratiques de documentation

### [Partie 6 : Gestion des erreurs](https://www.sfeir.dev/partie-6-gestion-des-erreurs/)
- Configuration de la gestion d'erreurs
- Utilisation de DataFetcherExceptionResolver
- Types d'erreurs personnalisés
- Validation des données
- Bonnes pratiques de gestion d'erreurs

## Prérequis
- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- Spring Boot 3.x
- IDE (IntelliJ IDEA recommandé)

## Installation
1. Clonez ce dépôt
2. Naviguez dans le répertoire `graphql-tutorial`
3. Exécutez `mvn clean install`
4. Lancez l'application avec `mvn spring-boot:run`

## Structure du projet
```
src/
├── main/
│   ├── java/
│   │   └── fr/eletutour/
│   │       ├── config/         # Configuration GraphQL
│   │       ├── model/          # Entités et DTOs
│   │       ├── controller/     # Contrôleurs GraphQL
│   │       └── service/        # Services métier
│   └── resources/
│       └── graphql/           # Schémas GraphQL
```

## Exemple de schéma GraphQL
```graphql
type Query {
    book(id: ID!): Book
    books: [Book!]!
}

type Book {
    id: ID!
    title: String!
    author: Author!
}

type Author {
    id: ID!
    name: String!
    books: [Book!]!
}
```

## Bonnes pratiques
1. **Organisation du code** :
   - Séparer les schémas par domaine
   - Utiliser des DataFetchers pour la logique complexe
   - Documenter les types et les champs

2. **Performance** :
   - Utiliser le DataLoader pour éviter le N+1
   - Mettre en cache les requêtes fréquentes
   - Optimiser les résolveurs

3. **Sécurité** :
   - Valider les entrées
   - Implémenter l'authentification
   - Gérer les autorisations

## Ressources supplémentaires
- [Documentation Spring for GraphQL](https://docs.spring.io/spring-graphql/docs/current/reference/html/)
- [Spécification GraphQL](https://spec.graphql.org/)
- [GraphQL Java](https://www.graphql-java.com/)
- [GraphiQL](https://github.com/graphql/graphiql)