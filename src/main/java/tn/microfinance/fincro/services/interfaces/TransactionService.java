package tn.microfinance.fincro.services.interfaces;

import org.springframework.scheduling.annotation.Async;
import tn.microfinance.fincro.dao.model.SmsRequest;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.model.TransactionType;

import java.util.Calendar;
import java.util.List;

public interface TransactionService {
    List<Transaction> retrieveAllTransactions();
    Transaction addTransaction(Transaction t);
    void transferMoney(Transaction T) throws IllegalAccessException, InterruptedException;
    void deleteTransaction(Long id);
    void deleteAllTransactions();
    Transaction retrieveTransaction(Long id);
    Calendar scheduleTransactions(Transaction transaction, int rep);
    List<Transaction> getTransactionByType(TransactionType transactionType);
    void makeScheduledTransaction(Transaction transaction);
    void executeScheduledTransactions();
    public List<Transaction> findAccountTransByType(TransactionType transactionType, long accountId);
    public int getTransNbreByAccountAndType(TransactionType transactionType, long accountId);
    public void sendSms(SmsRequest smsRequest);
    public void sendEmail(String to, String subject, String body);

    //public void verifTransferMoney(Transaction t);
}
