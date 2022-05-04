package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @PutMapping("sellCredit/{accountId}/{microCreditId}/{n}/{type}")
    public Investment sellCredit(@PathVariable("accountId") long accountId, @PathVariable("microCreditId") long microCreditId, @PathVariable("n") int n, @PathVariable("type") String type){
        return investmentService.sellMicroCredit(accountId,microCreditId,n,type);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @PostMapping("purchaseCredit/{accountId}/{investmentId}")
    public Investment purchaseCredit(@PathVariable("accountId") long accountId, @PathVariable("investmentId") long investmentId){
        return investmentService.purchaseMicroCredit(accountId,investmentId);
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping("getAllInvestments")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public List<Investment> getAllInvests(){
        return investmentService.getInvestments();
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("getInvestment/{id}")
    public Investment getInvest(@PathVariable("id") long id){
        return investmentService.getInvestmentsById(id);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("calculateSavingInterest/{nbreOfPeriods}/{montant}/{scheme}")
    public double calculateSavInterest(@PathVariable("nbreOfPeriods") int nbreOfPeriods, @PathVariable("montant") double montant, @PathVariable("scheme") String scheme){
        return investmentService.savingInterestCalculation(nbreOfPeriods, montant, scheme);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @PostMapping("MakeASaving/{investorId}/{nbreOfPeriods}/{amount}/{typePeriod}")
    public Investment makeSaving(@PathVariable("investorId") long investorId, @PathVariable("nbreOfPeriods") int nbreOfPeriods, @PathVariable("amount") double amount, @PathVariable("typePeriod") String typePeriod){
        return investmentService.makeSaving(investorId, nbreOfPeriods, amount, typePeriod);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("getInvestmentsByType/{type}/{id}")
    public List<Investment> getInvestsByType(@PathVariable("type")InvestmentType investmentType, @PathVariable("id") Long id){
        return investmentService.getAccountInvestsByType(investmentType,id);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("calculateAccValue/{investorId}/{nbreOfPeriods}/{amount}/{typePeriod}")
    public double calculateAccValue(@PathVariable("nbreOfPeriods") int nbreOfPeriods, @PathVariable("amount") double amount, @PathVariable("typePeriod") String typePeriod){
        return investmentService.calculateAccValue(nbreOfPeriods,amount,typePeriod);
    }
}
