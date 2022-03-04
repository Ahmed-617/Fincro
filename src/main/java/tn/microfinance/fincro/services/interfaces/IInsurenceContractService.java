package tn.microfinance.fincro.services.interfaces;


import tn.microfinance.fincro.dao.model.InsurenceContract;

public interface IInsurenceContractService {

    public InsurenceContract addContract(InsurenceContract insurenceContract);
    public void deleteContract(String id);
    public InsurenceContract updateContract(InsurenceContract ip);
    public InsurenceContract retrieveContract(String id);

}
