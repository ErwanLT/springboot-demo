package fr.eletutour.rules.account.operation.withdraw;

import com.deliveredtechnologies.rulebook.annotation.*;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import java.math.BigDecimal;

@RuleBean
@Rule(order = 1)
public class WithdrawalRule {

    @Given("balance")
    private BigDecimal balance;

    @Given("amount")
    private BigDecimal amount;

    @Result
    private String result;

    @When
    public boolean when() {
        return amount == null || amount.compareTo(BigDecimal.ZERO) <= 0
                || balance == null || balance.compareTo(amount) < 0;
    }

    @Then
    public void then() {
        result = "Withdrawal failed: Insufficient balance or invalid amount";
    }
}