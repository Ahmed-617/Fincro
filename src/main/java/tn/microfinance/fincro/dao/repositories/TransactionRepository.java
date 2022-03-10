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
}
