package com.example.lab9.service;

import com.example.lab9.model.Account;
import com.example.lab9.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // สร้าง Account ใหม่
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // ค้นหา Account จาก id
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
}