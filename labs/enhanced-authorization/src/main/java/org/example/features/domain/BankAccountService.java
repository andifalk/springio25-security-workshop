package org.example.features.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    BankAccount findById(long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    @Transactional
    BankAccount save(BankAccount toSave) {
        return bankAccountRepository.save(toSave);
    }

    @Transactional
    boolean update(long id, BankAccount toUpdate) {
        return bankAccountRepository.updateBankAccount(id, toUpdate.getBalance()) == 1;
    }
}
