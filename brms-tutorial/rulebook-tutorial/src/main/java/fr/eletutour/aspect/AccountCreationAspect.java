package fr.eletutour.aspect;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import fr.eletutour.model.Account;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Aspect pour la validation de la création de comptes bancaires.
 * Cet aspect intercepte l'exécution de la méthode createAccount du service
 * et applique les règles métier définies dans le RuleBook de création de compte.
 * Il vérifie notamment :
 * - La validité du numéro de compte
 * - La validité du solde initial
 */
@Aspect
@Component
public class AccountCreationAspect {

    /**
     * Le RuleBook runner pour les règles de création de compte.
     */
    @Autowired
    private RuleBookRunner accountCreationRuleBook;

    /**
     * Point de coupe exécuté avant la création d'un compte.
     * Cette méthode :
     * - Prépare les faits pour le RuleBook (compte à créer)
     * - Exécute les règles de validation
     * - Lance une exception si des erreurs sont détectées
     *
     * @param account Le compte à valider
     * @throws IllegalArgumentException Si le compte ne respecte pas les règles métier
     */
    @Before("execution(* fr.eletutour.service.AccountService.createAccount(..)) && args(account)")
    public void validateAccountCreation(Account account) {
        NameValueReferableMap<Object> facts = new FactMap<>();
        facts.setValue("account", account);

        accountCreationRuleBook.run(facts);

        if (accountCreationRuleBook.getResult().isPresent()) {
            throw new IllegalArgumentException(accountCreationRuleBook.getResult().get().toString());
        }
    }
}