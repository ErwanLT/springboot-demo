package fr.eletutour.service;

import fr.eletutour.model.Account;
import fr.eletutour.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    public Account withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }
}