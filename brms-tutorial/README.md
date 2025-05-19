# Système de gestion de règles métier (BRMS)

Ce projet contient des tutoriels sur l'utilisation de différents moteurs de règles métier (BRMS - Business Rules Management System) avec Spring Boot.

## Tutoriels disponibles

### [Drools](drools-tutorial)
Drools est un moteur de règles métier puissant et mature développé par Red Hat. Ce tutoriel montre comment :
- Intégrer Drools dans une application Spring Boot
- Définir et gérer des règles métier de manière déclarative
- Utiliser le moteur d'inférence Rete pour une exécution efficace des règles
- Implémenter des cas d'usage complexes comme la validation de transactions bancaires

### [Easy Rules](easyrules-tutorial)
Easy Rules est une bibliothèque Java légère et simple pour la gestion de règles métier. Ce tutoriel couvre :
- L'intégration d'Easy Rules dans Spring Boot
- La création de règles simples et composées
- L'exécution de règles avec des priorités
- Des exemples pratiques d'utilisation

### [RuleBook](rulebook-tutorial)
RuleBook est un framework de règles métier moderne et flexible. Ce tutoriel explique :
- L'intégration de RuleBook avec Spring Boot
- La définition de règles en utilisant le DSL de RuleBook
- La gestion des règles avec des annotations
- Des cas d'usage concrets

## Prérequis
- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- Spring Boot 3.x

## Installation
1. Clonez ce dépôt
2. Naviguez dans le répertoire souhaité (drools-tutorial, easyrules-tutorial, ou rulebook-tutorial)
3. Exécutez `mvn clean install`
4. Lancez l'application avec `mvn spring-boot:run`

## Structure du projet
Chaque tutoriel est un projet Spring Boot indépendant avec :
- Un fichier `pom.xml` pour la gestion des dépendances
- Un dossier `src` contenant le code source
- Un README détaillé avec des instructions spécifiques
- Des exemples de règles et de code