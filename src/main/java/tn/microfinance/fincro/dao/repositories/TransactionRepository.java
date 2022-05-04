package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.model.TransactionType;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long> {
    @Query("select t from Transaction t where t.transactionType=?1")
    List<Transaction> findTransactionByTransactionType(TransactionType transactionType);
    @Query("select t from  Transaction t where t.transactionType=?1 and (t.senderAccountId=?2 or t.receiverAccountId=?2) order by t.transactionDate")
    List<Transaction> findAccountTransactionsByType(TransactionType transactionType, long accountId);
    @Query("select count (t) from Transaction t where t.transactionType=?1 and (t.senderAccountId=?2 or t.receiverAccountId=?2)")
    int getTransactionsNbreByAccountAndType(TransactionType transactionType, long accountId);
}
