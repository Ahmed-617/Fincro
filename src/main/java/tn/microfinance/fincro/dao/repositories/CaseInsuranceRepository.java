package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.CaseInsurance;
import tn.microfinance.fincro.dao.model.InsurenceContract;

import java.util.List;

@Repository
public interface CaseInsuranceRepository extends CrudRepository<CaseInsurance,Long> {
    @Query("SELECT u FROM CaseInsurance u WHERE u.status=0")
    List<CaseInsurance> getAllWaitingCases();

    @Query("SELECT u FROM CaseInsurance u WHERE u.status=1")
    List<CaseInsurance> getAllUncompletedCases();

    @Query("SELECT u FROM CaseInsurance u WHERE u.status=2")
    List<CaseInsurance> getAllCompletedCases();

    @Query("SELECT count(u) FROM CaseInsurance u WHERE u.fkContract= ?1")
    Integer getCountContractPerCase(InsurenceContract contratId);
}
