package fr.eletutour.controller;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PostMapping("/transaction")
    public Transaction processTransaction(@RequestBody Transaction transaction) {
        return accountService.processTransaction(transaction);
    }
}