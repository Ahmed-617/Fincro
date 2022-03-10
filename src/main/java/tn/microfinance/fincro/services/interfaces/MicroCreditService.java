package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.MicroCredit;

import java.util.Hashtable;
import java.util.List;

public interface MicroCreditService {
    List<MicroCredit> retrieveAllCredits();

    MicroCredit addCredit(MicroCredit c);

    void archiveCredit(Long id);

    void deleteCredit(Long id);

    MicroCredit updateCredit(MicroCredit c);

    MicroCredit retrieveCredit(Long id);

    Hashtable<String, Double> Simulation(double amount, int period, double interest);
}
