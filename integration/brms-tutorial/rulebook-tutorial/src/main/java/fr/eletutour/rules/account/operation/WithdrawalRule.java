/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of rulebook-tutorial.
 *
 * rulebook-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * rulebook-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with rulebook-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
 * Règle de validation pour les opérations de retrait.
 * Cette règle vérifie que :
 * - La transaction est bien de type RETRAIT
 * - Le montant du retrait est positif
 * - Le solde du compte est suffisant pour effectuer le retrait
 */
@RuleBean
@Rule(order = 2)
public class WithdrawalRule {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(WithdrawalRule.class);

    /**
     * La transaction à valider, fournie par le moteur de règles.
     */
    @Given("transaction")
    private Transaction transaction;

    /**
     * Le solde actuel du compte, fourni par le moteur de règles.
     */
    @Given("balance")
    private BigDecimal balance;

    /**
     * La liste des erreurs à laquelle ajouter les messages d'erreur.
     */
    @Given("errors")
    private List<String> errors;

    /**
     * Condition de déclenchement de la règle.
     * La règle se déclenche si :
     * - La transaction est un retrait
     * - Le montant est invalide (null ou négatif)
     * - Le solde est insuffisant
     *
     * @return true si la règle doit être appliquée (conditions invalides), false sinon
     */
    @When
    public boolean when() {
        logger.info("Validating withdrawal transaction: {}", transaction);
        return transaction.getType() == TransactionType.WITHDRAWAL
                && (transaction.getAmount() == null
                || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0
                || balance == null
                || balance.compareTo(transaction.getAmount()) < 0);
    }

    /**
     * Action à exécuter lorsque la règle est déclenchée.
     * Ajoute un message d'erreur à la liste des erreurs.
     */
    @Then
    public void then() {
        logger.error("Withdrawal failed: Insufficient balance or invalid amount");
        errors.add("Withdrawal failed: Insufficient balance or invalid amount");
    }
}