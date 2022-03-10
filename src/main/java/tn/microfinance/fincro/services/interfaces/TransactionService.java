package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.model.TransactionType;

import java.util.List;

public interface TransactionService {
    List<Transaction> retrieveAllTransactions();
    Transaction addTransaction(Transaction t);
    void deleteTransaction(Long id);
    void deleteAllTransactions();
    Transaction retrieveTransaction(Long id);
    void scheduleTransactions(Transaction transaction, int rep);
    List<Transaction> getTransactionByType(TransactionType transactionType);
    void makeScheduledTransaction(long id);
    void executeScheduledTransactions();
}
