package com.example.lab9.controller;

import com.example.lab9.model.Account;
import com.example.lab9.model.DepositTransaction;
import com.example.lab9.repository.DepositRepository;
import com.example.lab9.service.AccountService;
import com.example.lab9.service.DepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final DepositService depositService;
    private final DepositRepository depositRepository;

    public AccountController(AccountService accountService, DepositService depositService, DepositRepository depositRepository) {
        this.accountService = accountService;
        this.depositService = depositService;
        this.depositRepository = depositRepository;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body("Invalid amount");
        }

        depositService.deposit(id, amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @GetMapping("/{id}/deposits")
    public ResponseEntity<List<DepositTransaction>> getDepositHistory(@PathVariable Long id) {
        List<DepositTransaction> history = depositRepository.findByAccount_Id(id);
        return ResponseEntity.ok(history);
    }
}