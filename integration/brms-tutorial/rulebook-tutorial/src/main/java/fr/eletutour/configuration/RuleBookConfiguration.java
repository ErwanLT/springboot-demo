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