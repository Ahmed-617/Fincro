package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> retrieveAllTransactions();
    Transaction addTransaction(Transaction t);
    void deleteTransaction(Integer id);
    Transaction retrieveTransaction(Integer id);
}
