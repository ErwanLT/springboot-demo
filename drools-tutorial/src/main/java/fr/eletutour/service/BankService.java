package fr.eletutour.service;

import fr.eletutour.exception.AccountNotFoundException;
import fr.eletutour.exception.TransactionFailedException;
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
 */
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

    /**
     * Traite une transaction en récupérant le compte associé.
     *
     * @param transaction La transaction à traiter.
     * @return La transaction mise à jour.
     * @throws AccountNotFoundException Si le compte n'existe pas.
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
     * Crée un nouveau compte.
     *
     * @param account Le compte à créer.
     * @return Le compte créé.
     */
    @Transactional
    public Account createAccount(Account account) {
        logger.info("Création du compte: {}", account.getAccountNumber());
        return accountRepository.save(account);
    }

    /**
     * Récupère un compte par son numéro.
     *
     * @param accountNumber Le numéro du compte.
     * @return Le compte trouvé.
     * @throws AccountNotFoundException Si le compte n'existe pas.
     */
    public Account getAccount(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> {
                    logger.error("Compte non trouvé : {}", accountNumber);
                    return new AccountNotFoundException("Compte non trouvé : " + accountNumber);
                });
    }
}