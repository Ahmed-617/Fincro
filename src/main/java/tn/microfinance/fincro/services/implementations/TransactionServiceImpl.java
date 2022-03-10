package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.model.TransactionType;
import tn.microfinance.fincro.dao.repositories.AccountRepository;
import tn.microfinance.fincro.dao.repositories.TransactionRepository;
import tn.microfinance.fincro.services.interfaces.TransactionService;

import javax.validation.constraints.Null;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Transaction> retrieveAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    @Override
    public Transaction addTransaction(Transaction t) {
        return transactionRepository.save(t);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }

    @Override
    public Transaction retrieveTransaction(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public void scheduleTransactions(Transaction transaction, int rep) {
        Date date = transaction.getTransactionDate() ;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (transaction.getTransactionScheme()){
            case Daily:
                for (int i=0;i<rep;i++){
                    System.out.println(c.getTime());
                    transaction.setTransactionDate(c.getTime());
                    System.out.println(transaction.getTransactionDate());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(c.getTime());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.DATE,1);

                }
                break;
            case Weekly:
                for (int i=0;i<rep;i++){
                    transaction.setTransactionDate(c.getTime());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(transaction.getTransactionDate());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.DATE,7);
                }
                break;
            case Monthly:
                for (int i=0;i<rep;i++){
                    transaction.setTransactionDate(c.getTime());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(transaction.getTransactionDate());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.MONTH,1);
                }
                break;
            case Annually:
                for (int i=0;i<rep;i++){
                    transaction.setTransactionDate(c.getTime());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(transaction.getTransactionDate());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.YEAR,1);
                }
                break;
            default:
                break;
            }

    }

    @Override
    public List<Transaction> getTransactionByType(TransactionType transactionType) {
        return transactionRepository.findTransactionByTransactionType(transactionType);
    }

    @Override
    public void makeScheduledTransaction(long id) {
        Transaction receiverTransaction = transactionRepository.findById(id).get();
        Transaction senderTransaction = new Transaction();
        Account senderAccount = accountRepository.findById(receiverTransaction.getSenderAccountId()).get();
        Account receiverAccount = accountRepository.findById(receiverTransaction.getReceiverAccountId()).get();
        senderTransaction.setAmount(receiverTransaction.getAmount());
        senderTransaction.setTransactionType(TransactionType.Transfer);
        senderTransaction.setTransactionDate(receiverTransaction.getTransactionDate());
        senderTransaction.setAccount(senderAccount);
        senderTransaction.setReceiverAccountId(receiverTransaction.getReceiverAccountId());
        transactionRepository.save(senderTransaction);

        receiverTransaction.setTransactionType(TransactionType.Income);
        receiverTransaction.setAccount(receiverAccount);
        transactionRepository.save(receiverTransaction);

        senderAccount.setBalance(senderAccount.getBalance()- receiverTransaction.getAmount());
        receiverAccount.setBalance(receiverAccount.getBalance()+ receiverTransaction.getAmount());
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
        }

    @Override
    public void executeScheduledTransactions() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        List<Transaction> scheduledTransacions =  transactionRepository.findTransactionByTransactionType(TransactionType.Scheduled);
        System.out.println(sdf.format(date));

        for (Transaction tran : scheduledTransacions){
            System.out.println(sdf.format(tran.getTransactionDate()));
            if (sdf.format(date).equals(sdf.format(tran.getTransactionDate()))){
                makeScheduledTransaction(tran.getIdTransaction());
            }

        }
    }

}
