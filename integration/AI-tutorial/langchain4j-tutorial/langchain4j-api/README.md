# Tutoriel sur l'API Langchain4j

Ce tutoriel montre comment utiliser la bibliothèque **Langchain4j** directement pour interagir avec un modèle de langage (LLM), ici Mistral AI. Il couvre la configuration manuelle, la création de prompts, la mémoire de chat, le RAG (Retrieval-Augmented Generation), le streaming et la sortie structurée.

## Concepts Clés

- **Langchain4j**: Une bibliothèque Java qui simplifie l'intégration des modèles de langage dans les applications.
- **ChatModel**: L'interface principale pour interagir avec un LLM. L'implémentation `MistralAiChatModel` est utilisée ici.
- **PromptTemplate**: Un modèle pour créer des prompts dynamiques en injectant des variables.
- **Chat Memory**: Permet de conserver le contexte d'une conversation. `MessageWindowChatMemory` est une implémentation simple qui garde les N derniers messages.
- **RAG (Retrieval-Augmented Generation)**: Un pattern où l'on enrichit le prompt avec des informations pertinentes extraites d'une base de connaissances (ici, un fichier texte) pour obtenir des réponses plus précises.
- **EmbeddingModel & EmbeddingStore**: Utilisés dans le RAG pour convertir le texte en vecteurs (embeddings) et les stocker pour la recherche de similarité.
- **Streaming**: Permet de recevoir la réponse du modèle en plusieurs parties (tokens) au fur et à mesure qu'elle est générée.
- **Structured Output**: Permet de forcer le modèle à répondre dans un format spécifique, comme le JSON, en fournissant un schéma.

## Fonctionnalités Démontrées

- **Configuration manuelle**: Le `ChatService` configure manuellement les beans `MistralAiChatModel`, `MistralAiEmbeddingModel`, et `MistralAiStreamingChatModel`.
- **Prompt dynamique**: L'endpoint `/joke` utilise un `PromptTemplate` pour générer une blague.
- **Mémoire de conversation**: L'endpoint `/name` simule une conversation où le modèle se souvient d'un nom donné précédemment.
- **RAG**: L'endpoint `/RAG` répond à une question sur les axolotls en se basant sur le contenu du fichier `about-axolotl.txt`.
- **Streaming de réponse**: L'endpoint `/streaming` écrit un poème et affiche la réponse en temps réel dans la console du serveur.
- **Sortie structurée (JSON)**: L'endpoint `/structured` demande au modèle d'extraire des informations sur un livre et de les retourner au format JSON, qui est ensuite mappé sur un record Java `Book`.

## Comment Tester

1.  Démarrez l'application. Vous devez fournir une clé d'API Mistral via la propriété `mistral.api.key` (par exemple, en exportant la variable d'environnement `export MISTRAL_API_KEY=votre_clé`).

2.  Utilisez `curl` pour interroger les endpoints :

    - **Générer une blague :**
      ```bash
      curl -X POST "http://localhost:8080/joke?subject=les%20développeurs&adjective=drôle"
      ```

    - **Tester la mémoire de chat :**
      ```bash
      curl http://localhost:8080/name
      ```

    - **Poser une question au RAG :**
      ```bash
      curl http://localhost:8080/RAG
      ```

    - **Générer un poème en streaming (vérifiez la console de l'application) :**
      ```bash
      curl http://localhost:8080/streaming
      ```

    - **Obtenir une réponse structurée :**
      ```bash
      curl http://localhost:8080/structured
      ```