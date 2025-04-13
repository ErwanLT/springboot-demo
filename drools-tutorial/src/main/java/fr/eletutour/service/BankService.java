package fr.eletutour.service;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.repository.AccountRepository;
import fr.eletutour.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionProcessor transactionProcessor;

    public BankService(AccountRepository accountRepository, TransactionRepository transactionRepository,
                       TransactionProcessor transactionProcessor) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionProcessor = transactionProcessor;
    }

    @Transactional
    public Transaction getAccountAndDoTransaction(Transaction transaction) {
        logger.info("Récupération du compte pour la transaction: {}", transaction.getAccountNumber());
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> {
                    logger.error("Compte non trouvé : {}", transaction.getAccountNumber());
                    return new IllegalArgumentException("Compte non trouvé : " + transaction.getAccountNumber());
                });
        return transactionProcessor.doTransaction(transaction, account);
    }

    @Transactional
    public Account createAccount(Account account) {
        logger.info("Création du compte: {}", account.getAccountNumber());
        return accountRepository.save(account);
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> {
                    logger.error("Compte non trouvé : {}", accountNumber);
                    return new IllegalArgumentException("Compte non trouvé : " + accountNumber);
                });
    }
}