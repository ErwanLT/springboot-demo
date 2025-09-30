# Tutoriel Serveur d'Autorisation OAuth2

Ce tutoriel montre comment mettre en place un écosystème OAuth2 complet avec Spring Boot, incluant un serveur d'autorisation, un serveur de ressources et une application cliente.

## Description

Ce projet est divisé en trois modules pour illustrer le flux OAuth2 de manière claire et découplée :

1.  **Serveur d'Autorisation (`server`)**: Le cœur du système, responsable de l'authentification des utilisateurs et de l'émission des jetons d'accès.
2.  **Serveur de Ressources (`resources`)**: Une application qui expose des données protégées (par exemple, une API). L'accès à ces ressources nécessite un jeton d'accès valide émis par le serveur d'autorisation.
3.  **Client (`client`)**: Une application qui agit au nom de l'utilisateur pour accéder aux ressources protégées. Elle obtient un jeton d'accès auprès du serveur d'autorisation et l'utilise pour appeler l'API du serveur de ressources.

## Concepts Clés

-   **Spring Authorization Server**: Le projet de la fondation Spring qui facilite la création de serveurs d'autorisation conformes aux spécifications OAuth2 et OpenID Connect.
-   **Serveur de Ressources Spring Security**: Configuration pour protéger des endpoints en validant les jetons JWT.
-   **Client OAuth2 Spring Security**: Configuration pour interagir avec un serveur d'autorisation et gérer le flux d'obtention de jeton.

## Comment l'exécuter

1.  **Lancez le Serveur d'Autorisation**: Naviguez dans le dossier `server` et exécutez `mvn spring-boot:run`.
2.  **Lancez le Serveur de Ressources**: Naviguez dans le dossier `resources` et exécutez `mvn spring-boot:run`.
3.  **Lancez le Client**: Naviguez dans le dossier `client` et exécutez `mvn spring-boot:run`.

## Flux de Test

1.  Accédez à l'application cliente sur `http://localhost:8082`.
2.  Cliquez sur le lien pour accéder aux ressources protégées.
3.  Vous serez redirigé vers la page de connexion du serveur d'autorisation (`http://localhost:9000`).
4.  Authentifiez-vous avec les identifiants configurés (par exemple, `user`/`password`).
5.  Approuvez la demande d'accès du client.
6.  Vous serez redirigé vers l'application cliente, qui affichera les ressources obtenues du serveur de ressources.