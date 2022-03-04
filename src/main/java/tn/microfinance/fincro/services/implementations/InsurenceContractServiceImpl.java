package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.InsurenceContract;
import tn.microfinance.fincro.dao.repositories.InsurenceContractRepository;
import tn.microfinance.fincro.services.interfaces.IInsurenceContractService;

import java.util.Optional;
@Service
public class InsurenceContractServiceImpl implements IInsurenceContractService {
    @Autowired
    InsurenceContractRepository insurenceContractRepository;
    @Override
    public InsurenceContract addContract(InsurenceContract insurenceContract) {
        return insurenceContractRepository.save(insurenceContract);
    }

    @Override
    public void deleteContract(String id) {
        Optional<InsurenceContract> ContractOp = insurenceContractRepository.findById(Integer.parseInt(id));
        if (ContractOp.isPresent()) {
            insurenceContractRepository.delete(ContractOp.get());
            System.out.println("Insurence Contract deleted");
        } else {
            System.out.println("Insurence Contract not found");
        }
    }

    @Override
    public InsurenceContract updateContract(InsurenceContract u) {
        int t = u.getIdContract();
        if (insurenceContractRepository.findById(t).isPresent()) {
            return insurenceContractRepository.save(u);
        } else {
            System.out.println("Insurence Contract doesn't exist !");
            return null;
        }

    }

    @Override
    public InsurenceContract retrieveContract(String id) {
        Optional<InsurenceContract> ContractOp = insurenceContractRepository.findById(Integer.parseInt(id));
        if (ContractOp.isPresent()) {
            ContractOp.get();
        } else {
            System.out.println("Insurence Contract not found");
        }

        return ContractOp.get();
    }
}
