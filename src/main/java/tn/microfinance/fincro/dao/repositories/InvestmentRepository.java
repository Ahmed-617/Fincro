package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.Investment;
import tn.microfinance.fincro.dao.model.InvestmentType;

import java.util.List;

@Repository
public interface InvestmentRepository extends CrudRepository<Investment,Long> {
    @Query("select i from Investment i where i.investmentType=?1 and i.investorAccountId=?2")
    List<Investment> getAccountInvestmentsByType(InvestmentType investmentType, long accountId);
}
