# Tutoriel Blockchain

Mini API REST de blockchain simplifiée (BaguetteCoin).

## Endpoints

- `GET /api/chain`
- `POST /api/transactions/new` (body avec `sender`, `recipient`, `amount`)
- `POST /api/mine` (body avec `miner`)
- `GET /api/validate`
- `GET /api/pending`

## Comment l'exécuter

```bash
mvn spring-boot:run
```
