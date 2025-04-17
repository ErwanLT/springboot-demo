package fr.eletutour.rules.account.operation;

import com.deliveredtechnologies.rulebook.annotation.*;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import fr.eletutour.model.Transaction;
import fr.eletutour.model.TransactionType;

import java.math.BigDecimal;

@RuleBean
@Rule(order = 2)
public class WithdrawalRule {

    @Given("transaction")
    private Transaction transaction;

    @Given("balance")
    private BigDecimal balance;

    @Result
    private String result;

    @When
    public boolean when() {
        return transaction.getType() == TransactionType.WITHDRAWAL
                && (transaction.getAmount() == null
                || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0
                || balance == null
                || balance.compareTo(transaction.getAmount()) < 0);
    }

    @Then
    public void then() {
        result = "Withdrawal failed: Insufficient balance or invalid amount";
    }
}