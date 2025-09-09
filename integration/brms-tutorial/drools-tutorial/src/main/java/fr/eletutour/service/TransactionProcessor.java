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
package fr.eletutour.service;

import fr.eletutour.annotation.DroolsRule;
import fr.eletutour.exception.TransactionFailedException;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.repository.AccountRepository;
import fr.eletutour.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Processeur pour exécuter les transactions avec les règles Drools.
 * Cette classe est responsable de l'exécution des transactions bancaires
 * en utilisant le moteur de règles Drools pour appliquer la logique métier.
 * Elle gère également la persistance des transactions et des comptes.
 */
@Component
public class TransactionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessor.class);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Constructeur du processeur de transactions.
     *
     * @param accountRepository Le repository pour les comptes
     * @param transactionRepository Le repository pour les transactions
     */
    public TransactionProcessor(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Exécute une transaction en appliquant les règles Drools.
     * Cette méthode est marquée avec l'annotation @DroolsRule pour indiquer
     * qu'elle doit être interceptée par l'aspect Drools.
     * La méthode est transactionnelle et gère la persistance des modifications.
     *
     * @param transaction La transaction à traiter
     * @param account Le compte associé à la transaction
     * @return La transaction mise à jour avec le résultat du traitement
     * @throws TransactionFailedException Si la transaction est refusée ou invalide
     */
    @Transactional
    @DroolsRule
    public Transaction doTransaction(Transaction transaction, Account account) {
        logger.info("Exécution de la transaction: type={}, accountNumber={}, amount={}",
                transaction.getType(), transaction.getAccountNumber(), transaction.getAmount());

        // La logique Drools est appliquée via l'aspect
        logger.info("Après Drools - Transaction: approved={}, message={}",
                transaction.isApproved(), transaction.getMessage());

        // Valider l'état après Drools
        if (transaction.getMessage() == null || transaction.getMessage().isEmpty()) {
            logger.error("La transaction n'a pas de message après l'exécution des règles");
            throw new TransactionFailedException("Échec de la transaction : aucun message fourni par les règles");
        }

        // Vérifier si la transaction est approuvée
        if (!transaction.isApproved()) {
            logger.warn("Transaction non approuvée: {}", transaction.getMessage());
            transactionRepository.save(transaction);
            throw new TransactionFailedException(transaction.getMessage());
        }

        // Sauvegarder les modifications
        logger.info("Sauvegarde de Account: balance={}", account.getBalance());
        accountRepository.save(account);
        transactionRepository.save(transaction);

        return transaction;
    }
}