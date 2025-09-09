/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of drools-tutorial.
 *
 * drools-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * drools-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with drools-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer les comptes et les transactions bancaires.
 * Ce contrôleur expose les endpoints pour :
 * - Créer des comptes bancaires
 * - Récupérer les informations d'un compte
 * - Traiter les transactions (dépôts et retraits)
 */
@RestController
@RequestMapping("/api/bank")
public class BankController {
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    private final BankService bankService;

    /**
     * Constructeur du contrôleur bancaire.
     *
     * @param bankService Le service bancaire injecté pour gérer la logique métier
     */
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Crée un nouveau compte bancaire.
     *
     * @param account Les informations du compte à créer
     * @return Le compte créé avec son identifiant
     */
    @Operation(summary = "Créer un nouveau compte", description = "Crée un compte bancaire avec les informations fournies.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compte créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        logger.info("Requête pour créer le compte: {}", account.getAccountNumber());
        return ResponseEntity.ok(bankService.createAccount(account));
    }

    /**
     * Récupère les informations d'un compte bancaire.
     *
     * @param accountNumber Le numéro du compte à récupérer
     * @return Les informations du compte
     */
    @Operation(summary = "Récupérer un compte", description = "Récupère les détails d'un compte par son numéro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compte trouvé"),
            @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        logger.info("Requête pour récupérer le compte: {}", accountNumber);
        return ResponseEntity.ok(bankService.getAccount(accountNumber));
    }

    /**
     * Traite une transaction bancaire (dépôt ou retrait).
     *
     * @param transaction Les détails de la transaction à traiter
     * @return La transaction traitée avec son statut
     */
    @Operation(summary = "Traiter une transaction", description = "Traite une transaction (dépôt ou retrait) pour un compte.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction traitée avec succès"),
            @ApiResponse(responseCode = "400", description = "Transaction invalide ou non approuvée"),
            @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> processTransaction(@Valid @RequestBody Transaction transaction) {
        logger.info("Requête pour traiter la transaction: type={}, accountNumber={}, amount={}",
                transaction.getType(), transaction.getAccountNumber(), transaction.getAmount());
        Transaction result = bankService.getAccountAndDoTransaction(transaction);
        return ResponseEntity.ok(result);
    }
}