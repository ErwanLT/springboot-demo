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