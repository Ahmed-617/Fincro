package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.MicroCredit;
import tn.microfinance.fincro.dao.repositories.MicroCreditRepository;
import tn.microfinance.fincro.services.interfaces.MicroCreditService;

import java.util.Hashtable;
import java.util.List;

@Service
public class MicroCreditServiceImpl implements MicroCreditService {
    @Autowired
    MicroCreditRepository creditRepo;

    @Override
    public List<MicroCredit> retrieveAllCredits() {
       return (List<MicroCredit>) creditRepo.findAll();
    }

    @Override
    public MicroCredit addCredit(MicroCredit c) {
        System.out.println("Credit added");
        return creditRepo.save(c);

    }

    @Override
    public void archiveCredit(Long id) {

    }

    @Override
    public void deleteCredit(Long id) {
        MicroCredit credit= creditRepo.findById(id).get();
        if (creditRepo.findById(id).isPresent()) {
            creditRepo.delete(credit);
            System.out.println("Credit deleted");
        } else {
            System.out.println("Credit not found");
        }
    }

    @Override
    public MicroCredit updateCredit(MicroCredit c) {
        Long t = c.getIdCredit();
        if (creditRepo.findById(t).isPresent()) {
            return creditRepo.save(c);
        } else {
            System.out.println("credit doesn't exist !");
            return null;
        }
    }

    @Override
    public MicroCredit retrieveCredit(Long id) {

        return creditRepo.findById(id).get();
    }

    @Override
    public Hashtable<String, Double> Simulation(double amount, int period, double interest) {
        Hashtable<String, Double> sim = new Hashtable<String, Double>();
        double mensuality;
        double crd;
        crd = amount;
        interest=interest/100;

        mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));
        sim.put("Mensuality", mensuality);
        System.out.println("Mensuality : "+mensuality);

        sim.put("CRD 1", crd);
        System.out.println("CRD 1 :"+crd);

        for (int i=2;i<=period;i++){
             crd = crd - (mensuality-(crd*interest));
             sim.put("CRD "+i, crd);
            System.out.println("CRD "+i+" : "+crd);
        }

        return sim;
    }
}
