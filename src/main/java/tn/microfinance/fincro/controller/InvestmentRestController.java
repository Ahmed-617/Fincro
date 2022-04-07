package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.Investment;
import tn.microfinance.fincro.dao.model.InvestmentType;
import tn.microfinance.fincro.dao.model.TransactionType;
import tn.microfinance.fincro.services.interfaces.InvestmentService;

import java.util.List;

@RestController
public class InvestmentRestController {
    @Autowired
    InvestmentService investmentService;

    @PutMapping("sellCredit/{accountId}/{microCreditId}/{n}/{type}")
    public Investment sellCredit(@PathVariable("accountId") long accountId, @PathVariable("microCreditId") long microCreditId, @PathVariable("n") int n, @PathVariable("type") String type){
        return investmentService.sellMicroCredit(accountId,microCreditId,n,type);
    }

    @PostMapping("purchaseCredit/{accountId}/{investmentId}")
    public Investment purchaseCredit(@PathVariable("accountId") long accountId, @PathVariable("investmentId") long investmentId){
        return investmentService.purchaseMicroCredit(accountId,investmentId);
    }

    @GetMapping("getAllInvestments")
    public List<Investment> getAllInvests(){
        return investmentService.getInvestments();
    }

    @GetMapping("getInvestment/{id}")
    public Investment getInvest(@PathVariable("id") long id){
        return investmentService.getInvestmentsById(id);
    }

    @GetMapping("calculateSavingInterest/{nbreOfPeriods}/{montant}/{scheme}")
    public double calculateSavInterest(@PathVariable("nbreOfPeriods") int nbreOfPeriods, @PathVariable("montant") double montant, @PathVariable("scheme") String scheme){
        return investmentService.savingInterestCalculation(nbreOfPeriods, montant, scheme);
    }

    @PostMapping("MakeASaving/{investorId}/{nbreOfPeriods}/{amount}/{typePeriod}")
    public Investment makeSaving(@PathVariable("investorId") long investorId, @PathVariable("nbreOfPeriods") int nbreOfPeriods, @PathVariable("amount") double amount, @PathVariable("typePeriod") String typePeriod){
        return investmentService.makeSaving(investorId, nbreOfPeriods, amount, typePeriod);
    }

    @GetMapping("getInvestmentsByType/{type}/{id}")
    public List<Investment> getInvestsByType(@PathVariable("type")InvestmentType investmentType, @PathVariable("id") Long id){
        return investmentService.getAccountInvestsByType(investmentType,id);
    }
}
