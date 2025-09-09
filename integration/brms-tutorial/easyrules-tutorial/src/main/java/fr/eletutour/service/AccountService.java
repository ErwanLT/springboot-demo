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
package fr.eletutour.service;

import fr.eletutour.annotations.TransactionRule;
import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.model.TransactionType;
import fr.eletutour.repository.AccountRepository;
import fr.eletutour.repository.TransactionRepository;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @TransactionRule
    public Transaction processTransaction(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (transaction.getType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        } else if (transaction.getType() == TransactionType.WITHDRAWAL) {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        }

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }
}