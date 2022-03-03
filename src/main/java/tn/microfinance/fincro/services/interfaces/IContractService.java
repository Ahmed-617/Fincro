package tn.microfinance.fincro.services.interfaces;


import tn.microfinance.fincro.dao.model.Contract;

import java.util.List;

public interface IContractService {

    public Contract addContract(Contract contract, int propertyId, String clientId);
    public void deleteContract(String id);
    public Contract updateContract(Contract ip);
    public Contract retrieveContract(String id);

}
