package fr.eletutour.aspect;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import fr.eletutour.model.Account;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AccountCreationAspect {

    @Autowired
    private RuleBookRunner accountCreationRuleBook;

    @Before("execution(* fr.eletutour.service.AccountService.createAccount(..)) && args(account)")
    public void validateAccountCreation(Account account) {
        NameValueReferableMap<Object> facts = new FactMap<>();
        facts.setValue("account", account);

        accountCreationRuleBook.run(facts);

        if (accountCreationRuleBook.getResult().isPresent()) {
            throw new IllegalArgumentException(accountCreationRuleBook.getResult().get().toString());
        }
    }
}