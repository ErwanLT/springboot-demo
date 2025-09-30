# Tutoriel sur la Bannière Spring Boot

Ce tutoriel montre comment personnaliser la bannière qui s'affiche au démarrage d'une application Spring Boot.

## Description

Spring Boot affiche une bannière par défaut au démarrage. Cette bannière peut être facilement personnalisée pour afficher votre propre logo, le nom de l'application, ou d'autres informations.

## Implémentation

Il existe plusieurs façons de personnaliser la bannière :

### 1. Bannière Texte (`banner.txt`)

La méthode la plus simple consiste à ajouter un fichier `banner.txt` dans le répertoire `src/main/resources`. Le contenu de ce fichier sera affiché au démarrage.

Vous pouvez utiliser des générateurs d'art ASCII en ligne pour créer votre propre bannière personnalisée.

### 2. Bannière Image

Vous pouvez également utiliser une image comme bannière. Il suffit de placer un fichier `banner.gif`, `banner.jpg`, ou `banner.png` dans le répertoire `src/main/resources`.

### 3. Propriétés de la Bannière

Spring Boot offre plusieurs propriétés pour contrôler le comportement de la bannière dans votre fichier `application.properties`:

-   `spring.main.banner-mode`: Peut être `console` (par défaut), `log`, ou `off` pour désactiver la bannière.
-   `spring.banner.image.location`: Pour spécifier un chemin personnalisé pour votre fichier de bannière image.
-   `spring.banner.charset`: Pour spécifier l'encodage de votre fichier `banner.txt`.

## Comment l'exécuter

1.  Lancez l'application Spring Boot.
2.  Observez la console au démarrage. Vous verrez le contenu du fichier `banner.txt` affiché.

Pour voir l'effet de la désactivation de la bannière, décommentez la ligne suivante dans `src/main/resources/application.properties` et relancez l'application :

```properties
spring.main.banner-mode=off
```