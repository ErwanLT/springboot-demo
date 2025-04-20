package fr.eletutour.rules;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Component;

@Component
public class Engine {
    private final Rules rules;
    private final DefaultRulesEngine rulesEngine;
    private final TransactionRulesListener rulesListener;
    
    public Engine() {
        rules = new Rules();
        rules.register(new InsufficientBalanceRule());
        rulesEngine = new DefaultRulesEngine();
        rulesListener = new TransactionRulesListener();
        rulesEngine.registerRuleListener(rulesListener);
    }
    
    public void executeRules(Account account, Transaction transaction) {
        Facts facts = new Facts();
        facts.put("account", account);
        facts.put("transaction", transaction);
        rulesEngine.fire(rules, facts);
        rulesListener.throwIfFailed();
    }
}