package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.microfinance.fincro.dao.model.InsurenceContract;

import tn.microfinance.fincro.services.interfaces.IInsurenceContractService;



public class InsurenceContractRestController {

    @RestController
    public class InsurenceCcontractRestController {
        @Autowired
        IInsurenceContractService contractService;

        @PostMapping("addContract")
        public InsurenceContract addContract (@RequestBody InsurenceContract insurenceContract){
            return contractService.addContract(insurenceContract);
        }


    }

}
