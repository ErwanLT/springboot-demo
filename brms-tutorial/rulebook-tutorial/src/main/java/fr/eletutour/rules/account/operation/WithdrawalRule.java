package fr.eletutour.rules.account.operation;

import com.deliveredtechnologies.rulebook.annotation.*;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import fr.eletutour.model.Transaction;
import fr.eletutour.model.TransactionType;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;

@RuleBean
@Rule(order = 2)
public class WithdrawalRule {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(WithdrawalRule.class);

    @Given("transaction")
    private Transaction transaction;

    @Given("balance")
    private BigDecimal balance;

    @Given("errors")
    private List<String> errors;

    @When
    public boolean when() {
        logger.info("Validating withdrawal transaction: {}", transaction);
        return transaction.getType() == TransactionType.WITHDRAWAL
                && (transaction.getAmount() == null
                || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0
                || balance == null
                || balance.compareTo(transaction.getAmount()) < 0);
    }

    @Then
    public void then() {
        logger.error("Withdrawal failed: Insufficient balance or invalid amount");
        errors.add("Withdrawal failed: Insufficient balance or invalid amount");
    }
}