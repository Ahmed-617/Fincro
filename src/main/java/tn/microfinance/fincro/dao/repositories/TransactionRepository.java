package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Integer> {
}
