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

import fr.eletutour.exception.AccountNotFoundException;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.repository.AccountRepository;
import fr.eletutour.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour gérer les comptes et les transactions bancaires.
 * Cette classe fournit les opérations métier principales pour :
 * - La création et la récupération de comptes
 * - Le traitement des transactions bancaires
 * - L'intégration avec le moteur de règles Drools via TransactionProcessor
 */
@Service
public class BankService {
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionProcessor transactionProcessor;

    /**
     * Constructeur du service bancaire.
     *
     * @param accountRepository Le repository pour les comptes
     * @param transactionRepository Le repository pour les transactions
     * @param transactionProcessor Le processeur de transactions
     */
    public BankService(AccountRepository accountRepository, TransactionRepository transactionRepository,
                       TransactionProcessor transactionProcessor) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionProcessor = transactionProcessor;
    }

    /**
     * Traite une transaction en récupérant le compte associé.
     * Cette méthode est transactionnelle et utilise le processeur de transactions
     * pour appliquer les règles métier via Drools.
     *
     * @param transaction La transaction à traiter
     * @return La transaction mise à jour avec le résultat du traitement
     * @throws AccountNotFoundException Si le compte n'existe pas
     */
    @Transactional
    public Transaction getAccountAndDoTransaction(Transaction transaction) {
        logger.info("Récupération du compte pour la transaction: {}", transaction.getAccountNumber());
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> {
                    logger.error("Compte non trouvé : {}", transaction.getAccountNumber());
                    return new AccountNotFoundException("Compte non trouvé : " + transaction.getAccountNumber());
                });
        return transactionProcessor.doTransaction(transaction, account);
    }

    /**
     * Crée un nouveau compte bancaire.
     * Cette méthode est transactionnelle et persiste le compte dans la base de données.
     *
     * @param account Le compte à créer
     * @return Le compte créé avec son identifiant
     */
    @Transactional
    public Account createAccount(Account account) {
        logger.info("Création du compte: {}", account.getAccountNumber());
        return accountRepository.save(account);
    }

    /**
     * Récupère un compte bancaire par son numéro.
     *
     * @param accountNumber Le numéro du compte à récupérer
     * @return Le compte trouvé
     * @throws AccountNotFoundException Si le compte n'existe pas
     */
    public Account getAccount(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> {
                    logger.error("Compte non trouvé : {}", accountNumber);
                    return new AccountNotFoundException("Compte non trouvé : " + accountNumber);
                });
    }
}