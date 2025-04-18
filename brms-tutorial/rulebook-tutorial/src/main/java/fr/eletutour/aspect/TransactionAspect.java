package fr.eletutour.aspect;

import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.FactMap;
import fr.eletutour.exception.TransactionException;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.repository.AccountRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class TransactionAspect {

    @Autowired
    private RuleBookRunner transactionRuleBook;

    @Autowired
    private AccountRepository accountRepository;

    @Before("@annotation(fr.eletutour.annotations.TransactionRule) && args(transaction)")
    public void validateTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        List<String> errors = new ArrayList<>();
        NameValueReferableMap<Object> facts = new FactMap<>();
        facts.setValue("transaction", transaction);
        facts.setValue("balance", account.getBalance());
        facts.setValue("errors", errors);

        transactionRuleBook.run(facts);

        if (!errors.isEmpty()) {
            throw new TransactionException(errors);
        }
    }
}