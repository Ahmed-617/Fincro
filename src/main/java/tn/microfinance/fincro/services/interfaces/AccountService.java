package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.Transaction;

import java.util.List;

public interface AccountService {
    List<Account> retrieveAllAccounts();
    Account addAccount(Account t);
    void deleteAccount(Long id);
    Account updateAccount(Account t);
    Account retrieveAccount(Long id);
    double getBalance(long accountId);
    void setAleaCode(long accountId, String code);
    Account getAccountByUserId(long userId);
}
