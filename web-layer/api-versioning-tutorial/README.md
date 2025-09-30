# Tutoriel sur le Versioning d'API

Ce tutoriel démontre plusieurs stratégies courantes pour versionner une API REST dans une application Spring Boot. Le versioning est crucial pour faire évoluer une API sans perturber les clients existants.

## Stratégies de Versioning Démontrées

Ce projet illustre trois approches populaires pour le versioning d'API :

### 1. Versioning par l'URL (URL Path Versioning)

C'est l'approche la plus simple et la plus directe. La version de l'API est incluse directement dans le chemin de l'URL.

-   **Endpoint V1**: `GET /api/user/v1`
-   **Endpoint V2**: `GET /api/user/v2`

**Avantages**:
-   Très explicite et facile à comprendre.
-   Simple à mettre en œuvre et à documenter.

**Inconvénients**:
-   Enfreint le principe REST selon lequel une URI doit représenter une ressource unique.

### 2. Versioning par Paramètre de Requête (Query Parameter Versioning)

La version est spécifiée via un paramètre dans l'URL de la requête.

-   **Endpoint V1**: `GET /api/user?version=1`
-   **Endpoint V2**: `GET /api/user?version=2`

**Avantages**:
-   Ne pollue pas l'URI de base.
-   Facile à utiliser pour les tests dans un navigateur.

**Inconvénients**:
-   Peut être moins visible que le versioning par URL.

### 3. Versioning par En-tête (Header Versioning)

La version est spécifiée dans un en-tête HTTP personnalisé (par exemple, `X-API-VERSION`).

-   **Endpoint V1**: `GET /api/user/header` avec l'en-tête `X-API-VERSION: 1`
-   **Endpoint V2**: `GET /api/user/header` avec l'en-tête `X-API-VERSION: 2`

**Avantages**:
-   Garde l'URI propre et focalisée sur la ressource.
-   Considérée par beaucoup comme une approche plus "pure" de REST.

**Inconvénients**:
-   Moins accessible pour des tests rapides via un navigateur.

## Comment Tester

1.  Démarrez l'application.
2.  Utilisez un outil comme `curl` ou Postman pour tester les différents endpoints et stratégies.

**Exemples avec `curl`**:

```bash
# Versioning par URL
curl http://localhost:8080/api/user/v1
curl http://localhost:8080/api/user/v2

# Versioning par Paramètre de Requête
curl http://localhost:8080/api/user?version=1
curl http://localhost:8080/api/user?version=2

# Versioning par En-tête
curl -H "X-API-VERSION: 1" http://localhost:8080/api/user/header
curl -H "X-API-VERSION: 2" http://localhost:8080/api/user/header
```
