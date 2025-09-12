package fr.eletutour.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Représente une transaction bancaire dans le système.
 * Cette classe est utilisée comme fait dans le moteur de règles Drools
 * pour la validation et le traitement des opérations bancaires.
 */
@Entity
public class Transaction implements DroolsFact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Le type de transaction est requis")
    private String type; // "DEPOSIT" ou "WITHDRAW"
    @Positive(message = "Le montant doit être positif")
    private double amount;
    @NotBlank(message = "Le numéro de compte ne peut pas être vide")
    private String accountNumber;
    private boolean approved;
    private String message;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Transaction() {
    }

    /**
     * Constructeur avec paramètres pour créer une transaction.
     *
     * @param type Le type de transaction ("DEPOSIT" ou "WITHDRAW")
     * @param amount Le montant de la transaction
     * @param accountNumber Le numéro du compte concerné
     */
    public Transaction(String type, double amount, String accountNumber) {
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.approved = false;
        this.message = "";
    }

    /**
     * Récupère l'identifiant unique de la transaction.
     *
     * @return L'identifiant de la transaction
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la transaction.
     *
     * @param id Le nouvel identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère le type de la transaction.
     *
     * @return Le type de transaction ("DEPOSIT" ou "WITHDRAW")
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type de la transaction.
     *
     * @param type Le type de transaction ("DEPOSIT" ou "WITHDRAW")
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Récupère le montant de la transaction.
     *
     * @return Le montant
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Définit le montant de la transaction.
     *
     * @param amount Le montant
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Récupère le numéro du compte concerné par la transaction.
     *
     * @return Le numéro de compte
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Définit le numéro du compte concerné par la transaction.
     *
     * @param accountNumber Le numéro de compte
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Vérifie si la transaction a été approuvée.
     *
     * @return true si la transaction est approuvée, false sinon
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Définit le statut d'approbation de la transaction.
     *
     * @param approved Le nouveau statut d'approbation
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Récupère le message associé à la transaction.
     *
     * @return Le message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message associé à la transaction.
     *
     * @param message Le nouveau message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retourne une représentation textuelle de la transaction.
     *
     * @return Une chaîne de caractères décrivant la transaction
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", accountNumber='" + accountNumber + '\'' +
                ", approved=" + approved +
                ", message='" + message + '\'' +
                '}';
    }
}