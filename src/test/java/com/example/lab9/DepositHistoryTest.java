package com.example.lab9;

import com.example.lab9.model.Account;
import com.example.lab9.model.DepositTransaction;
import com.example.lab9.repository.AccountRepository;
import com.example.lab9.repository.DepositRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DepositHistoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Test
    void shouldReturnDepositHistoryByAccountId() {
        Account account = accountRepository.save(new Account("ACC-1001", "Alice", 0.0));

        depositRepository.save(new DepositTransaction(100.0, account));
        depositRepository.save(new DepositTransaction(50.0, account));

        List<DepositTransaction> history = depositRepository.findByAccount_Id(account.getId());

        assertThat(history).hasSize(2);
        assertThat(history)
                .extracting(DepositTransaction::getAmount)
                .containsExactlyInAnyOrder(100.0, 50.0);
    }
}
