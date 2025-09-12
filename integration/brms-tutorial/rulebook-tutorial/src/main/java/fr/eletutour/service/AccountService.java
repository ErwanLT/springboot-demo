package fr.eletutour.service;

import fr.eletutour.annotations.TransactionRule;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.model.TransactionType;
import fr.eletutour.repository.AccountRepository;
import fr.eletutour.repository.TransactionRepository;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des comptes bancaires et des transactions.
 * Ce service gère :
 * - La création de nouveaux comptes
 * - Le traitement des transactions (dépôts et retraits)
 * Les opérations sont soumises aux règles métier définies dans les RuleBooks.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Constructeur du service de gestion des comptes.
     *
     * @param accountRepository Le repository pour la persistance des comptes
     * @param transactionRepository Le repository pour la persistance des transactions
     */
    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Crée un nouveau compte bancaire.
     * Cette méthode est interceptée par AccountCreationAspect pour valider
     * les règles de création de compte avant la persistance.
     *
     * @param account Le compte à créer
     * @return Le compte créé avec son identifiant
     */
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Traite une transaction bancaire (dépôt ou retrait).
     * Cette méthode est marquée avec @TransactionRule et est donc interceptée
     * par TransactionAspect pour valider les règles de transaction.
     * Elle met à jour le solde du compte en fonction du type de transaction.
     *
     * @param transaction La transaction à traiter
     * @return La transaction traitée avec son statut
     * @throws IllegalArgumentException Si le compte associé à la transaction n'existe pas
     */
    @TransactionRule
    public Transaction processTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (transaction.getType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        } else if (transaction.getType() == TransactionType.WITHDRAWAL) {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        }

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }
}