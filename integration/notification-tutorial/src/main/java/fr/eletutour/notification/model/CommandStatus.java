package fr.eletutour.notification.model;

public enum CommandStatus {
    PENDING,        // En attente, pas encore en préparation
    IN_PREPARATION, // En cours de préparation
    READY,          // Prête à être récupérée
    COMPLETED       // Terminée et récupérée
}
