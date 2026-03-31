# Tutoriel RAG Boardgame

Tutoriel RAG basé sur Spring AI, Ollama et Chroma pour interroger des règles de jeu.

## Description

- Modèles Ollama configurés via `spring.ai.ollama.*`.
- Vector store Chroma configuré via `spring.ai.vectorstore.chroma.*`.
- Réindexation au démarrage configurable avec `app.rag.reindex-on-startup`.

## Endpoint

- `POST /api/ludo/rules/ask` avec un JSON du type :

```json
{ "question": "...", "game": "..." }
```

## Prérequis

- Ollama accessible sur `http://localhost:11434`.
- Chroma accessible sur `http://localhost:8000`.

## Comment l'exécuter

```bash
mvn spring-boot:run
```
