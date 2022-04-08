package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.CaseInsurance;
import tn.microfinance.fincro.dao.repositories.CaseInsuranceRepository;
import tn.microfinance.fincro.services.interfaces.CaseInsuranceService;

import java.util.List;
@Service
public class CaseInsuranceImpl implements CaseInsuranceService {

@Autowired
    CaseInsuranceRepository caseInsuranceRepository;
 @Override
 public List<CaseInsurance> retrieveAllCases() {
     return null;
 }

    @Override
    public CaseInsurance addCase(CaseInsurance ci) {
      return caseInsuranceRepository.save(ci);
    }

    @Override
    public void deleteCase(Long id) {
        caseInsuranceRepository.deleteById(id);

    }

    @Override
    public CaseInsurance updateCase(CaseInsurance ci) {
     return null;
    }

    @Override
    public CaseInsurance retrieveCase(Long id) {
        return caseInsuranceRepository.findById(id).get();
    }
}//
