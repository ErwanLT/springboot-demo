# Tutoriel sur les Notifications en Temps Réel avec Server-Sent Events (SSE)

Ce tutoriel démontre comment implémenter un système de notifications en temps réel (push) du serveur vers le client en utilisant les **Server-Sent Events (SSE)** avec Spring Boot. L'exemple est basé sur un système de gestion de commandes où les clients sont notifiés des changements de statut d'une commande.

## Concepts Clés

-   **Server-Sent Events (SSE)**: Une technologie web qui permet à un serveur de pousser des données vers un client de manière asynchrone, une fois que la connexion initiale a été établie. C'est une alternative plus simple aux WebSockets pour les cas d'usage où la communication est unidirectionnelle (serveur vers client).
-   **`SseEmitter`**: Une classe de Spring MVC qui représente une connexion SSE. Elle permet à l'application de gérer l'envoi d'événements au client de manière asynchrone.

## Fonctionnalités Démontrées

-   Un client peut s'abonner à un flux de notifications via un endpoint `GET /notifications/subscribe`.
-   Lorsqu'une commande est créée ou que son statut est mis à jour, le serveur envoie une notification contenant les informations de la commande à tous les clients abonnés.
-   La communication est unidirectionnelle : du serveur vers le client.

## Structure du Code

-   **`NotificationController`**: Expose l'endpoint `/notifications/subscribe` auquel les clients se connectent pour recevoir des événements.
-   **`NotificationService`**: Gère la liste des clients connectés (`SseEmitter`). Il fournit une méthode pour envoyer des données à tous les clients connectés.
-   **`CommandController`**: Un contrôleur REST standard pour gérer les commandes (création, mise à jour de statut).
-   **`CommandService`**: La logique métier pour les commandes. Après chaque modification d'une commande, il appelle le `NotificationService` pour notifier les clients.
-   **`Command`** et **`CommandStatus`**: Le modèle de données pour les commandes.

## Comment Tester

1.  **Démarrez l'application.**
2.  **Abonnez-vous aux notifications.** Ouvrez un terminal et utilisez `curl` pour vous abonner au flux d'événements. La commande restera en attente, gardant la connexion ouverte.
    ```bash
    curl -N http://localhost:8080/notifications/subscribe
    ```
3.  **Créez une commande.** Ouvrez un *autre* terminal et envoyez une requête POST pour créer une commande.
    ```bash
    curl -X POST http://localhost:8080/commands -H "Content-Type: application/json" -d '{"customerName": "John Doe", "product": "Livre Spring Boot"}'
    ```
4.  **Observez la notification.** Dans le premier terminal (celui de `curl .../subscribe`), vous devriez voir un événement apparaître, contenant les données de la commande que vous venez de créer au format JSON.
    ```
    data:{"id":1,"customerName":"John Doe","product":"Livre Spring Boot","status":"PENDING"}
    ```
5.  **Mettez à jour le statut de la commande.** Dans le deuxième terminal, envoyez une requête PUT.
    ```bash
    curl -X PUT "http://localhost:8080/commands/1/status?status=SHIPPED"
    ```
6.  **Observez la nouvelle notification.** Le premier terminal affichera un nouvel événement avec le statut mis à jour.
    ```
    data:{"id":1,"customerName":"John Doe","product":"Livre Spring Boot","status":"SHIPPED"}
    ```

## Client Frontend (Exemple)

Pour intégrer cela dans un frontend JavaScript, vous utiliseriez l'API `EventSource` :

```javascript
const eventSource = new EventSource("http://localhost:8080/notifications/subscribe");

eventSource.onmessage = function(event) {
    const command = JSON.parse(event.data);
    console.log("Nouvelle notification:", command);
    // Mettre à jour l'interface utilisateur ici
};

eventSource.onerror = function(err) {
    console.error("Erreur EventSource:", err);
};
```