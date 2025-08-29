# Tutoriel Spring Boot Admin

Ce tutoriel montre comment mettre en place un serveur **Spring Boot Admin** pour monitorer et gérer des applications Spring Boot.

## Description

Spring Boot Admin fournit une interface web simple et claire pour visualiser les informations de vos applications. Il s'appuie sur les **Actuators** de Spring Boot pour collecter et afficher des métriques, des logs, la configuration, et bien plus encore.

L'architecture est composée de deux parties :

1.  **Un Serveur (Server)** : L'application centrale qui fournit l'interface de monitoring.
2.  **Un ou plusieurs Clients (Client)** : Vos applications Spring Boot qui s'enregistrent auprès du serveur.

## Sous-modules

- **[springboot-admin-server](springboot-admin-server)** : Le serveur de monitoring central.
- **[springboot-admin-client](springboot-admin-client)** : Une application Spring Boot configurée pour s'enregistrer en tant que client.

## Comment l'exécuter

1.  **Lancez le serveur** : Naviguez dans le dossier `springboot-admin-server` et exécutez `mvn spring-boot:run`. L'interface sera disponible sur `http://localhost:8097`.
2.  **Lancez le client** : Naviguez dans le dossier `springboot-admin-client` et exécutez `mvn spring-boot:run`.
3.  **Observez** : Après quelques instants, l'application `admin-client` apparaîtra dans l'interface du serveur, vous permettant de l'inspecter.
