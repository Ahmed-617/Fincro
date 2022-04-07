package tn.microfinance.fincro.services.interfaces;


import tn.microfinance.fincro.dao.model.InsurenceContract;
import tn.microfinance.fincro.dao.model.User;

import java.util.List;

public interface IInsurenceContractService {

  List<InsurenceContract> retrieveAllContracts();
  InsurenceContract addContract(InsurenceContract contract, int propertyId, int clientId);
  void deleteContract(String id);
  InsurenceContract updateContract(InsurenceContract ip);
  InsurenceContract retrieveContract(String id);
  void affecterPropertyAContract(int propertyId, int contractId);
  void discountPremiumByStat(int contractId);
  float PremiumManagementByClientSalary(int clientCIN);
  Double PremiumManagementByInsuredPropertyValue(InsurenceContract contract,double premium);
  Double PremiumManagementByInsuredPropertyStats(InsurenceContract contract, int propertyId);
  Double PremiumManagementByZoning(InsurenceContract contract, User client);
  InsurenceContract renewContract(int contractId);
  List<InsurenceContract> retrieveAllContractsByClient(int clientCin);
  void terminateContract(int contractId);
  Double PremiumManagementByContractRank(InsurenceContract contract);

}
