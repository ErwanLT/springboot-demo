package fr.eletutour.rules;

import fr.eletutour.exception.TransactionException;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.model.TransactionType;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.math.BigDecimal;

/**
 * Règle métier vérifiant la suffisance du solde d'un compte pour une transaction.
 * Cette règle s'applique uniquement aux retraits et vérifie que le compte dispose
 * d'un solde suffisant pour effectuer la transaction.
 */
@Rule(name = "insufficient balance rule", description = "Check if account has sufficient balance")
public class InsufficientBalanceRule {
    
    /**
     * Vérifie si les conditions de la règle sont remplies.
     * La règle s'applique si :
     * - La transaction est un retrait
     * - Le montant de la transaction est null ou négatif
     * - Le solde du compte est null
     * - Le solde du compte est insuffisant pour la transaction
     *
     * @param account Le compte concerné par la transaction
     * @param transaction La transaction à vérifier
     * @return true si les conditions de la règle sont remplies, false sinon
     */
    @Condition
    public boolean when(@Fact("account") Account account, @Fact("transaction") Transaction transaction) {
        return transaction.getType() == TransactionType.WITHDRAWAL
                && (transaction.getAmount() == null
                || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0
                || account.getBalance() == null
                || account.getBalance().compareTo(transaction.getAmount()) < 0);
    }
    
    /**
     * Action à exécuter lorsque les conditions de la règle sont remplies.
     * Lance une exception indiquant que le solde est insuffisant.
     *
     * @throws TransactionException Si le solde est insuffisant
     */
    @Action
    public void then() throws Exception {
        throw new TransactionException("Insufficient balance");
    }
}