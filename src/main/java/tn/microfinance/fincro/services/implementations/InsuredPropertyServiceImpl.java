package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import tn.microfinance.fincro.dao.model.InsuredProperty;
import tn.microfinance.fincro.dao.repositories.InsuredPropertyRepository;
import tn.microfinance.fincro.services.interfaces.IPropertyService;

import java.util.Optional;

public class InsuredPropertyServiceImpl implements IPropertyService {
    @Autowired
    InsuredPropertyRepository insuredpropertyRepository;
    @Override
    public InsuredProperty addInsuredProperty(InsuredProperty u) {

        return insuredpropertyRepository.save(u);
    }

    @Override
    public void deleteInsuredProperty(String id) {
        Optional<InsuredProperty> InsuredPropertyOp = insuredpropertyRepository.findById(Integer.parseInt(id));
        if (InsuredPropertyOp.isPresent()) {
            insuredpropertyRepository.delete(InsuredPropertyOp.get());
            System.out.println("InsuredProperty deleted");
        } else {
            System.out.println("InsuredProperty not found");
        }

    }

    @Override
    public InsuredProperty updateInsuredProperty(InsuredProperty u) {
        Integer t = u.getIdProperty();
        if(insuredpropertyRepository.findById(t).isPresent()){
            return insuredpropertyRepository.save(u);
        }
        else{
            System.out.println("InsuredProperty doesn't exist !");
            return null;
        }
    }

    @Override
    public InsuredProperty retrieveInsuredProperty(String id) {
        Optional<InsuredProperty> InsuredPropertyOp = insuredpropertyRepository.findById(Integer.parseInt(id));
        if (InsuredPropertyOp.isPresent()) {
            InsuredPropertyOp.get();
        } else {
            System.out.println("InsuredProperty not found");
        }

        return InsuredPropertyOp.get();
    }
}
