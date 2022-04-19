package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.CreditStatus;
import tn.microfinance.fincro.dao.model.CreditType;
import tn.microfinance.fincro.dao.model.MicroCredit;

import java.util.Hashtable;
import java.util.List;

public interface MicroCreditService {
    List<MicroCredit> retrieveAllCredits();

    MicroCredit addCredit(MicroCredit c,Long idAccount);

    void archiveCredit(Long id);

    void deleteCredit(Long id);

    MicroCredit updateCredit(MicroCredit c);

    MicroCredit retrieveCredit(Long id);

    List<MicroCredit> getCreditsByType(CreditType type);

    List<MicroCredit> getCreditsByStatus(CreditStatus status);

    Hashtable<String, Double> Simulation(double amount, int period,String typePeriod);

    double score(double amount,int period,String typePeriod);

    Hashtable<String,Double> FailureToPay(long idCredit,int period,double interestAmount);

    double calculateInterest(double score);

    //Recomendation

    int CapacityToPay(long idCredit, double crd);
}
