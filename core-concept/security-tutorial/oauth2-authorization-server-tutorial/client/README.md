# Client OAuth2

Application cliente OAuth2 qui récupère des articles via un resource server.

## Endpoints

- `GET /articles` : appelle le resource server avec un token OAuth2.

## Configuration clé

- Port : `8080`
- Issuer : `http://localhost:9000`
- Client ID : `articles-client`

## Comment l'exécuter

1. Démarrer l'authorization server.
2. Démarrer le resource server.
3. Démarrer ce client :

```bash
mvn spring-boot:run
```

Puis accéder à `GET /articles`.
