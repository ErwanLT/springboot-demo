package fr.eletutour.rules.account.operation;

import com.deliveredtechnologies.rulebook.annotation.*;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import fr.eletutour.model.Transaction;
import fr.eletutour.model.TransactionType;

import java.math.BigDecimal;

@RuleBean
@Rule(order = 1)
public class DepositRule {

    @Given("transaction")
    private Transaction transaction;

    @Result
    private String result;

    @When
    public boolean when() {
        return transaction.getType() == TransactionType.DEPOSIT
                && (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0);
    }

    @Then
    public void then() {
        result = "Deposit failed: Amount must be positive";
    }
}