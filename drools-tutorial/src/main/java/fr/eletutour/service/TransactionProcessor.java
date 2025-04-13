package fr.eletutour.service;

import fr.eletutour.annotation.DroolsRule;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.repository.AccountRepository;
import fr.eletutour.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessor.class);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionProcessor(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @DroolsRule
    public Transaction doTransaction(Transaction transaction, Account account) {
        logger.info("Exécution de la transaction: type={}, accountNumber={}, amount={}",
                transaction.getType(), transaction.getAccountNumber(), transaction.getAmount());

        // La logique Drools est appliquée via l'aspect
        logger.info("Après Drools - Transaction: approved={}, message={}",
                transaction.isApproved(), transaction.getMessage());

        // Vérifier si la transaction est approuvée
        if (!transaction.isApproved()) {
            logger.warn("Transaction non approuvée: {}", transaction.getMessage());
            transactionRepository.save(transaction);
            throw new IllegalStateException(transaction.getMessage());
        }

        // Sauvegarder les modifications
        logger.info("Sauvegarde de Account: balance={}", account.getBalance());
        accountRepository.save(account);
        transactionRepository.save(transaction);

        return transaction;
    }
}