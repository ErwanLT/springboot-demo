package fr.eletutour.rules.account.operation.deposit;

import com.deliveredtechnologies.rulebook.annotation.*;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import java.math.BigDecimal;

@RuleBean
@Rule(order = 1)
public class DepositRule {

    @Given("amount")
    private BigDecimal amount;

    @Result
    private String result;

    @When
    public boolean when() {
        return amount == null || amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    @Then
    public void then() {
        result = "Deposit failed: Amount must be positive";
    }
}