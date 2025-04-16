package fr.eletutour.aspect;

import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.FactMap;
import fr.eletutour.model.Account;
import fr.eletutour.repository.AccountRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Aspect
@Component
public class DepositAspect {

    @Autowired
    private RuleBookRunner depositRuleBook;

    @Autowired
    private AccountRepository accountRepository;

    @Before("execution(* fr.eletutour.service.AccountService.deposit(..)) && args(accountNumber, amount)")
    public void validateDeposit(String accountNumber, BigDecimal amount) {
        // Vérifier que le compte existe
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        NameValueReferableMap<Object> facts = new FactMap<>();
        facts.setValue("amount", amount);

        depositRuleBook.run(facts);

        if (depositRuleBook.getResult().isPresent()) {
            throw new IllegalArgumentException(depositRuleBook.getResult().get().toString());
        }
    }
}