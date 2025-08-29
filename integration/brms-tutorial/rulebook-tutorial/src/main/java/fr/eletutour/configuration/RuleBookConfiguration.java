package fr.eletutour.configuration;

import com.deliveredtechnologies.rulebook.spring.SpringAwareRuleBookRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration du moteur RuleBook pour l'application.
 * Cette classe configure les différents RuleBook runners pour gérer
 * les règles métier de l'application, notamment pour la création
 * de comptes et les opérations sur les comptes.
 */
@Configuration
public class RuleBookConfiguration {

    /**
     * Configure le RuleBook pour la création de comptes.
     * Ce RuleBook gère les règles métier liées à la création
     * et à la validation des nouveaux comptes bancaires.
     *
     * @return Un SpringAwareRuleBookRunner configuré pour les règles de création de compte
     */
    @Bean
    public SpringAwareRuleBookRunner accountCreationRuleBook() {
        return new SpringAwareRuleBookRunner("fr.eletutour.rules.account.creation");
    }

    /**
     * Configure le RuleBook pour les opérations sur les comptes.
     * Ce RuleBook gère les règles métier liées aux transactions
     * et aux opérations sur les comptes existants.
     *
     * @return Un SpringAwareRuleBookRunner configuré pour les règles d'opération sur compte
     */
    @Bean
    public SpringAwareRuleBookRunner transactionRuleBook() {
        return new SpringAwareRuleBookRunner("fr.eletutour.rules.account.operation");
    }
}