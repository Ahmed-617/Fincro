package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.Transaction;

import java.util.List;

public interface AccountService {
    List<Account> retrieveAllAccounts();
    Account addAccount(Account t);
    void deleteAccount(Integer id);
    Account updateAccount(Account t);
    Account retrieveAccount(Integer id);

    public static void addAmount(float amount, Account b) {

    }
}
