package tn.microfinance.fincro.services.interfaces;

import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.CaseInsurance;

import java.util.List;


public interface CaseInsuranceService {

    List<CaseInsurance> retrieveAllCases();

    CaseInsurance addCase(CaseInsurance ci);

    void deleteCase(Long id);

    CaseInsurance updateCase(CaseInsurance ci);

    CaseInsurance retrieveCase(Long id);
}//
