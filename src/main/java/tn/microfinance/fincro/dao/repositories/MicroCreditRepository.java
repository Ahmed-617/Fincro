package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.CreditStatus;
import tn.microfinance.fincro.dao.model.CreditType;
import tn.microfinance.fincro.dao.model.MicroCredit;
import tn.microfinance.fincro.dao.model.Account;

import java.util.List;

@Repository
public interface MicroCreditRepository extends CrudRepository<MicroCredit,Long> {
    //public void Simulation(double amount,int period,double interest);

    @Query("SELECT c from  MicroCredit c where c.creditType = ?1")
    List<MicroCredit> retrieveCreditsByType(CreditType type);

    @Query("SELECT c from  MicroCredit c where c.status = ?1")
    List<MicroCredit> retrieveCreditsByStatus(CreditStatus status);

    @Query("SELECT c from MicroCredit c where c.accountFK.idAccount = (Select a.idAccount from Account a where a.user.userId=?1)")
    List<MicroCredit> retrieveCredetsByUserId(Long idUser);
}
