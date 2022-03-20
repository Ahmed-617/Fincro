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
    public Hashtable<String, Double> Simulation(double amount, int period, String typePeriod) {
        Hashtable<String, Double> sim = new Hashtable<String, Double>();
        double mensuality;
        double crd;
        crd = amount;
        double interest= 6.25 + calculateInterest(score(amount,period,typePeriod));//score personel manquant
        interest=interest/100;
        System.out.println("interest "+ interest );
        switch (typePeriod){
            case "Monthly":{
                interest = Math.pow(1+interest,(1d/12))-1;
                System.out.println("interest "+ interest );
                mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

                sim.put("Mensuality", mensuality);
                System.out.println("Mensuality : "+mensuality);
                System.out.println("amount : "+amount);
                sim.put("CRD 1", crd);
                System.out.println("CRD 1 :"+crd);

                for (int i=2;i<=period;i++){
                    crd = crd - (mensuality-(crd*interest));
                    sim.put("CRD "+i, crd);
                    System.out.println("CRD "+i+" : "+crd);
                }
            }break;
            case "Half-Yearly":{ interest = Math.pow(1+interest,1d/2)-1;
                System.out.println("interest "+ interest );
                mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));
                sim.put("Mensuality", mensuality);
                System.out.println("Mensuality : "+mensuality);

                sim.put("CRD 1", crd);
                System.out.println("CRD 1 :"+crd);

                for (int i=2;i<=period;i++){
                    crd = crd - (mensuality-(crd*interest));
                    sim.put("CRD "+i, crd);
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            case "Quarterly" : { interest = Math.pow(1+interest,1d/4)-1;
                System.out.println("interest "+ interest );
                mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));
                sim.put("Mensuality", mensuality);
                System.out.println("Mensuality : "+mensuality);

                sim.put("CRD 1", crd);
                System.out.println("CRD 1 :"+crd);

                for (int i=2;i<=period;i++){
                    crd = crd - (mensuality-(crd*interest));
                    sim.put("CRD "+i, crd);
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            case "Yearly" : {
                mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));
                sim.put("Mensuality", mensuality);
                System.out.println("Mensuality : "+mensuality);

                sim.put("CRD 1", crd);
                System.out.println("CRD 1 :"+crd);

                for (int i=2;i<=period;i++){
                    crd = crd - (mensuality-(crd*interest));
                    sim.put("CRD "+i, crd);
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            default: {System.out.println("Invalid Type");}
        }


        return sim;
    }

    @Override
    public double score(double amount, int period, String typePeriod) {
        double scr=0;
        if(amount<=10000)
            scr = 400 - (amount/10000)*100;
        else if(amount<=20000)
            scr = 300 - ((amount-10000)/10000)*100;
        else if(amount<=30000)
            scr = 200 - ((amount-20000)/10000)*100;
        else if(amount<=40000)
            scr = 100 - ((amount-30000)/10000)*100;

        switch (typePeriod){
            case "Monthly" : {
                if(period<=21)
                    scr += 400 - ((double)period/21)*100;
                else if(period<=42)
                    scr += 300 - (((double)period-21)/21)*100;
                else if(period<=63)
                    scr += 200 - (((double)period-42)/21)*100;
                else if(period<=84)
                    scr += 100 - (((double)period-63)/21)*100;
            }break;
            case "Quarterly" : {
                period = period*4;
                if(period<=21)
                    scr += 400 - ((double)period/21)*100;
                else if(period<=42)
                    scr += 300 - (((double)period-21)/21)*100;
                else if(period<=63)
                    scr += 200 - (((double)period-42)/21)*100;
                else if(period<=84)
                    scr += 100 - (((double)period-63)/21)*100;
            }break;
            case "Half-Yearly" : {
                period = period*6;
                if(period<=21)
                    scr += 400 - ((double)period/21)*100;
                else if(period<=42)
                    scr += 300 - (((double)period-21)/21)*100;
                else if(period<=63)
                    scr += 200 - (((double)period-42)/21)*100;
                else if(period<=84)
                    scr += 100 - (((double)period-63)/21)*100;
            }break;
            case "Yearly" : {
                period = period*12;
                if(period<=21)
                    scr += 400 - ((double)period/21)*100;
                else if(period<=42)
                    scr += 300 - (((double)period-21)/21)*100;
                else if(period<=63)
                    scr += 200 - (((double)period-42)/21)*100;
                else if(period<=84)
                    scr += 100 - (((double)period-63)/21)*100;
            }break;
            default: {scr = 0;}
        }


        return scr;
    }

    @Override
    public double calculateInterest(double score) {
        double interest = 0;

        if (score<=500)
            interest = 3;
        else if ((score>500)&&(score<=1000))
            interest = 2.5;
        else if ((score>1000)&&(score<=1300))
            interest = 2.25;
        else if ((score>1300)&&(score<=1500))
            interest = 2;
        else if ((score>1500)&&(score<=1800))
            interest = 1.5;
        else if ((score>1800)&&(score<=2000))
            interest = 1.25;

        return interest;
    }

    @Override
    public Hashtable<String, Double> FailureToPay(long idCredit,double crd, double interestAmount) {
        Hashtable<String, Double> newCredit = new Hashtable<String, Double>();
        double mensuality;
        int newPeriod = retrieveCredit(idCredit).getPeriod()+3;//ajout period selon type
        double newAmount = crd+interestAmount;
        //score personel manquant
        double interestRate =6.25 + calculateInterest(score(newAmount,newPeriod,retrieveCredit(idCredit).getTypePeriod()));
        interestRate=interestRate/100;

        switch (retrieveCredit(idCredit).getTypePeriod()){
            case "Monthly":{
                interestRate = Math.pow(1+interestRate,(1d/12))-1;
                System.out.println("interest "+ interestRate );
                mensuality = (newAmount*interestRate)/(1-Math.pow(1+interestRate,-newPeriod));

                newCredit.put("Mensuality", mensuality);
                System.out.println("Mensuality : "+mensuality);
                System.out.println("amount : "+newAmount);
                newCredit.put("CRD 1", crd);
                System.out.println("CRD 1 :"+crd);

                for (int i=2;i<=newPeriod;i++){
                    crd = crd - (mensuality-(crd*interestRate));
                    newCredit.put("CRD "+i, crd);
                    System.out.println("CRD "+i+" : "+crd);
                }
            }break;
            case "Half-Yearly":{
                interestRate = Math.pow(1+interestRate,1d/2)-1;
                System.out.println("interest "+ interestRate );
                mensuality = (newAmount*interestRate)/(1-Math.pow(1+interestRate,-newPeriod));
                newCredit.put("Mensuality", mensuality);
                System.out.println("Mensuality : "+mensuality);

                newCredit.put("CRD 1", crd);
                System.out.println("CRD 1 :"+crd);

                for (int i=2;i<=newPeriod;i++){
                    crd = crd - (mensuality-(crd*interestRate));
                    newCredit.put("CRD "+i, crd);
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            case "Quarterly" : {
                interestRate = Math.pow(1+interestRate,1d/4)-1;
                System.out.println("interest "+ interestRate );
                mensuality = (newAmount*interestRate)/(1-Math.pow(1+interestRate,-newPeriod));
                newCredit.put("Mensuality", mensuality);
                System.out.println("Mensuality : "+mensuality);

                newCredit.put("CRD 1", crd);
                System.out.println("CRD 1 :"+crd);

                for (int i=2;i<=newPeriod;i++){
                    crd = crd - (mensuality-(crd*interestRate));
                    newCredit.put("CRD "+i, crd);
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            case "Yearly" : {
                mensuality = (newAmount*interestRate)/(1-Math.pow(1+interestRate,-newPeriod));
                newCredit.put("Mensuality", mensuality);
                System.out.println("Mensuality : "+mensuality);

                newCredit.put("CRD 1", crd);
                System.out.println("CRD 1 :"+crd);

                for (int i=2;i<=newPeriod;i++){
                    crd = crd - (mensuality-(crd*interestRate));
                    newCredit.put("CRD "+i, crd);
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            default: {System.out.println("Invalid Type");}
        }

        return newCredit;
    }
}
