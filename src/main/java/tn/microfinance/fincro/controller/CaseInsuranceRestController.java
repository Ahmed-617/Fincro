package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.CaseInsurance;
import tn.microfinance.fincro.services.interfaces.CaseInsuranceService;

import java.util.List;

@RestController
public class CaseInsuranceRestController {

    @Autowired
    CaseInsuranceService caseInsuranceService;


    @GetMapping("getAllCases")
    public List<CaseInsurance> getCases(){
        return caseInsuranceService.retrieveAllCases();
    }

    @GetMapping("getCase/{id}")
    public CaseInsurance getCaseById(@PathVariable("id") Long id){
        return caseInsuranceService.retrieveCase(id);
    }

    @PostMapping("addCase")
    public CaseInsurance addCase(@RequestBody CaseInsurance caseInsurance){
        return caseInsuranceService.addCase(caseInsurance);
    }

    @PutMapping("updateCase")
    public CaseInsurance updateCase(@RequestBody CaseInsurance caseInsurance){
        return caseInsuranceService.updateCase(caseInsurance);
    }

    @DeleteMapping("deleteCase/{id}")
    public void deleteCase(@PathVariable("id") Long id){
        caseInsuranceService.deleteCase(id);
    }

}
