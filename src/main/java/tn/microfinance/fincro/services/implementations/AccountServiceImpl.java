package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.repositories.AccountRepository;
import tn.microfinance.fincro.services.interfaces.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> retrieveAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    @Override
    public Account addAccount(Account t) {
        return accountRepository.save(t);
    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account updateAccount(Account t) {
        return accountRepository.save(t);
    }

    @Override
    public Account retrieveAccount(Integer id) {
        return accountRepository.findById(id).get();
    }
}
