package fr.eletutour.aspect;


import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.repository.AccountRepository;
import fr.eletutour.rules.Engine;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
public class RulesAspect {
    
    private final Engine rulesEngine;
    private final AccountRepository accountRepository;
    
    public RulesAspect(Engine rulesEngine, AccountRepository accountRepository) {
        this.rulesEngine = rulesEngine;
        this.accountRepository = accountRepository;
    }

    @Before("@annotation(fr.eletutour.annotations.TransactionRule) && args(transaction)")
    public void applyRules(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        rulesEngine.executeRules(account, transaction);
    }
}