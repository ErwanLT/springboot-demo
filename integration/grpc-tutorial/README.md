# Tutoriel gRPC avec Spring Boot

Ce tutoriel montre comment créer un service et un client gRPC simples avec Spring Boot.

## Description

Ce projet démontre comment définir un service en utilisant Protocol Buffers, générer le code Java correspondant, et implémenter le service en utilisant les outils fournis par l'écosystème Spring gRPC.

## Implémentation

1.  **Définition du Service (`.proto`)**: Le service est défini dans le fichier `src/main/proto/greeting.proto`. Il spécifie un `GreetingService` avec une méthode `sayHello`.

    ```protobuf
    syntax = "proto3";

    option java_multiple_files = true;
    option java_package = "fr.eletutour.integration.grpc";
    option java_outer_classname = "GreetingProto";

    package grpc;

    // The greeting service definition.
    service GreetingService {
      // Sends a greeting
      rpc sayHello (HelloRequest) returns (HelloReply) {}
    }

    // The request message containing the user's name.
    message HelloRequest {
      string name = 1;
    }

    // The response message containing the greetings
    message HelloReply {
      string message = 1;
    }
    ```

2.  **Génération de Code**: Le `protobuf-maven-plugin` est configuré dans le `pom.xml` pour générer automatiquement les classes Java à partir du fichier `.proto` lors de la phase de build de Maven.

3.  **Implémentation du Service**: La classe `GreetingServiceImpl` étend la classe de base générée et implémente la logique de la méthode `sayHello`. L'annotation `@GrpcService` l'expose en tant que service gRPC.

## Comment l'exécuter

1.  **Construire le projet**: Cette étape est nécessaire pour déclencher la génération du code gRPC.

    ```bash
    mvn clean install
    ```

2.  **Lancer l'application**: Une fois le projet construit, lancez l'application Spring Boot.

    ```bash
    mvn spring-boot:run
    ```

    Le serveur gRPC démarrera sur le port `9090` (par défaut).

## Comment Tester

Vous pouvez tester le service en utilisant un client gRPC comme `grpcurl`.

1.  **Lister les services**:

    ```bash
    grpcurl -plaintext localhost:9090 list
    ```

2.  **Appeler la méthode `sayHello`**:

    ```bash
    grpcurl -plaintext -d '{"name": "World"}' localhost:9090 grpc.GreetingService/sayHello
    ```

    Vous devriez recevoir la réponse suivante :

    ```json
    {
      "message": "Hello, World"
    }
    ```
