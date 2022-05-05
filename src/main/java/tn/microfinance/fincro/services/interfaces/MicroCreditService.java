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

    MicroCredit updateCredit(MicroCredit c,Long idAccount);

    MicroCredit retrieveCredit(Long id);

    List<MicroCredit> getCreditsByType(CreditType type);

    List<MicroCredit> getCreditsByStatus(CreditStatus status);

    List<MicroCredit> getCreditsByUserId(Long idUser);

    List<Object> Simulation(double amount, int period,String typePeriod);
    //Hashtable<String, Object> Simulation(double amount, int period,String typePeriod);
    double score(double amount,int period,String typePeriod);

    Hashtable<String,Double> FailureToPay(long idCredit,int period,double interestAmount);

    double calculateInterest(double score);

    //Recomendation

    int CapacityToPay(long idCredit, double crd);

    MicroCredit updateCreditStatus(MicroCredit c,Long idAccount,CreditStatus status);
}
