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
package fr.eletutour.controller;

import fr.eletutour.model.Account;
import fr.eletutour.model.Transaction;
import fr.eletutour.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PostMapping("/transaction")
    public Transaction processTransaction(@RequestBody Transaction transaction) {
        return accountService.processTransaction(transaction);
    }
}