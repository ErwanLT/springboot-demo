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
package fr.eletutour.controller;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer les comptes bancaires et leurs transactions.
 * Ce contrôleur expose les endpoints pour :
 * - Créer de nouveaux comptes bancaires
 * - Traiter les transactions sur les comptes existants
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    /**
     * Constructeur du contrôleur de comptes.
     *
     * @param accountService Le service de gestion des comptes injecté
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Crée un nouveau compte bancaire.
     * L'opération est soumise aux règles métier définies dans le RuleBook.
     *
     * @param account Les informations du compte à créer
     * @return Le compte créé avec son identifiant
     */
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    /**
     * Traite une transaction bancaire.
     * L'opération est soumise aux règles métier définies dans le RuleBook.
     *
     * @param transaction Les détails de la transaction à traiter
     * @return La transaction traitée avec son statut
     */
    @PostMapping("/transaction")
    public Transaction processTransaction(@RequestBody Transaction transaction) {
        return accountService.processTransaction(transaction);
    }
}