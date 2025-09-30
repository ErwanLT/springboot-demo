# Tutoriel sur la Génération de Client OpenAPI

Ce tutoriel montre comment générer automatiquement un client REST (un client Feign Spring Cloud) à partir d'une spécification OpenAPI (anciennement Swagger). Cette approche est extrêmement utile dans une architecture de microservices pour garantir que les clients d'API sont toujours synchronisés avec les fournisseurs d'API.

## Structure du Projet

Ce tutoriel est composé de deux applications Spring Boot :

1.  **`BookApplication`**: Agit comme le **fournisseur de l'API**. Il expose des endpoints pour gérer des livres. La spécification de son API est définie dans un fichier OpenAPI (`books.json`).

2.  **`AuthorApplication`**: Agit comme le **consommateur de l'API**. Il a besoin d'appeler l'API de `BookApplication`. Au lieu d'écrire un client manuellement, il utilise le plugin `openapi-generator-maven-plugin` pour générer un client Feign basé sur le fichier `books.json` de `BookApplication`.

## Mécanisme de Génération de Code

-   Le `pom.xml` du module `AuthorApplication` contient la configuration du `openapi-generator-maven-plugin`.
-   Pendant la phase de `generate-sources` de Maven, ce plugin lit le fichier `src/main/resources/swagger/books.json`.
-   Il génère ensuite une série d'interfaces et de modèles de données dans le répertoire `target/generated-sources/openapi/src/gen/main/java`.
-   Ces classes générées constituent un client Feign entièrement fonctionnel que `AuthorApplication` peut injecter et utiliser pour communiquer avec `BookApplication` de manière typée et sécurisée.

## Comment l'exécuter

1.  **Construire le projet**: À la racine de ce module (`client-generation-tutorial`), exécutez la commande Maven suivante. Cela déclenchera la génération du code client.

    ```bash
    mvn clean install
    ```

2.  **Lancer `BookApplication` (le fournisseur d'API)**: Naviguez dans le dossier `BookApplication` et lancez l'application.

    ```bash
    cd BookApplication
    mvn spring-boot:run
    ```

3.  **Lancer `AuthorApplication` (le consommateur d'API)**: Dans un autre terminal, naviguez dans le dossier `AuthorApplication` et lancez l'application.

    ```bash
    cd AuthorApplication
    mvn spring-boot:run
    ```

4.  **Tester l'intégration**: Vous pouvez maintenant appeler les endpoints de `AuthorApplication`, qui à son tour appellera `BookApplication` en utilisant le client Feign généré.
