package fr.eletutour.rules.account.creation;

import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;
import com.deliveredtechnologies.rulebook.spring.RuleBean;
import fr.eletutour.model.Account;
import org.slf4j.Logger;

import java.math.BigDecimal;

/**
 * Règle de validation pour la création d'un compte bancaire.
 * Cette règle vérifie que :
 * - Le numéro de compte n'est pas null ou vide
 * - Le solde initial n'est pas null et n'est pas négatif
 */
@RuleBean
@Rule(order = 1)
public class AccountCreationRule {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountCreationRule.class);

    /**
     * Le compte à valider, fourni par le moteur de règles.
     */
    @Given("account")
    private Account account;

    /**
     * Le résultat de la validation, qui sera utilisé pour construire le message d'erreur.
     */
    @Result
    private String result;

    /**
     * Condition de déclenchement de la règle.
     * La règle se déclenche si le numéro de compte est invalide ou si le solde initial est négatif.
     *
     * @return true si la règle doit être appliquée (conditions invalides), false sinon
     */
    @When
    public boolean when() {
        logger.info("Validating account creation for account: {}", account);
        return account.getAccountNumber() == null || account.getAccountNumber().isEmpty()
                || account.getBalance() == null || account.getBalance().compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Action à exécuter lorsque la règle est déclenchée.
     * Enregistre un message d'erreur indiquant que la création du compte a échoué.
     */
    @Then
    public void then() {
        logger.error("Account creation failed: Invalid account number or negative initial balance");
        result = "Account creation failed: Invalid account number or negative initial balance";
    }
}