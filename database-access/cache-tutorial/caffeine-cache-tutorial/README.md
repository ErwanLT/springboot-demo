# Tutoriel sur le Caching avec Caffeine et Spring Boot

Ce tutoriel explique comment intégrer [Caffeine](https://github.com/ben-manes/caffeine), un cache en mémoire Java 8 haute performance, dans une application Spring Boot.

## Concepts Clés

- **Caffeine**: Une bibliothèque de cache quasi-optimale avec une API flexible pour configurer l'éviction, l'expiration, et plus encore.
- **`CaffeineCacheManager`**: L'implémentation du `CacheManager` de Spring qui utilise Caffeine. Spring Boot l'auto-configure si Caffeine est détecté sur le classpath.
- **Configuration personnalisée**: Bien que Spring Boot puisse auto-configurer Caffeine, ce tutoriel montre comment créer une configuration personnalisée pour définir des stratégies d'expiration ou de taille.

## Fonctionnalités Démontrées

Ce projet reprend l'exemple du service de gestion de livres (`BookService`) et remplace le cache par défaut par Caffeine.

- La configuration du cache est faite dans `CaffeineCacheConfig.java`, où l'on définit une politique d'expiration de 10 minutes et une taille maximale de 500 entrées.
- Le reste de l'application (service, contrôleur) reste inchangé grâce à l'abstraction de cache de Spring.

## Comment Tester

1.  Démarrez l'application `CaffeineCacheApplication`.
2.  Utilisez un outil comme `curl` ou Postman.
3.  **Récupérez un livre pour la première fois :**
    ```bash
    curl http://localhost:8080/books/9782253006329
    ```
    Vous remarquerez un délai de 3 secondes (simulé).
4.  **Récupérez le même livre une deuxième fois :**
    ```bash
    curl http://localhost:8080/books/9782253006329
    ```
    La réponse sera quasi instantanée, car elle provient du cache Caffeine.
