package fr.eletutour.rules.account.operation;

import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import fr.eletutour.model.Transaction;
import fr.eletutour.model.TransactionType;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;

@RuleBean
@Rule(order = 1)
public class DepositRule {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(DepositRule.class);

    @Given("transaction")
    private Transaction transaction;

    @Given("errors")
    private List<String> errors;

    @When
    public boolean when() {
        logger.info("Validating deposit transaction: {}", transaction);
        return transaction.getType() == TransactionType.DEPOSIT
                && (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0);
    }

    @Then
    public void then() {
        logger.error("Deposit failed: Amount must be positive");
        errors.add("Deposit failed: Amount must be positive");
    }
}