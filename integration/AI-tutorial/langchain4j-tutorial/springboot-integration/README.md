# Tutoriel sur l'Intégration Spring Boot de Langchain4j

Ce tutoriel montre comment utiliser l'intégration de **Langchain4j avec Spring Boot** (`langchain4j-spring-boot-starter`). Il se concentre sur l'utilisation des services déclaratifs (`@AiService`) et l'injection d'outils (`@Tool`) que l'IA peut utiliser.

## Concepts Clés

- **`langchain4j-spring-boot-starter`**: Starter qui configure automatiquement les beans de Langchain4j (comme `ChatModel`, `EmbeddingModel`, etc.) en se basant sur les propriétés de l'application.
- **`@AiService`**: Une annotation puissante qui transforme une interface Java en un client pour un modèle de langage. Langchain4j implémente l'interface pour vous, gérant la communication avec l'IA.
- **`@SystemMessage`**: Utilisée dans une interface `@AiService` pour définir le comportement et la personnalité de l'assistant IA.
- **`@Tool`**: Annotation pour marquer des méthodes Java comme des outils qu'un assistant IA peut appeler pour effectuer des actions. L'assistant décide intelligemment quand et comment utiliser ces outils en fonction de la demande de l'utilisateur.
- **Injection de dépendances**: Tous les composants (assistants, outils, services) sont gérés par Spring et injectés là où c'est nécessaire.

## Fonctionnalités Démontrées

- **Configuration automatique**: Le `ChatModel` est configuré via `application.properties`.
- **Assistants multiples**: Création de plusieurs personnalités d'IA (`PoliteAssistant`, `AngryAssistant`, `PirateAssistant`) en utilisant `@AiService`.
- **Sortie structurée facile**: L'interface `SentimentAnalyzer` montre comment le modèle peut retourner directement un `Enum` ou un `boolean` sans manipulation manuelle de JSON.
- **Utilisation d'outils (Tools)**: Le `CookingAssistant` est capable d'utiliser les méthodes du `CookingTools` (annotées avec `@Tool`) pour trouver des recettes ou en suggérer.
- **Contrôleur REST**: Un `IAController` expose les différents assistants via des endpoints REST pour une interaction facile.

## Comment Tester

1.  Démarrez l'application. Vous devez fournir une clé d'API Mistral dans `src/main/resources/application.properties` :
    ```properties
    langchain4j.mistral-ai.api-key=<votre_clé_api>
    ```

2.  Utilisez `curl` pour interroger les endpoints :

    - **Discuter avec l'assistant poli :**
      ```bash
      curl http://localhost:8080/polite
      ```

    - **Discuter avec l'assistant en colère :**
      ```bash
      curl http://localhost:8080/angry
      ```

    - **Demander une recette au chef cuisinier (qui utilisera un outil) :**
      ```bash
      curl "http://localhost:8080/cook?message=Donne-moi%20une%20recette%20de%20crêpes"
      ```

    - **Analyser le sentiment d'un texte :**
      ```bash
      curl "http://localhost:8080/sentiment?message=J'adore%20ce%20film!"
      ```