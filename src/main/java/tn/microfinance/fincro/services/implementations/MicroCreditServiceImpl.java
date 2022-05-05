package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.CreditStatus;
import tn.microfinance.fincro.dao.model.CreditType;
import tn.microfinance.fincro.dao.model.MicroCredit;
import tn.microfinance.fincro.dao.repositories.AccountRepository;
import tn.microfinance.fincro.dao.repositories.MicroCreditRepository;
import tn.microfinance.fincro.services.interfaces.MicroCreditService;

import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

@Service
public class MicroCreditServiceImpl implements MicroCreditService {
    @Autowired
    MicroCreditRepository creditRepo;
    @Autowired
    AccountRepository accountRepo;
    private static final DecimalFormat df = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.US));

    @Override
    public List<MicroCredit> retrieveAllCredits() {
        return (List<MicroCredit>) creditRepo.findAll();
    }

    @Override
    public MicroCredit addCredit(MicroCredit c,Long idAccount) {
        System.out.println("Credit added");
        Account ac = accountRepo.findById(idAccount).get();
        c.setAccountFK(ac);
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
    public MicroCredit updateCredit(MicroCredit c,Long idAccount) {
        Account ac = accountRepo.findById(idAccount).get();
        Long id = c.getIdCredit();
        if (creditRepo.findById(id).isPresent()) {
            c.setAccountFK(ac);
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
    public List<MicroCredit> getCreditsByType(CreditType type) {
        return creditRepo.retrieveCreditsByType(type);
    }

    @Override
    public List<MicroCredit> getCreditsByStatus(CreditStatus status) {
        return creditRepo.retrieveCreditsByStatus(status);
    }

   /* @Override
    public Hashtable<String, Double> Simulation(double amount, int period, String typePeriod) {
            Hashtable<String, Double> sim = new Hashtable<String, Double>();
        double mensuality;
        double crd;
        crd = amount;
        double interest= 6.25 + calculateInterest(score(amount,period,typePeriod));//score personel manquant
        sim.put("InterestRate",interest);
        interest=interest/100;
        System.out.println("interest "+ interest );
        switch (typePeriod){
            case "Monthly":{
                interest = Math.pow(1+interest,(1d/12))-1;
               // sim.put("InterestRate",Double.parseDouble(df.format(interest*100)));
                System.out.println("interest "+ interest );
                mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

                sim.put("Mensuality",Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : "+Double.parseDouble(df.format(mensuality)));
                System.out.println("amount : "+amount);
                sim.put("CRD1", crd);
                sim.put("I1", crd * interest);
                sim.put("P1",mensuality-(crd*interest));

                //  System.out.println("CRD 1 :"+Double.parseDouble(df.format(crd)));
                // System.out.println("CRD 1 :"+df.format(crd));

                for (int i=2;i<=period;i++){
                    double interet = crd * interest;
                    sim.put("I"+i,interet);
                    double principal = mensuality - interet;
                    sim.put("P"+i,principal);
                    crd = crd - principal;
                    sim.put("CRD"+i, Double.parseDouble(df.format(crd)));
                    System.out.println("CRD "+i+" : "+Double.parseDouble(df.format(crd)));
                }
            }break;
            case "Half-Yearly":{
                interest = Math.pow(1+interest,1d/2)-1;
               // sim.put("InterestRate",Double.parseDouble(df.format(interest*100)));
                System.out.println("interest "+ interest );
                mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

                sim.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : "+mensuality);
                System.out.println("amount : "+amount);
                sim.put("CRD1", crd);
                System.out.println("CRD 1 :"+Double.parseDouble(df.format(crd)));
                sim.put("I1", crd * interest);
                sim.put("P1",mensuality-(crd*interest));

                for (int i=2;i<=period;i++){
                    double interet = crd * interest;
                    sim.put("I"+i,interet);
                    double principal = mensuality - interet;
                    sim.put("P"+i,principal);
                    crd = crd - principal;
                    sim.put("CRD"+i, crd);
                    System.out.println("CRD "+i+" : "+Double.parseDouble(df.format(crd)));
                }
            }break;
            case "Quarterly" : {
                interest = Math.pow(1+interest,1d/4)-1;
               // sim.put("InterestRate",Double.parseDouble(df.format(interest*100)));
                System.out.println("interest "+ interest );
                mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

                sim.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : "+mensuality);
                System.out.println("amount : "+amount);
                sim.put("CRD1", crd);
                System.out.println("CRD 1 :"+Double.parseDouble(df.format(crd)));
                sim.put("I1", crd * interest);
                sim.put("P1",mensuality-(crd*interest));

                for (int i=2;i<=period;i++){
                    crd = crd - (mensuality-(crd*interest));
                    sim.put("CRD"+i, crd);
                    System.out.println("CRD "+i+" : "+Double.parseDouble(df.format(crd)));
                }
            }break;
            case "Yearly" : {
                //sim.put("InterestRate",Double.parseDouble(df.format(interest*100)));
                mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

                sim.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : "+mensuality);
                System.out.println("amount : "+amount);
                sim.put("CRD1", crd);
                System.out.println("CRD 1 :"+Double.parseDouble(df.format(crd)));
                sim.put("I1", crd * interest);
                sim.put("P1",mensuality-(crd*interest));

                for (int i=2;i<=period;i++){
                    crd = crd - (mensuality-(crd*interest));
                    sim.put("CRD"+i, crd);
                    System.out.println("CRD "+i+" : "+Double.parseDouble(df.format(crd)));
                }
            }break;
            default: {System.out.println("Invalid Type");}
        }


        return sim;
    }*/
   @Override
   public List<Object> Simulation(double amount, int period, String typePeriod) {
       List<Object> sim = new ArrayList<Object>();
       double mensuality;
       double crd;
       crd = amount;
       double interest= 6.25 + calculateInterest(score(amount,period,typePeriod));//score personel manquant
       sim.add(interest);
       interest=interest/100;
       System.out.println("interest "+ interest );
       switch (typePeriod){
           case "Monthly":{
               interest = Math.pow(1+interest,(1d/12))-1;
               // sim.put("InterestRate",Double.parseDouble(df.format(interest*100)));
               System.out.println("interest "+ interest );
               mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

              // sim.put("Mensuality",Double.parseDouble(df.format(mensuality)));
               System.out.println("Mensuality : "+Double.parseDouble(df.format(mensuality)));
               System.out.println("amount : "+amount);
              /* sim.put("CRD1", crd);
               sim.put("I1", crd * interest);
               sim.put("P1",mensuality-(crd*interest));*/
               Hashtable<String,Object> p = new Hashtable<String,Object>();
               p.put("CRD",crd);
               p.put("I", crd * interest);
               p.put("P",mensuality-(crd*interest));
               p.put("Mensuality",Double.parseDouble(df.format(mensuality)));
               sim.add(p);
               //  System.out.println("CRD 1 :"+Double.parseDouble(df.format(crd)));
               // System.out.println("CRD 1 :"+df.format(crd));

               for (int i=2;i<=period;i++){
                   Hashtable<String,Object> p1 = new Hashtable<String,Object>();
                   double interet = crd * interest;
                   p1.put("I",interet);
                   double principal = mensuality - interet;
                   p1.put("P",principal);
                   crd = crd - principal;
                   p1.put("CRD", Double.parseDouble(df.format(crd)));
                   System.out.println("CRD "+i+" : "+Double.parseDouble(df.format(crd)));
                   p1.put("Mensuality",Double.parseDouble(df.format(mensuality)));
                   sim.add(p1);
                   //System.out.println(sim.get(1));
               }
           }break;
           case "Half-Yearly":{
               interest = Math.pow(1+interest,1d/2)-1;
               // sim.put("InterestRate",Double.parseDouble(df.format(interest*100)));
               System.out.println("interest "+ interest );
               mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

               Hashtable<String,Object> p = new Hashtable<String,Object>();
               p.put("CRD",crd);
               p.put("I", crd * interest);
               p.put("P",mensuality-(crd*interest));
               p.put("Mensuality",Double.parseDouble(df.format(mensuality)));
               sim.add(p);

               for (int i=2;i<=period;i++){
                   Hashtable<String,Object> p1 = new Hashtable<String,Object>();
                   double interet = crd * interest;
                   p1.put("I",interet);
                   double principal = mensuality - interet;
                   p1.put("P",principal);
                   crd = crd - principal;
                   p1.put("CRD", Double.parseDouble(df.format(crd)));
                   System.out.println("CRD "+i+" : "+Double.parseDouble(df.format(crd)));
                   p1.put("Mensuality",Double.parseDouble(df.format(mensuality)));
                   sim.add(p1);
               }
           }break;
           case "Quarterly" : {
               interest = Math.pow(1+interest,1d/4)-1;
               // sim.put("InterestRate",Double.parseDouble(df.format(interest*100)));
               System.out.println("interest "+ interest );
               mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

               Hashtable<String,Object> p = new Hashtable<String,Object>();
               p.put("CRD",crd);
               p.put("I", crd * interest);
               p.put("P",mensuality-(crd*interest));
               p.put("Mensuality",Double.parseDouble(df.format(mensuality)));
               sim.add(p);

               for (int i=2;i<=period;i++){
                   Hashtable<String,Object> p1 = new Hashtable<String,Object>();
                   double interet = crd * interest;
                   p1.put("I",interet);
                   double principal = mensuality - interet;
                   p1.put("P",principal);
                   crd = crd - principal;
                   p1.put("CRD", Double.parseDouble(df.format(crd)));
                   System.out.println("CRD "+i+" : "+Double.parseDouble(df.format(crd)));
                   p1.put("Mensuality",Double.parseDouble(df.format(mensuality)));
                   sim.add(p1);
               }
           }break;
           case "Yearly" : {
               //sim.put("InterestRate",Double.parseDouble(df.format(interest*100)));
               mensuality = (amount*interest)/(1-Math.pow(1+interest,-period));

               Hashtable<String,Object> p = new Hashtable<String,Object>();
               p.put("CRD",crd);
               p.put("I", crd * interest);
               p.put("P",mensuality-(crd*interest));
               p.put("Mensuality",Double.parseDouble(df.format(mensuality)));
               sim.add(p);

               for (int i=2;i<=period;i++){
                   Hashtable<String,Object> p1 = new Hashtable<String,Object>();
                   double interet = crd * interest;
                   p1.put("I",interet);
                   double principal = mensuality - interet;
                   p1.put("P",principal);
                   crd = crd - principal;
                   p1.put("CRD", Double.parseDouble(df.format(crd)));
                   System.out.println("CRD "+i+" : "+Double.parseDouble(df.format(crd)));
                   p1.put("Mensuality",Double.parseDouble(df.format(mensuality)));
                   sim.add(p1);

               }
           }break;
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
            interest = 4;
        else if ((score>500)&&(score<=1000))
            interest = 3.5;
        else if ((score>1000)&&(score<=1300))
            interest = 3;
        else if ((score>1300)&&(score<=1500))
            interest = 2.5;
        else if ((score>1500)&&(score<=1800))
            interest = 2;
        else if ((score>1800)&&(score<=2000))
            interest = 1.5;

        return interest;
    }

    @Override
    public Hashtable<String, Double> FailureToPay(long idCredit,int period, double interestAmount) {
        Hashtable<String, Double> newCredit = new Hashtable<String, Double>();
        MicroCredit credit = retrieveCredit(idCredit);
        double mensuality;
        double crd =((Hashtable<String,Double>)  Simulation(credit.getAmountCredit(),credit.getPeriod(),credit.getTypePeriod()).get(period)).get("CRD");
        int newPeriod =(int) (credit.getAmountRemaining()/credit.getPayedAmount()) + 3;
        System.out.println("period : "+ newPeriod);
        // int newPeriod = credit.getPeriod()+3;//ajout period selon type(calcul de periode restante)
        double newAmount = crd+interestAmount;
        //score personel manquant
        double interestRate =6.25 + calculateInterest(score(newAmount,newPeriod,credit.getTypePeriod()));
        interestRate=interestRate/100;

        switch (credit.getTypePeriod()){
            case "Monthly":{
                interestRate = Math.pow(1+interestRate,(1d/12))-1;
                System.out.println("interest "+ interestRate );
                mensuality = (newAmount*interestRate)/(1-Math.pow(1+interestRate,-newPeriod));

                newCredit.put("Mensuality",Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : "+mensuality);
                crd = newAmount;
                newCredit.put("CRD1", Double.parseDouble(df.format(crd)));
                System.out.println("CRD 1 :"+Double.parseDouble(df.format(crd)));
                newCredit.put("I1", crd * interestRate);
                newCredit.put("P1",mensuality-(crd*interestRate));

                for (int i=2;i<=newPeriod;i++){
                    double interet = crd * interestRate;
                    newCredit.put("I"+i,interet);
                    double principal = mensuality - interet;
                    newCredit.put("P"+i,principal);
                    crd = crd - principal;
                    newCredit.put("CRD"+i, Double.parseDouble(df.format(crd)));
                    System.out.println("CRD "+i+" : "+crd);
                }
            }break;
            case "Half-Yearly":{
                interestRate = Math.pow(1+interestRate,1d/2)-1;
                System.out.println("interest "+ interestRate );
                mensuality = (newAmount*interestRate)/(1-Math.pow(1+interestRate,-newPeriod));
                newCredit.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : "+mensuality);

                newCredit.put("CRD1", Double.parseDouble(df.format(crd)));
                System.out.println("CRD 1 :"+crd);
                newCredit.put("I1", crd * interestRate);
                newCredit.put("P1",mensuality-(crd*interestRate));

                for (int i=2;i<=newPeriod;i++){
                    double interet = crd * interestRate;
                    newCredit.put("I"+i,interet);
                    double principal = mensuality - interet;
                    newCredit.put("P"+i,principal);
                    crd = crd - principal;
                    newCredit.put("CRD"+i, Double.parseDouble(df.format(crd)));
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            case "Quarterly" : {
                interestRate = Math.pow(1+interestRate,1d/4)-1;
                System.out.println("interest "+ interestRate );
                mensuality = (newAmount*interestRate)/(1-Math.pow(1+interestRate,-newPeriod));
                newCredit.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : "+mensuality);

                newCredit.put("CRD1", Double.parseDouble(df.format(crd)));
                System.out.println("CRD 1 :"+crd);
                newCredit.put("I1", crd * interestRate);
                newCredit.put("P1",mensuality-(crd*interestRate));

                for (int i=2;i<=newPeriod;i++){
                    double interet = crd * interestRate;
                    newCredit.put("I"+i,interet);
                    double principal = mensuality - interet;
                    newCredit.put("P"+i,principal);
                    crd = crd - principal;
                    newCredit.put("CRD"+i, Double.parseDouble(df.format(crd)));
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            case "Yearly" : {
                mensuality = (newAmount*interestRate)/(1-Math.pow(1+interestRate,-newPeriod));
                newCredit.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : "+mensuality);

                newCredit.put("CRD1", Double.parseDouble(df.format(crd)));
                System.out.println("CRD 1 :"+crd);
                newCredit.put("I1", crd * interestRate);
                newCredit.put("P1",mensuality-(crd*interestRate));

                for (int i=2;i<=newPeriod;i++){
                    double interet = crd * interestRate;
                    newCredit.put("I"+i,interet);
                    double principal = mensuality - interet;
                    newCredit.put("P"+i,principal);
                    crd = crd - principal;
                    newCredit.put("CRD"+i, Double.parseDouble(df.format(crd)));
                    System.out.println("CRD "+i+" : "+crd);
                }}break;
            default: {System.out.println("Invalid Type");}
        }

        return newCredit;
    }

    @Override
    public int CapacityToPay(long idCredit, double newAmount) {
        int period =0;
        MicroCredit credit = retrieveCredit(idCredit);
        double amount =((Hashtable<String,Double>) Simulation(credit.getAmountCredit(),credit.getPeriod(),credit.getTypePeriod()).get(1)).get("Mensuality");
        System.out.println("Mensuality : " + amount);
        int p =(int) ((credit.getAmountCredit()-credit.getAmountRemaining())/credit.getPayedAmount());
        System.out.println("p : "+p);
        if(newAmount>=amount){
            // period = (int)((Math.log(newAmount/(newAmount-credit.getAmountRemaining()*credit.getInterestRate()))/Math.log(10))/(Math.log(1+credit.getInterestRate())/Math.log(10)));
            double i = Math.pow(1+(credit.getInterestRate()/100),(1d/12))-1;
            double crd =((Hashtable<String,Double>) Simulation(credit.getAmountCredit(),credit.getPeriod(),credit.getTypePeriod()).get(p+1)).get("CRD");
            System.out.println("crd : "+ crd);
            double a = Math.log((newAmount-(crd*i))/newAmount)/Math.log(10);
            System.out.println("a : " + a);
            double b = Math.log(1/(1+i))/Math.log(10);
            System.out.println("b : " + b);
            period = (int) (a / b);
            System.out.println("Period : " + period);
        }
        return period;
    }

    @Override
    public List<MicroCredit> getCreditsByUserId(Long idUser) {
        return creditRepo.retrieveCredetsByUserId(idUser);
    }

    @Override
    public MicroCredit updateCreditStatus(MicroCredit c,Long idAccount,CreditStatus status) {
        Account ac = accountRepo.findById(idAccount).get();
        Long id = c.getIdCredit();
        if (creditRepo.findById(id).isPresent()) {
            c.setAccountFK(ac);
            c.setStatus(status);
            return creditRepo.save(c);
        } else {
            System.out.println("credit doesn't exist !");
            return null;
        }
    }
}
