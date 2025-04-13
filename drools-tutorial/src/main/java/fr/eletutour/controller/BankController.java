package fr.eletutour.controller;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankController {
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        logger.info("Requête pour créer le compte: {}", account.getAccountNumber());
        return ResponseEntity.ok(bankService.createAccount(account));
    }

    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        logger.info("Requête pour récupérer le compte: {}", accountNumber);
        return ResponseEntity.ok(bankService.getAccount(accountNumber));
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> processTransaction(@RequestBody Transaction transaction) {
        logger.info("Requête pour traiter la transaction: type={}, accountNumber={}, amount={}",
                transaction.getType(), transaction.getAccountNumber(), transaction.getAmount());
        try {
            Transaction result = bankService.getAccountAndDoTransaction(transaction);
            return ResponseEntity.ok(result);
        } catch (IllegalStateException e) {
            logger.warn("Échec de la transaction: {}", e.getMessage());
            Transaction failed = new Transaction();
            failed.setApproved(false);
            failed.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failed);
        } catch (IllegalArgumentException e) {
            logger.warn("Erreur de validation: {}", e.getMessage());
            Transaction failed = new Transaction();
            failed.setApproved(false);
            failed.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failed);
        }
    }
}