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
public class WithdrawalAspect {

    @Autowired
    private RuleBookRunner withdrawalRuleBook;

    @Before("execution(* fr.eletutour.service.AccountService.withdraw(..)) && args(accountNumber, amount)")
    public void validateWithdrawal(String accountNumber, BigDecimal amount) {
        // Récupérer le solde du compte
        BigDecimal balance = getBalance(accountNumber); // Suppose une méthode pour récupérer le solde

        NameValueReferableMap<Object> facts = new FactMap<>();
        facts.setValue("balance", balance);
        facts.setValue("amount", amount);

        withdrawalRuleBook.run(facts);

        if (withdrawalRuleBook.getResult().isPresent()) {
            throw new IllegalArgumentException(withdrawalRuleBook.getResult().get().toString());
        }
    }

    @Autowired
    private AccountRepository accountRepository;

    private BigDecimal getBalance(String accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance();
    }
}