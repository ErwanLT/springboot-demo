# Tutoriel Library Tools

Tutoriel Spring AI avec base H2 et endpoints de questions/réponses.

## Description

- Stockage H2 en mémoire.
- Intégration Ollama via `spring.ai.ollama.*`.

## Endpoints

- `GET /api/library/ask?question=...`
- `POST /api/library/ask` avec un JSON du type :

```json
{ "question": "..." }
```

## Prérequis

- Ollama accessible sur `http://localhost:11434`.

## Comment l'exécuter

```bash
mvn spring-boot:run
```
