
package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.Claim;
import tn.microfinance.fincro.dao.model.MicroCredit;
import tn.microfinance.fincro.services.interfaces.ClaimService;

import java.util.List;
@RestController
public class ClaimRestController {

    @Autowired
    ClaimService claimService;

    @GetMapping("getallclaims")
    public List<Claim> getClaims(){
        return claimService.retrieveAllClaims();
    }

    @GetMapping("getClaim/{id}")
    public Claim getClaimById(@PathVariable("id") Long id){

        return claimService.retrieveClaim(id);
    }

    @PostMapping("addClaim")
    public Claim addClaim(@RequestBody Claim claim){
        return claimService.addClaim(claim);
    }

    @PutMapping("updateClaim")
    public Claim updateClaim(@RequestBody Claim claim){
        return claimService.updateClaim(claim);
    }

    @DeleteMapping("deleteClaim/{id}")
    public void deleteClaim(@PathVariable("id") Long id){
        claimService.deleteClaim(id);
    }




}
