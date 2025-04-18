package fr.eletutour.rules.account.creation;

import com.deliveredtechnologies.rulebook.annotation.*;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import fr.eletutour.model.Account;
import org.slf4j.Logger;

import java.math.BigDecimal;

@RuleBean
@Rule(order = 1)
public class AccountCreationRule {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountCreationRule.class);

    @Given("account")
    private Account account;

    @Result
    private String result;

    @When
    public boolean when() {
        logger.info("Validating account creation for account: {}", account);
        return account.getAccountNumber() == null || account.getAccountNumber().isEmpty()
                || account.getBalance() == null || account.getBalance().compareTo(BigDecimal.ZERO) < 0;
    }

    @Then
    public void then() {
        logger.error("Account creation failed: Invalid account number or negative initial balance");
        result = "Account creation failed: Invalid account number or negative initial balance";
    }
}