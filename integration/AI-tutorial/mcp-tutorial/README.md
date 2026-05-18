# Tutoriel MCP (Model Context Protocol)

Ce tutoriel démontre l'utilisation du Model Context Protocol (MCP) avec Spring AI pour connecter un client IA à un serveur de données/outils distant.

## Architecture

- **mcp-server**: Un serveur Spring Boot qui expose des outils (Tools) via MCP en utilisant le transport SSE (Server-Sent Events).
- **mcp-client**: Un client Spring Boot qui se connecte au serveur MCP, récupère dynamiquement les outils disponibles, et les met à disposition d'un modèle de langage (Ollama).

## mcp-server

Le serveur expose deux outils simples :
1. `getCurrentDateTime`: Retourne la date et l'heure actuelle.
2. `daysBetween`: Calcule le nombre de jours entre deux dates.

Ces outils sont annotés avec `@Tool` et sont automatiquement découverts par l'infrastructure MCP de Spring AI.

## mcp-client

Le client est configuré pour se connecter au serveur MCP via son endpoint SSE. Il utilise un `ChatClient` qui intègre automatiquement les outils distants via le `McpToolCallbackProvider`.

## Prérequis

- Ollama installé et fonctionnel (par défaut sur `http://localhost:11434`).
- Modèle `llama3.2` téléchargé dans Ollama (`ollama pull llama3.2`).

## Comment l'exécuter

1.  **Démarrer le serveur MCP :**
    ```bash
    cd integration/AI-tutorial/mcp-tutorial/mcp-server
    mvn spring-boot:run
    ```
    Le serveur démarrera sur le port `8081`.

2.  **Démarrer le client MCP :**
    ```bash
    cd integration/AI-tutorial/mcp-tutorial/mcp-client
    mvn spring-boot:run
    ```
    Le client démarrera sur le port `8082`.

3.  **Tester l'interaction :**
    Ouvrez un navigateur ou utilisez `curl` pour poser une question au client qui nécessite l'utilisation des outils du serveur :
    ```bash
    curl "http://localhost:8082/api/mcp/ask?question=Quelle+heure+est-il+?"
    ```
    Ou pour tester le calcul de jours :
    ```bash
    curl "http://localhost:8082/api/mcp/ask?question=Combien+de+jours+entre+le+2024-01-01+et+le+2024-12-31+?"
    ```

Le modèle utilisera alors l'outil `getCurrentDateTime` ou `daysBetween` du serveur distant pour répondre avec précision.
