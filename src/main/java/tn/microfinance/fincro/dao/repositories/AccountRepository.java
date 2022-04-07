package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {
    @Query("select a.balance from Account a where a.idAccount =?1")
    double getBalanceById(long idaccount);
}
