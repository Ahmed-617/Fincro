package tn.microfinance.fincro.services.interfaces;


import tn.microfinance.fincro.dao.model.InsurenceContract;
import tn.microfinance.fincro.dao.model.User;

import java.util.List;

public interface IInsurenceContractService {

    public List<InsurenceContract> retrieveAllContracts();
    public InsurenceContract addContract(InsurenceContract contract, int propertyId, String clientId);
    public void deleteContract(String id);
    public InsurenceContract updateContract(InsurenceContract ip);
    public InsurenceContract retrieveContract(String id);
    public void affecterPropertyAContract(int propertyId, int contractId);
    public void discountPremiumByStat(int contractId);
    public float PremiumManagementByClientSalary(String clientCIN);
    public Double PremiumManagementByInsuredPropertyValue(InsurenceContract contract,double premium);
    public Double PremiumManagementByInsuredPropertyStats(InsurenceContract contract, int propertyId);
    public Double PremiumManagementByZoning(InsurenceContract contract, User client);
    public InsurenceContract renewContract(int contractId);
    public List<InsurenceContract> retrieveAllContractsByClient(String clientCin);
    public void terminateContract(int contractId);
    public Double PremiumManagementByContractRank(InsurenceContract contract);

}
