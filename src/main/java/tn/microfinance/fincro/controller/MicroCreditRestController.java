package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.MicroCredit;
import tn.microfinance.fincro.services.interfaces.MicroCreditService;

import java.util.List;

@RestController
public class MicroCreditRestController {

    @Autowired
    MicroCreditService creditService;

    @GetMapping("getAllCredits")
    public List<MicroCredit> getAccounts(){
        return creditService.retrieveAllCredits();
    }

    @GetMapping("getCredit/{id}")
    public MicroCredit getAccoutById(@PathVariable("id") Long id){
        return creditService.retrieveCredit(id);
    }

    @PostMapping("addCredit")
    public MicroCredit addAccount(@RequestBody MicroCredit credit){
        return creditService.addCredit(credit);
    }

    @PutMapping("updateCredit")
    public MicroCredit updateAccount(@RequestBody MicroCredit credit){
        return creditService.updateCredit(credit);
    }

    @DeleteMapping("deleteCredit/{id}")
    public void deleteAccount(@PathVariable("id") Long id){
        creditService.deleteCredit(id);
    }
}
