package fr.eletutour.aspect;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.repository.AccountRepository;
import fr.eletutour.rules.Engine;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Aspect pour l'application des règles métier sur les transactions.
 * Cet aspect intercepte l'exécution des méthodes marquées avec @TransactionRule
 * et applique les règles métier définies dans le moteur de règles.
 * Il vérifie notamment :
 * - L'existence du compte
 * - La validité des transactions
 * - L'application des règles métier
 */
@Aspect
@Component
public class RulesAspect {
    
    private final Engine rulesEngine;
    private final AccountRepository accountRepository;
    
    /**
     * Constructeur de l'aspect de règles.
     *
     * @param rulesEngine Le moteur de règles à utiliser
     * @param accountRepository Le repository pour accéder aux comptes
     */
    public RulesAspect(Engine rulesEngine, AccountRepository accountRepository) {
        this.rulesEngine = rulesEngine;
        this.accountRepository = accountRepository;
    }

    /**
     * Point de coupe exécuté avant les méthodes marquées avec @TransactionRule.
     * Cette méthode :
     * - Récupère le compte associé à la transaction
     * - Applique les règles métier via le moteur de règles
     *
     * @param transaction La transaction à valider
     * @throws IllegalArgumentException Si le compte n'existe pas
     */
    @Before("@annotation(fr.eletutour.annotations.TransactionRule) && args(transaction)")
    public void applyRules(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        rulesEngine.executeRules(account, transaction);
    }
}