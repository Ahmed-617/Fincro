package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.Investment;
import tn.microfinance.fincro.dao.model.InvestmentType;

import java.util.List;

public interface InvestmentService {

    public Investment sellMicroCredit(long proposerAccountId, long microCreditId, int NbrePeriod, String typePeriod);
    public Investment purchaseMicroCredit(long investmentId, long investorAccountId);
    public List<Investment> getInvestments();
    public Investment getInvestmentsById(long id);
    public double savingInterestCalculation(int nbreOfPeriods, double montant, String scheme);
    public Investment makeSaving(long investorAccountId, int nbreOfPeriods, double amount, String typePeriod);
    public List<Investment> getAccountInvestsByType(InvestmentType investmentType, long accountId);
}
