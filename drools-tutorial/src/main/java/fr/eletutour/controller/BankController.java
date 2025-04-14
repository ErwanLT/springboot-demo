package fr.eletutour.controller;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.service.BankService;
import fr.eletutour.exception.AccountNotFoundException;
import fr.eletutour.exception.TransactionFailedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les comptes et les transactions bancaires.
 */
@RestController
@RequestMapping("/api/bank")
public class BankController {
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

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