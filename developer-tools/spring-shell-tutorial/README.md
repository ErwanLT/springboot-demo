# Tutoriel sur Spring Shell

Ce tutoriel montre comment construire une application en ligne de commande (CLI) interactive en utilisant **Spring Shell**.

## Description

Spring Shell permet de créer rapidement des applications CLI robustes en transformant des méthodes Java en commandes de shell. Ce projet simule un petit système de gestion d'entrepôt pour illustrer diverses fonctionnalités.

## Concepts Clés

- **`spring-shell-starter`**: La dépendance principale qui fournit toutes les fonctionnalités de Spring Shell.
- **Application non-web**: Le projet est configuré avec `spring.main.web-application-type=none` pour démarrer comme une application console et non comme un serveur web.
- **`@ShellComponent`**: Annotation de stéréotype qui marque une classe comme contenant des commandes de shell.
- **`@ShellMethod`**: Annotation qui expose une méthode comme une commande exécutable dans le shell. L'attribut `key` définit le nom de la commande.
- **`@ShellOption`**: Annotation pour lier les paramètres d'une méthode aux options de la commande.
- **Composants d'UI Textuelle (TUI)**: Spring Shell offre des composants pour créer des interfaces riches dans le terminal, comme :
    - `TableBuilder`: Pour afficher des données sous forme de tableau formaté.
    - `ProgressView`: Pour afficher une barre de progression pour les tâches longues.
- **Gestion Asynchrone**: L'utilisation de `@Async` sur un service permet d'exécuter des tâches longues sans bloquer le prompt du shell.

## Fonctionnalités Démontrées

- **Commandes CRUD**: Des commandes pour ajouter un produit, lister les produits, et modifier le stock (`add-product`, `list-products`, `add-stock`, `remove-stock`).
- **Affichage en Tableau**: La commande `list-products` utilise `TableBuilder` pour un affichage clair et structuré.
- **Tâches Asynchrones**: Les commandes `run-inventory` et `run-inventory-ui` lancent une tâche simulée de 5 secondes. L'une affiche un simple "spinner" textuel, tandis que l'autre utilise le composant `ProgressView` pour une barre de progression moderne.

## Comment l'exécuter et l'utiliser

1.  **Lancez l'application** à la racine de ce module (`spring-shell-tutorial`) :
    ```bash
    mvn spring-boot:run
    ```

2.  Une fois l'application démarrée, vous verrez un prompt interactif :
    ```
    shell:>
    ```

3.  **Testez les commandes disponibles**. Vous pouvez taper `help` pour voir la liste complète.

    - **Afficher la liste des produits :**
      ```shell
      shell:>list-products
      ```

    - **Ajouter un nouveau produit :**
      ```shell
      shell:>add-product --id K001 --name "Mechanical Keyboard"
      ```

    - **Ajouter du stock :**
      ```shell
      shell:>add-stock --id L001 --quantity 10
      ```

    - **Lancer une tâche longue (avec spinner) :**
      ```shell
      shell:>run-inventory
      ```

    - **Lancer une tâche longue (avec barre de progression) :**
      ```shell
      shell:>run-inventory-ui
      ```

    - **Quitter le shell :**
      ```shell
      shell:>exit
      ```
