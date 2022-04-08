package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.*;
import tn.microfinance.fincro.dao.repositories.AccountRepository;
import tn.microfinance.fincro.dao.repositories.InvestmentRepository;
import tn.microfinance.fincro.dao.repositories.MicroCreditRepository;
import tn.microfinance.fincro.dao.repositories.TransactionRepository;
import tn.microfinance.fincro.services.interfaces.InvestmentService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class InvestmentServiceImpl implements InvestmentService {
    @Autowired
    MicroCreditRepository microCreditRepository;
    @Autowired
    InvestmentRepository investmentRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    MicroCreditServiceImpl microCreditService;
    @Autowired
    TransactionServiceImpl transactionService;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Investment sellMicroCredit(long proposerAccountId, long microCreditId, int NbrePeriod, String typePeriod) {
        System.out.println("starting..");
        MicroCredit microCredit = microCreditRepository.findById(microCreditId).get();
        System.out.println("creditFound..");
        microCredit.setStatus(CreditStatus.ForSale);
        System.out.println("statusChanged..");
        System.out.println(microCredit);
        microCreditRepository.save(microCredit);
        System.out.println("microcreditUpdateded..");
        Investment investment = new Investment();
        investment.setAccountIv(accountRepository.findById(proposerAccountId).get());
        investment.setAccValue(microCredit.getAmountRemaining());
        investment.setInvestmentType(InvestmentType.MicroCreditProposal);
        investment.setStartDate(new Date());
        investment.setTypePeriod(typePeriod);
        investment.setNbreOfPeriods(NbrePeriod);
        investment.setMicroCreditId(microCreditId);
        investment.setProposerAccountId(proposerAccountId);
        System.out.println("investmentCreated..");
        System.out.println("Rem Amount : "+ microCredit.getAmountRemaining());
        System.out.println("Nbre of periods : "+investment.getNbreOfPeriods());
        System.out.println("Period Type : "+investment.getTypePeriod());
        double interest = (MicroCredit.getTMM()+microCreditService.calculateInterest(microCreditService.score(microCredit.getAmountRemaining(),investment.getNbreOfPeriods(),investment.getTypePeriod())))/100;
        System.out.println("Interest calculated..   :    "+interest );
        investment.setInterest(interest);
        investment.setAmount(calculatePrime(microCredit.getAmountRemaining(),interest,investment.getTypePeriod(),investment.getNbreOfPeriods()));
        System.out.println("Amount calculated..   :    "+investment.getAmount() );
        return investmentRepository.save(investment);
    }

    public double calculatePrime(double amount, double interest, String scheme, int NbreOfPeriods){
        interest = convertInterest(scheme, interest);
        return amount*(interest/(1-(1/Math.pow(1+interest,NbreOfPeriods))));
    }

    @Override
    public Investment purchaseMicroCredit(long investorAccountId, long investmentId ) {
        Account investorAccount = accountRepository.findById(investorAccountId).get();
        Account agencyAccount = accountRepository.findById(Long.valueOf(1)).get();
        Investment purchasedInvestment = investmentRepository.findById(investmentId).get();
        agencyAccount.setBalance(agencyAccount.getBalance()+purchasedInvestment.getAccValue());
        accountRepository.save(agencyAccount);
        investorAccount.setBalance(investorAccount.getBalance()-purchasedInvestment.getAccValue());
        Investment investment = new Investment();
        investment.setMicroCreditId(purchasedInvestment.getMicroCreditId());
        investment.setInvestorAccountId(investorAccountId);
        investment.setInvestmentType(InvestmentType.MicroCreditPurchase);
        investment.setNbreOfPeriods(purchasedInvestment.getNbreOfPeriods());
        investment.setAccountIv(investorAccount);
        investment.setStartDate(new Date());
        investment.setAmount(purchasedInvestment.getAmount());
        investment.setProposerAccountId(purchasedInvestment.getProposerAccountId());
        investment.setAccValue(purchasedInvestment.getAccValue());
        investment.setTypePeriod(purchasedInvestment.getTypePeriod());
        Transaction transaction = new Transaction();
        Date date = new Date() ;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (purchasedInvestment.getTypePeriod()){
            case "Monthly" : {
                c.add(Calendar.MONTH,1);
                break;
            }
            case "Quarterly" : {
                c.add(Calendar.MONTH,3);
                break;
            }
            case "Half-Yearly": {
                c.add(Calendar.MONTH,6);
                break;
            }
            case "Yearly" : {
                c.add(Calendar.YEAR,1);
                break;
            }
            default: {}
        }
        transaction.setTransactionDate(c.getTime());
        transaction.setTransactionType(TransactionType.Scheduled);
        transaction.setTransactionScheme(purchasedInvestment.getTypePeriod());
        transaction.setSenderAccountId(investment.getProposerAccountId());
        transaction.setReceiverAccountId(investment.getInvestorAccountId());
        transaction.setAmount(investment.getAmount());
        MicroCredit microCredit = microCreditRepository.findById(purchasedInvestment.getMicroCreditId()).get();
        microCredit.setStatus(CreditStatus.Closed);
        microCreditRepository.save(microCredit);
        transactionService.scheduleTransactions(transaction,investment.getNbreOfPeriods());
        accountRepository.save(investorAccount);

        investmentRepository.delete(purchasedInvestment);
        return investmentRepository.save(investment);
    }

    @Override
    public List<Investment> getInvestments() {
        return (List<Investment>) investmentRepository.findAll();
    }

    @Override
    public Investment getInvestmentsById(long id) {
        return investmentRepository.findById(id).get();
    }

    @Override
    public double savingInterestCalculation(int nbreOfPeriods, double montant, String scheme) {
        double fac = 0;
        double duree=0;
        montant=montant/1000;
        switch (scheme){
            case "Monthly" : {
                fac+=3;
                montant=montant*12;
                duree=nbreOfPeriods/12;
                break;
            }
            case "Quarterly" :{
                fac+=6;
                montant=montant*4;
                duree=nbreOfPeriods/4;
                break;
            }
            case "Half-Yearly" : {
                fac+=7.5;
                montant=montant*2;
                duree=nbreOfPeriods/2;
                break;
            }
            case "Yearly" : {
                fac+=9;
                duree=nbreOfPeriods;
                break;
            }
            default:{}
        }
        fac+= 8.148*duree+6.374*montant;
        return Math.log(fac)/2+MicroCredit.getTMM();
    }

    @Override
    public Investment makeSaving(long investorAccountId, int nbreOfPeriods, double amount, String typePeriod) {
        Investment investment = new Investment();
        double interest=savingInterestCalculation(nbreOfPeriods,amount,typePeriod);
        investment.setInterest(interest);
        interest=convertInterest(typePeriod, interest/100);
        System.out.println("interest = "+interest);
        investment.setAccValue(amount*((Math.pow(1+(interest),nbreOfPeriods)-1)/(interest)));
        investment.setInvestmentType(InvestmentType.Epargne);
        investment.setNbreOfPeriods(nbreOfPeriods);
        investment.setTypePeriod(typePeriod);
        investment.setStartDate(new Date());
        investment.setAmount(amount);
        investment.setInvestorAccountId(investorAccountId);
        investment.setAccountIv(accountRepository.findById(investorAccountId).get());
        Transaction transaction = new Transaction();
        transaction.setAccount(accountRepository.findById(investorAccountId).get());
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(TransactionType.Scheduled);
        transaction.setTransactionScheme(typePeriod);
        transaction.setAmount(amount);
        transaction.setSenderAccountId(investorAccountId);
        transaction.setReceiverAccountId(1L);
        Calendar c = transactionService.scheduleTransactions(transaction,nbreOfPeriods);
        Transaction repayement = new Transaction();
        repayement.setReceiverAccountId(investorAccountId);
        repayement.setAccount(accountRepository.findById(investorAccountId).get());
        repayement.setSenderAccountId(1L);
        repayement.setAmount(investment.getAccValue());
        repayement.setTransactionScheme("Normal");
        repayement.setTransactionType(TransactionType.Scheduled);
        repayement.setTransactionDate(c.getTime());
        transactionRepository.save(repayement);
        return investmentRepository.save(investment);
    }

    @Override
    public List<Investment> getAccountInvestsByType(InvestmentType investmentType, long accountId) {
        return investmentRepository.getAccountInvestmentsByType(investmentType,accountId);
    }

    public double convertInterest(String typePeriod, double interest) {
        switch(typePeriod){
            case "Monthly" : {
                interest = Math.pow(1+interest,(1d/12))-1;
                break;
            }
            case "Quarterly" : {
                interest = Math.pow(1+interest,1d/4)-1;
                break;
            }
            case "Half-Yearly" : {
                interest = Math.pow(1+interest,1d/2)-1;
                break;
            }
            default:{}
        }
        return interest;
    }
}













