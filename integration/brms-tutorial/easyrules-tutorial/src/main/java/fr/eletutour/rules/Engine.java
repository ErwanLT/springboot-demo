/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of easyrules-tutorial.
 *
 * easyrules-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * easyrules-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with easyrules-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.rules;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Component;

/**
 * Moteur de règles pour l'application des règles métier.
 * Cette classe encapsule le moteur de règles Easy Rules et gère :
 * - L'enregistrement des règles métier
 * - L'exécution des règles sur les faits
 * - La gestion des résultats via un listener
 */
@Component
public class Engine {
    private final Rules rules;
    private final DefaultRulesEngine rulesEngine;
    private final TransactionRulesListener rulesListener;
    
    /**
     * Constructeur du moteur de règles.
     * Initialise le moteur avec :
     * - Les règles métier (InsufficientBalanceRule)
     * - Le listener pour gérer les résultats
     */
    public Engine() {
        rules = new Rules();
        rules.register(new InsufficientBalanceRule());
        rulesEngine = new DefaultRulesEngine();
        rulesListener = new TransactionRulesListener();
        rulesEngine.registerRuleListener(rulesListener);
    }
    
    /**
     * Exécute les règles métier sur un compte et une transaction.
     * Cette méthode :
     * - Crée les faits à partir du compte et de la transaction
     * - Déclenche l'exécution des règles
     * - Vérifie les résultats via le listener
     *
     * @param account Le compte bancaire concerné
     * @param transaction La transaction à valider
     * @throws RuntimeException Si les règles métier ne sont pas respectées
     */
    public void executeRules(Account account, Transaction transaction) {
        Facts facts = new Facts();
        facts.put("account", account);
        facts.put("transaction", transaction);
        rulesEngine.fire(rules, facts);
        rulesListener.throwIfFailed();
    }
}