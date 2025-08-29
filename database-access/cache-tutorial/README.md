# Tutoriel sur le Caching avec Spring Boot

Ce tutoriel explique comment mettre en place un système de cache dans une application Spring Boot pour améliorer les performances en évitant des opérations coûteuses, comme des appels à une base de données ou à des services externes.

## Concepts Clés

-   **`@EnableCaching`**: Annotation à placer sur une classe de configuration pour activer l'abstraction de cache de Spring.
-   **`CacheManager`**: L'interface centrale de l'abstraction de cache de Spring. Ce tutoriel utilise `ConcurrentMapCacheManager`, une implémentation simple basée sur une `ConcurrentHashMap`. Pour des cas d'usage plus avancés, on pourrait utiliser des gestionnaires de cache distribués comme Redis ou Ehcache.
-   **`@Cacheable`**: Annotation qui indique que le résultat d'une méthode peut être mis en cache. Si une entrée est trouvée dans le cache pour les arguments donnés, le résultat mis en cache est retourné sans exécuter la méthode.
-   **`@CachePut`**: Annotation qui met à jour le cache avec le résultat de la méthode. La méthode est toujours exécutée.
-   **`@CacheEvict`**: Annotation qui supprime une ou plusieurs entrées du cache.

## Fonctionnalités Démontrées

Ce projet met en place un cache pour un service de gestion de livres (`BookService`).

-   La récupération d'un livre par son ISBN est mise en cache. Un appel lent à la base de données est simulé pour démontrer le gain de performance.
-   La création ou la mise à jour d'un livre met également à jour l'entrée correspondante dans le cache.
-   La suppression d'un livre invalide (supprime) l'entrée correspondante dans le cache.

## Structure du Code

-   **`CacheConfig`**: Configure le `CacheManager` pour utiliser un cache nommé "books".
-   **`BookService`**: Le service qui contient la logique métier et les annotations de cache (`@Cacheable`, `@CachePut`, `@CacheEvict`).
-   **`BookController`**: Le contrôleur REST qui expose les endpoints pour interagir avec les livres.
-   **`Book`**: L'entité JPA représentant un livre.

## Comment Tester

1.  Démarrez l'application.
2.  Utilisez un outil comme `curl` ou Postman.
3.  **Récupérez un livre pour la première fois :**
    ```bash
    curl http://localhost:8080/books/12345
    ```
    Vous remarquerez un délai de 3 secondes (simulé) et les logs indiqueront que la méthode `findBookByIsbn` a été exécutée.
4.  **Récupérez le même livre une deuxième fois :**
    ```bash
    curl http://localhost:8080/books/12345
    ```
    La réponse sera quasi instantanée, et les logs montreront que la méthode `findBookByIsbn` n'a pas été exécutée. Le résultat provient directement du cache.
5.  **Mettez à jour le livre :**
    ```bash
    curl -X POST http://localhost:8080/books -H "Content-Type: application/json" -d '{"isbn": "12345", "title": "Nouveau Titre"}'
    ```
    Le cache pour l'ISBN "12345" est mis à jour.
6.  **Supprimez le livre :**
    ```bash
    curl -X DELETE http://localhost:8080/books/12345
    ```
    Le cache pour l'ISBN "12345" est invalidé. Si vous essayez de le récupérer à nouveau, la méthode `findBookByIsbn` sera exécutée.