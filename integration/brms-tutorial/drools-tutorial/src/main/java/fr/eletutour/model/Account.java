package fr.eletutour.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * Représente un compte bancaire dans le système.
 * Cette classe est utilisée comme fait dans le moteur de règles Drools
 * pour la prise de décision sur les opérations bancaires.
 */
@Entity
public class Account implements DroolsFact {
    @Id
    @NotBlank(message = "Le numéro de compte ne peut pas être vide")
    private String accountNumber;
    private double balance;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Account() {
    }

    /**
     * Constructeur avec paramètres pour créer un compte.
     *
     * @param accountNumber Le numéro du compte
     * @param balance Le solde initial du compte
     */
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    /**
     * Récupère le numéro du compte.
     *
     * @return Le numéro du compte
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Définit le numéro du compte.
     *
     * @param accountNumber Le nouveau numéro de compte
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Récupère le solde du compte.
     *
     * @return Le solde actuel
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Définit le solde du compte.
     *
     * @param balance Le nouveau solde
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Retourne une représentation textuelle du compte.
     *
     * @return Une chaîne de caractères décrivant le compte
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}