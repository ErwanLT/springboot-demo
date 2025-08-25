# Tutoriel sur la Programmation Asynchrone avec Spring Boot

Ce tutoriel démontre comment utiliser la programmation asynchrone dans une application Spring Boot pour améliorer les performances et la scalabilité. L'exemple se base sur la récupération de différentes informations sur un produit (prix, stock, avis) de manière concurrente.

## Concepts Clés

- **`@EnableAsync`**: Annotation à ajouter à une classe de configuration pour activer la prise en charge de l'exécution de méthodes asynchrones par Spring.
- **`@Async`**: Annotation pour marquer une méthode comme asynchrone. Spring exécutera cette méthode dans un pool de threads séparé.
- **`CompletableFuture`**: Une classe de Java 8 qui représente le résultat futur d'un calcul asynchrone. Elle fournit de nombreuses méthodes pour composer, combiner et gérer les résultats de plusieurs opérations asynchrones.

## Fonctionnalités Démontrées

Ce projet expose une API REST avec deux endpoints pour récupérer les détails d'un produit :

- `GET /products/{id}/details`: Cet endpoint récupère le prix, le stock et les avis d'un produit en parallèle. Si l'une des opérations échoue, un message par défaut est retourné pour cette information, permettant aux autres de s'achever.
- `GET /products/{id}/details-fail-fast`: Similaire au premier endpoint, mais si l'une des opérations asynchrones échoue, l'ensemble de la requête échoue (fail-fast).

## Structure du Code

- **`ProductController`**: Le contrôleur REST qui expose les endpoints de l'API.
- **`ProductService`**: Le service qui orchestre les appels aux méthodes asynchrones. Il montre deux approches pour la gestion des erreurs : une gestion fine avec `exceptionally` et une approche "fail-fast".
- **`ProductAsyncService`**: Ce service contient les méthodes `@Async` qui simulent des appels à des services externes (par exemple, pour récupérer le prix, le stock, etc.). Une latence est simulée pour illustrer l'intérêt de l'approche asynchrone.
- **`AsyncConfig`**: La classe de configuration où l'on active l'asynchronisme avec `@EnableAsync` et où l'on peut potentiellement configurer le pool de threads.

## Comment Tester

1.  Démarrez l'application.
2.  Appelez les endpoints suivants avec un outil comme `curl` ou Postman :
    - `curl http://localhost:8080/products/2/details` (cas nominal)
    - `curl http://localhost:8080/products/1/details` (cas avec erreur gérée)
    - `curl http://localhost:8080/products/1/details-fail-fast` (cas avec erreur "fail-fast")