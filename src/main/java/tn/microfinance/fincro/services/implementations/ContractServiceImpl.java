package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import tn.microfinance.fincro.dao.model.Contract;
import tn.microfinance.fincro.dao.repositories.ContractRepository;
import tn.microfinance.fincro.services.interfaces.IContractService;

import java.util.Optional;

public class ContractServiceImpl implements IContractService {
    @Autowired
    ContractRepository contractRepository;
    @Override
    public Contract addContract(Contract contract, int propertyId, String clientId) {
        return contractRepository.save(contract);
    }

    @Override
    public void deleteContract(String id) {
        Optional<Contract> ContractOp = contractRepository.findById(Integer.parseInt(id));
        if (ContractOp.isPresent()) {
            contractRepository.delete(ContractOp.get());
            System.out.println("Contract deleted");
        } else {
            System.out.println("Contract not found");
        }
    }

    @Override
    public Contract updateContract(Contract u) {
        int t = u.getIdContract();
        if (contractRepository.findById(t).isPresent()) {
            return contractRepository.save(u);
        } else {
            System.out.println("Contract doesn't exist !");
            return null;
        }

    }

    @Override
    public Contract retrieveContract(String id) {
        Optional<Contract> ContractOp = contractRepository.findById(Integer.parseInt(id));
        if (ContractOp.isPresent()) {
            ContractOp.get();
        } else {
            System.out.println("Contract not found");
        }

        return ContractOp.get();
    }
}
