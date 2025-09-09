/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of drools-tutorial.
 *
 * drools-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * drools-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with drools-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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