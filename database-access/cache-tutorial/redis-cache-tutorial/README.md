# Tutoriel sur le Caching avec Redis et Spring Boot

Ce tutoriel explique comment intégrer [Redis](https://redis.io/), un data store en mémoire ultra-rapide, comme backend de cache dans une application Spring Boot.

## Concepts Clés

- **Redis**: Un data store NoSQL clé-valeur, souvent utilisé comme cache distribué, messagerie, et base de données.
- **`RedisCacheManager`**: L'implémentation du `CacheManager` de Spring qui utilise Redis. Spring Boot l'auto-configure si `spring-boot-starter-data-redis` est détecté sur le classpath et qu'une connexion Redis est disponible.
- **Configuration**: La connexion à Redis se fait simplement via le fichier `application.properties`.
- **RedisInsight**: Une interface graphique web pour visualiser et gérer les données dans Redis.

## Fonctionnalités Démontrées

Ce projet reprend l'exemple du service de gestion de livres (`BookService`) et utilise Redis comme système de cache.

- La configuration du cache est auto-gérée par Spring Boot.
- Le `docker-compose.yml` inclut Redis et **RedisInsight** pour une expérience de développement complète.

## Comment Tester

1.  **Démarrez Redis et RedisInsight** avec Docker Compose :
    ```bash
    docker-compose up -d
    ```
2.  **Accédez à RedisInsight** via votre navigateur à l'adresse `http://localhost:8001`.
    - Acceptez les conditions d'utilisation.
    - Ajoutez une nouvelle base de données Redis ("Add Redis Database").
    - Connectez-vous en utilisant `redis` comme **Host** et `6379` comme **Port**.

3.  Démarrez l'application `RedisCacheApplication`.

4.  Utilisez un outil comme `curl` ou Postman pour interroger l'API.
    - **Récupérez un livre pour la première fois :**
      ```bash
      curl http://localhost:8080/books/9782253006329
      ```
      Vous remarquerez un délai de 3 secondes (simulé).

5.  **Visualisez le cache dans RedisInsight** :
    - Rafraîchissez la vue dans RedisInsight. Vous devriez voir une nouvelle clé apparaître (par exemple, `books::9782253006329`). En cliquant dessus, vous verrez les données du livre sérialisées.

6.  **Récupérez le même livre une deuxième fois :**
    ```bash
    curl http://localhost:8080/books/9782253006329
    ```
    La réponse sera quasi instantanée, car elle provient du cache Redis.

7.  Vous pouvez arrêter les services avec `docker-compose down`.