# Tutoriel : Multi-Database avec Spring Boot

Ce tutoriel démontre comment configurer et utiliser plusieurs bases de données dans une seule application Spring Boot. L'exemple utilise Spring Data JPA pour se connecter à deux bases de données différentes :

1.  **PostgreSQL** : pour stocker des entités `User`.
2.  **H2 (en mémoire)** : pour stocker des entités `Order`.

## Concepts Clés

Pour gérer plusieurs sources de données, nous ne pouvons pas nous fier entièrement à l'auto-configuration de Spring Boot. Nous devons définir manuellement les beans nécessaires à la connexion et à la gestion des transactions pour chaque base de données.

Les étapes clés sont :

1.  **Propriétés de Connexion Personnalisées** : Définir des ensembles de propriétés distincts dans `application.properties` avec des préfixes uniques (ex: `app.datasource.users` et `app.datasource.orders`).

2.  **Configuration de Beans `DataSource`** : Créer une classe de configuration pour chaque base de données, où nous définissons explicitement les beans `DataSource`, `LocalContainerEntityManagerFactoryBean`, et `PlatformTransactionManager`.

3.  **Séparation des Repositories** : Utiliser `@EnableJpaRepositories` dans chaque classe de configuration pour spécifier le package où se trouvent les `Repositories` correspondants, et les lier à l' `EntityManagerFactory` et au `TransactionManager` corrects.

4.  **Utilisation de `@Qualifier`** : Bien que non montré dans le service final (car Spring peut déduire le bon repository par type), `@Qualifier` serait nécessaire si vous injectiez directement un `EntityManager` ou un `JdbcTemplate` pour lever l'ambiguïté.

5.  **Annotation `@Primary`** : L'un des `DataSource` (et ses beans associés) doit être marqué comme `@Primary` pour servir de configuration par défaut pour les composants qui ne spécifient pas de source de données.

## Structure du Code

-   `config/`
    -   `UserDbConfig.java` : Configuration pour la base de données PostgreSQL (marquée comme `@Primary`).
    -   `OrderDbConfig.java` : Configuration pour la base de données H2.

-   `users/` : Contient le domaine (`User`) et le repository (`UserRepository`) pour la base de données des utilisateurs.

-   `orders/` : Contient le domaine (`Order`) et le repository (`OrderRepository`) pour la base de données des commandes.

-   `service/AppService.java` : Un service qui utilise les deux repositories pour démontrer que les données sont bien écrites dans leurs bases de données respectives.

-   `controller/DemoController.java` : Un contrôleur REST pour exposer la fonctionnalité.

## Comment Tester

1.  **Prérequis** : Aucune base de données externe n'est requise, car l'exemple utilise deux bases de données H2 en mémoire.

2.  **Démarrez l'application** : Lancez la classe `MultiDatabaseApplication`.

3.  **Exécutez la requête** : Utilisez `curl` ou un autre client HTTP pour envoyer une requête POST avec un corps JSON à l'endpoint de démo.

    ```bash
    curl -X POST http://localhost:8080/create-user-and-order \
         -H "Content-Type: application/json" \
         -d '{
              "username": "john.doe",
              "firstName": "John",
              "lastName": "Doe",
              "email": "john.doe@example.com",
              "productName": "Livre Spring Boot Avancé",
              "productPrice": 45.99
            }'
    ```

4.  **Observez le résultat** : L'application devrait répondre avec un message confirmant la création de l'utilisateur, du produit et de la commande dans leurs bases de données respectives.

    ```
    User 'john.doe' (ID: 1) created in H2. Product 'Livre Spring Boot Avancé' (ID: 1) created in H2. Order (ID: 1) created in H2 linking them.
    ```

5.  **Vérifiez les bases de données** :
    -   Accédez à la console H2 (généralement `http://localhost:8080/h2-console`, URL JDBC: `jdbc:h2:mem:users_db`) et vérifiez que la table `users` contient le nouvel utilisateur.
    -   Accédez à la console H2 (généralement `http://localhost:8080/h2-console`, URL JDBC: `jdbc:h2:mem:orders_db`) et vérifiez que la table `products` et `orders` contiennent les nouvelles entrées.
