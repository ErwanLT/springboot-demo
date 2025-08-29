package fr.eletutour.rules;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Component;

/**
 * Moteur de règles pour l'application des règles métier.
 * Cette classe encapsule le moteur de règles Easy Rules et gère :
 * - L'enregistrement des règles métier
 * - L'exécution des règles sur les faits
 * - La gestion des résultats via un listener
 */
@Component
public class Engine {
    private final Rules rules;
    private final DefaultRulesEngine rulesEngine;
    private final TransactionRulesListener rulesListener;
    
    /**
     * Constructeur du moteur de règles.
     * Initialise le moteur avec :
     * - Les règles métier (InsufficientBalanceRule)
     * - Le listener pour gérer les résultats
     */
    public Engine() {
        rules = new Rules();
        rules.register(new InsufficientBalanceRule());
        rulesEngine = new DefaultRulesEngine();
        rulesListener = new TransactionRulesListener();
        rulesEngine.registerRuleListener(rulesListener);
    }
    
    /**
     * Exécute les règles métier sur un compte et une transaction.
     * Cette méthode :
     * - Crée les faits à partir du compte et de la transaction
     * - Déclenche l'exécution des règles
     * - Vérifie les résultats via le listener
     *
     * @param account Le compte bancaire concerné
     * @param transaction La transaction à valider
     * @throws RuntimeException Si les règles métier ne sont pas respectées
     */
    public void executeRules(Account account, Transaction transaction) {
        Facts facts = new Facts();
        facts.put("account", account);
        facts.put("transaction", transaction);
        rulesEngine.fire(rules, facts);
        rulesListener.throwIfFailed();
    }
}