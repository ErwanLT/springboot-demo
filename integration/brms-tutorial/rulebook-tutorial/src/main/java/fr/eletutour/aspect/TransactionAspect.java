package fr.eletutour.aspect;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import fr.eletutour.exception.TransactionException;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.repository.AccountRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Aspect pour la validation des transactions bancaires.
 * Cet aspect intercepte l'exécution des méthodes marquées avec @TransactionRule
 * et applique les règles métier définies dans le RuleBook de transaction.
 * Il vérifie notamment :
 * - L'existence du compte
 * - La validité du montant
 * - La suffisance du solde pour les retraits
 */
@Aspect
@Component
public class TransactionAspect {

    /**
     * Le RuleBook runner pour les règles de transaction.
     */
    @Autowired
    private RuleBookRunner transactionRuleBook;

    /**
     * Le repository pour accéder aux comptes bancaires.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Point de coupe exécuté avant les méthodes marquées avec @TransactionRule.
     * Cette méthode :
     * - Récupère le compte associé à la transaction
     * - Prépare les faits pour le RuleBook (transaction, solde, liste d'erreurs)
     * - Exécute les règles de validation
     * - Lance une exception si des erreurs sont détectées
     *
     * @param transaction La transaction à valider
     * @throws IllegalArgumentException Si le compte n'existe pas
     * @throws TransactionException Si la transaction ne respecte pas les règles métier
     */
    @Before("@annotation(fr.eletutour.annotations.TransactionRule) && args(transaction)")
    public void validateTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        List<String> errors = new ArrayList<>();
        NameValueReferableMap<Object> facts = new FactMap<>();
        facts.setValue("transaction", transaction);
        facts.setValue("balance", account.getBalance());
        facts.setValue("errors", errors);

        transactionRuleBook.run(facts);

        if (!errors.isEmpty()) {
            throw new TransactionException(errors);
        }
    }
}