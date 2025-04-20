package fr.eletutour.rules;

import fr.eletutour.exception.TransactionException;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.model.TransactionType;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.math.BigDecimal;

@Rule(name = "insufficient balance rule", description = "Check if account has sufficient balance")
public class InsufficientBalanceRule {
    
    @Condition
    public boolean when(@Fact("account") Account account, @Fact("transaction") Transaction transaction) {
        return transaction.getType() == TransactionType.WITHDRAWAL
                && (transaction.getAmount() == null
                || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0
                || account.getBalance() == null
                || account.getBalance().compareTo(transaction.getAmount()) < 0);
    }
    
    @Action
    public void then() throws Exception {
        throw new TransactionException("Insufficient balance");
    }
}