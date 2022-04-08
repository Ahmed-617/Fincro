package tn.microfinance.fincro.services.implementations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.*;
import tn.microfinance.fincro.dao.repositories.ContractRepository;
import tn.microfinance.fincro.dao.repositories.InsuredPropertyRepository;
import tn.microfinance.fincro.dao.repositories.UserRepository;
import tn.microfinance.fincro.services.interfaces.IInsurenceContractService;
import tn.microfinance.fincro.services.interfaces.IPropertyService;


import java.util.*;

@Service
public class ContractServiceImpl implements IInsurenceContractService {
    @Autowired
    ContractRepository contractRepository;

    @Autowired
    InsuredPropertyRepository propertyRepository;

    @Autowired
    UserRepository clientRepository;

    @Autowired
    IPropertyService propertyService;

    //@Autowired
    //MailClient mailService;

    public static final Logger L = LogManager.getLogger(ContractServiceImpl.class);

    public ContractRepository getContract() {
        return contractRepository;
    }

    public void setContract(ContractRepository ContractRepository) {
        this.contractRepository = ContractRepository;
    }

    @Override
    public List<InsurenceContract> retrieveAllContracts() {
        List<InsurenceContract> Contracts = (List<InsurenceContract>) contractRepository.findAll();

        for (InsurenceContract InsurenceContract : Contracts) {
            L.info("Contract +++" + InsurenceContract);
            ;
        }
        return Contracts;
    }

    // Dans le cas du renouvellement
    @Override
    public void discountPremiumByStat(int contractId) {
        InsurenceContract contractManagedEntity = new InsurenceContract();
        contractManagedEntity = contractRepository.findById(contractId).get();
        PropertyType propertyType = contractRepository.getPropertyTypeByContractId(contractId);
        switch (propertyType) {
            case VEHICULE:
                if ((double) propertyRepository.countInsuredPropertyByType(propertyType)
                        / propertyRepository.countInsuredProperty() > 0.75) {
                    contractManagedEntity.setPremium(contractManagedEntity.getPremium() * 0.9);
                    contractRepository.save(contractManagedEntity);
                }
                break;
            case FERME:
                if ((double) propertyRepository.countInsuredPropertyByType(propertyType)
                        / propertyRepository.countInsuredProperty() >= 0.3) {
                    contractManagedEntity.setPremium(contractManagedEntity.getPremium() * 0.95);
                    contractRepository.save(contractManagedEntity);
                }
                break;
            case TELEPHONE:
                if ((double) propertyRepository.countInsuredPropertyByType(propertyType)
                        / propertyRepository.countInsuredProperty() >= 0.3) {
                    contractManagedEntity.setPremium(contractManagedEntity.getPremium() * 0.85);
                    contractRepository.save(contractManagedEntity);
                }
                break;
            default:
                break;

        }

    }

    @Override
    public Double PremiumManagementByInsuredPropertyValue(InsurenceContract contract, double premium) {
        InsuredProperty property = contract.getFkInsuredProperty();
        switch (property.getPropertyType()) {
            case VEHICULE:
                if (property.getPropertyValue() >= 5000 && property.getPropertyValue() < 10000) {
                    contract.setPremium(premium * 1.1);
                } else if (property.getPropertyValue() >= 10000) {
                    contract.setPremium(premium * 1.3);
                }
                break;
            case FERME:
                if (property.getPropertyValue() >= 25000 && property.getPropertyValue() < 50000) {
                    contract.setPremium(premium * 1.3);
                }
                if (property.getPropertyValue() >= 50000 && property.getPropertyValue() < 100000) {
                    contract.setPremium(premium * 1.5);
                } else if (property.getPropertyValue() >= 100000) {
                    contract.setPremium(premium * 1.7);
                }
                break;

            case MAGASIN:
                contract.setPremium(premium * 1.1);
                break;

            case SOCIETE:
                contract.setPremium(premium * 1.5);
                break;
            default:
                break;
        }

        return contract.getPremium();
    }

    @Override
    public float PremiumManagementByClientSalary(int clientCIN) {
       User clientManagedEntity = clientRepository.findByCin(clientCIN);
        float salary = clientManagedEntity.getSalary();
        System.out.println("test    "+clientCIN);
        if (salary < 150) {
            return (float) (salary * 0.15);
        } else if ((150 <= salary) && (salary < 200)) {
            return (float) (salary * 0.2);
        } else if ((200 <= salary) && (salary < 300)) {
            return (float) (salary * 0.25);
        } else if (salary >= 300) {
            return (float) (salary * 0.25);
        }
        return salary;
    }

    @Override
    public Double PremiumManagementByInsuredPropertyStats(InsurenceContract contract, int propertyId) {
        InsuredProperty propertyManagedEntity = propertyRepository.findById(propertyId).get();
        PropertyType propertyType = propertyManagedEntity.getPropertyType();
        switch (propertyType) {
            case VEHICULE:
                if ((double) propertyRepository.countInsuredPropertyByType(propertyType)
                        / propertyRepository.countInsuredProperty() > 0.75) {
                    contract.setPremium(contract.getPremium() * 0.9);
                }
                break;
            case FERME:
                if ((double) propertyRepository.countInsuredPropertyByType(propertyType)
                        / propertyRepository.countInsuredProperty() >= 0.3) {
                    contract.setPremium(contract.getPremium() * 0.95);
                }
                break;

            case TELEPHONE:
                if ((double) propertyRepository.countInsuredPropertyByType(propertyType)
                        / propertyRepository.countInsuredProperty() >= 0.3) {
                    contract.setPremium(contract.getPremium() * 0.85);
                }
                break;
            default:
                break;

        }
        return contract.getPremium();
    }

    @Override
    public Double PremiumManagementByZoning(InsurenceContract contract, User client) {
        Set<Address> GrandTunis = EnumSet.of(Address.TUNIS, Address.BENAROUS, Address.ARIANA, Address.MANOUBA);
        if (GrandTunis.contains(client.getAdress())) {
            contract.setPremium(contract.getPremium() * 1.05);
        } else {
            contract.setPremium(contract.getPremium() * 1.103);
        }

        return contract.getPremium();
    }

    @Override
    public Double PremiumManagementByContractRank(InsurenceContract contract) {
        switch (contract.getContractRank()) {
            case 2:
                contract.setPremium(contract.getPremium() + 13.02);
                break;
            case 3:
                contract.setPremium(contract.getPremium() + 19.46);
        }
        return contract.getPremium();
    }

    @Override
    public InsurenceContract addContract(InsurenceContract contract, int propertyId, int clientCIN) {

        // Assigning the Insured Property and the Client to the contract
        InsuredProperty propertyManagedEntity = propertyRepository.findById(propertyId).get();
        User clientManagedEntity = clientRepository.findByCin(clientCIN);
        contract.setFkInsuredProperty(propertyManagedEntity);
        contract.setFkClient(clientManagedEntity);

        // Calculating Premium using Client's Salary
        float premium = PremiumManagementByClientSalary(clientCIN);
        contract.setPremium((double) premium);

        // Calculating Premium using Client's address 'zoning'
        contract.setPremium(PremiumManagementByZoning(contract, clientManagedEntity));

        // Discount Premium using Contracts' number
        int nbContracts = contractRepository.countContractNumberByClientCIN(clientCIN);
        if (nbContracts >= 2) {
            contract.setPremium(contract.getPremium() * 0.9);
        }

        // Calculating Premium using Insured Properties Statistics
        contract.setPremium(PremiumManagementByInsuredPropertyStats(contract, propertyId));

        // Calculating Premium using Insured Property's value
        contract.setPremium(PremiumManagementByInsuredPropertyValue(contract, contract.getPremium()));

        // Calculating Premium using Contract's rank value
        contract.setPremium(PremiumManagementByContractRank(contract));
        return contractRepository.save(contract);
    }


    @Override
    @SuppressWarnings("deprecation")
    public InsurenceContract renewContract(int contractId) {
        InsurenceContract contractManagedEntity = contractRepository.findById(contractId).get();

        // Loyal Customer
        if ((contractManagedEntity.getDueDateContract().getYear()
                - contractManagedEntity.getStartDateContract().getYear()) >= 2) {
            contractManagedEntity.setPremium(contractManagedEntity.getPremium() * 0.95);
        }

        // Calculating Premium using Insured Properties Statistics
        contractManagedEntity.setPremium(PremiumManagementByInsuredPropertyStats(contractManagedEntity,
                contractManagedEntity.getFkInsuredProperty().getIdProperty()));

        // Adding another year to the contract
        Date oldDate = contractManagedEntity.getDueDateContract();
        DateTime dtOrg = new DateTime(oldDate);
        DateTime newDate = dtOrg.plusYears(1);
        contractManagedEntity.setDueDateContract(newDate.toDate());

        // Mail Service
        /*String email = contractManagedEntity.getFkClient().getEmail();
        String fname = contractManagedEntity.getFkClient().getName();
        String lname = contractManagedEntity.getFkClient().getLastName();
*/
      //  mailService.prepareAndSend1(email, fname, fname, lname, contractManagedEntity.getClauses(),
             //   contractManagedEntity.getFkInsuredProperty().getPropertyValue(), contractManagedEntity.getPremium();

        return contractRepository.save(contractManagedEntity);
    }

    @Override
    public void terminateContract(int contractId){
        InsurenceContract contractManagedEntity = contractRepository.findById(contractId).get();

        contractManagedEntity.setVisibility(false);
        contractRepository.save(contractManagedEntity);
    }

    @Override
    public void deleteContract(String id) {
        Optional<InsurenceContract> ContractOp = contractRepository.findById(Integer.parseInt(id));
        if (ContractOp.isPresent()) {
            contractRepository.delete(ContractOp.get());
            System.out.println("Contract deleted");
        } else {
            System.out.println("Contract not found");
        }

    }

    @Override
    public InsurenceContract updateContract(InsurenceContract u) {
        int t = u.getIdContract();
        if (contractRepository.findById(t).isPresent()) {
            return contractRepository.save(u);
        } else {
            System.out.println("Contract doesn't exist !");
            return null;
        }
    }

    @Override
    public InsurenceContract retrieveContract(String id) {
        Optional<InsurenceContract> ContractOp = contractRepository.findById(Integer.parseInt(id));
        if (ContractOp.isPresent()) {
            ContractOp.get();
        } else {
            System.out.println("Contract not found");
        }
        L.info("Contract +++" + ContractOp.get());
        return ContractOp.get();
    }

    @Override
    public void affecterPropertyAContract(int propertyId, int contractId) {
        InsurenceContract contractManagedEntity = contractRepository.findById(contractId).get();
        InsuredProperty propertyManagedEntity = propertyRepository.findById(propertyId).get();

        contractManagedEntity.setFkInsuredProperty(propertyManagedEntity);
        contractRepository.save(contractManagedEntity);
    }

    @Override
    public List<InsurenceContract> retrieveAllContractsByClient(int  clientCin) {
        List<InsurenceContract> Contracts = (List<InsurenceContract>) contractRepository.findContractsByClientCIN(clientCin);

        for (InsurenceContract InsurenceContract : Contracts) {
            L.info("Contract +++" + InsurenceContract);

        }
        return Contracts;
    }

}
