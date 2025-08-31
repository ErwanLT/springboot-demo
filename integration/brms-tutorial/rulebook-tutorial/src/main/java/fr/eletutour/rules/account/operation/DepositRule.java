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

/**
 * Règle de validation pour les opérations de dépôt.
 * Cette règle vérifie que :
 * - La transaction est bien de type DÉPÔT
 * - Le montant du dépôt est positif
 */
@RuleBean
@Rule(order = 1)
public class DepositRule {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(DepositRule.class);

    /**
     * La transaction à valider, fournie par le moteur de règles.
     */
    @Given("transaction")
    private Transaction transaction;

    /**
     * La liste des erreurs à laquelle ajouter les messages d'erreur.
     */
    @Given("errors")
    private List<String> errors;

    /**
     * Condition de déclenchement de la règle.
     * La règle se déclenche si la transaction est un dépôt et que le montant est invalide.
     *
     * @return true si la règle doit être appliquée (conditions invalides), false sinon
     */
    @When
    public boolean when() {
        logger.info("Validating deposit transaction: {}", transaction);
        return transaction.getType() == TransactionType.DEPOSIT
                && (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0);
    }

    /**
     * Action à exécuter lorsque la règle est déclenchée.
     * Ajoute un message d'erreur à la liste des erreurs.
     */
    @Then
    public void then() {
        logger.error("Deposit failed: Amount must be positive");
        errors.add("Deposit failed: Amount must be positive");
    }
}