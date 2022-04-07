package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.repositories.AccountRepository;
import tn.microfinance.fincro.services.interfaces.AccountService;
import tn.microfinance.fincro.services.interfaces.TransactionService;


import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionService transactionService;


    @Override
    public List<Account> retrieveAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    @Override
    public Account addAccount(Account t) {
        t=accountRepository.save(t);
        transactionService.sendEmail(t.getUser().getEmail(),"Welcome to FINCRO","Your account has been created successfully !! \nYou can start benefiting from our services immediately ! Just go to our website and connect to your personal space. \nWelcome to fincro family");
        return t;
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account updateAccount(Account t) {
        return accountRepository.save(t);
    }

    @Override
    public Account retrieveAccount(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public double getBalance(long accountId) {
        return accountRepository.getBalanceById(accountId);
    }

    @Override
    public void setAleaCode(long accountId, String code) {
        Account account = accountRepository.findById(accountId).get();
        account.setAleaCode(code);
        accountRepository.save(account);
        System.out.println("Code : "+account.getAleaCode());
    }
}
