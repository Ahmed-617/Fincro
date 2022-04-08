package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.microfinance.fincro.dao.model.InsurenceContract;
import tn.microfinance.fincro.services.interfaces.IInsurenceContractService;
import tn.microfinance.fincro.services.interfaces.IPropertyService;

import java.util.List;

@RestController

public class ContractRestController {
    @Autowired
    IPropertyService propertyService;

    @Autowired
    IInsurenceContractService contractService;

    // Ajouter Contract :
   // http://localhost:8081/SpringMVC/add-contract/1/1?idClient=1&idProperty=1
    @PostMapping("/add-contract/{idProperty}/{idClient}")
    public void addContract(@RequestBody InsurenceContract contract, @PathVariable("idProperty") int propertyId,
                            @PathVariable("idClient") String cin) {
        contractService.addContract(contract, propertyId, Integer.parseInt(cin));
    }



    @GetMapping("/retrieve-all-contracts")
    @ResponseBody
    public List<InsurenceContract> getContracts() {
        List<InsurenceContract> list = contractService.retrieveAllContracts();
        return list;
    }


    @PutMapping("/discountStat/{contractId}")
    @ResponseBody
    public void discountStat(@PathVariable("contractId") int contractId) {
        contractService.discountPremiumByStat(contractId);
    }


    @PutMapping("/renewContract/{contractId}")
    @ResponseBody
    public void renewContract(@PathVariable("contractId") int contractId) {
        contractService.renewContract(contractId);
    }


    @GetMapping("/retrieve-all-contracts/{clientCIN}")
    @ResponseBody
    public List<InsurenceContract> getContractsByClient(@PathVariable("clientCIN") String clientCIN) {
        List<InsurenceContract> list = contractService.retrieveAllContracts();
        return list;
    }


    @PutMapping("/terminateContract/{contractId}")
    @ResponseBody
    public void terminateContract(@PathVariable("contractId") int contractId) {
        contractService.terminateContract(contractId);
    }


    @GetMapping("/retrieveContract/{contractId}")
    @ResponseBody
    public InsurenceContract retrieveContract(@PathVariable("contractId") String contractId) {
        return contractService.retrieveContract(contractId);
    }
}