package fr.eletutour.rules.account.creation;

import com.deliveredtechnologies.rulebook.annotation.*;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import fr.eletutour.model.Account;

import java.math.BigDecimal;

@RuleBean
@Rule(order = 1)
public class AccountCreationRule {

    @Given("account")
    private Account account;

    @Result
    private String result;

    @When
    public boolean when() {
        return account.getAccountNumber() == null || account.getAccountNumber().isEmpty()
                || account.getBalance() == null || account.getBalance().compareTo(BigDecimal.ZERO) < 0;
    }

    @Then
    public void then() {
        result = "Account creation failed: Invalid account number or negative initial balance";
    }
}