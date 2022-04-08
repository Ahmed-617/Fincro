package tn.microfinance.fincro.services.implementations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.CaseInsurance;
import tn.microfinance.fincro.dao.model.ClaimType;
import tn.microfinance.fincro.dao.model.User;
import tn.microfinance.fincro.dao.repositories.*;
import tn.microfinance.fincro.services.interfaces.AccountService;
import tn.microfinance.fincro.services.interfaces.CaseInsuranceService;

import java.util.Date;
import java.util.List;
@Service
public class CaseInsuranceImpl implements CaseInsuranceService {

@Autowired
    CaseInsuranceRepository caseInsuranceRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClaimRepository claimRepository;

    @Autowired
    UserRepository employeeRepository;

    @Autowired
    InsurenceContractRepository insurenceContractRepository;


    @Override
    public CaseInsurance addCase(CaseInsurance ci) {
      return caseInsuranceRepository.save(ci);
    }

    @Override
    public void deleteCase(Long id) {
        caseInsuranceRepository.deleteById(id);

    }


    @Override
    public CaseInsurance retrieveCase(Long id) {
        return caseInsuranceRepository.findById(id).get();
    }

    public CaseInsuranceRepository getCaseRepository() { return caseInsuranceRepository; }

    public void setCaseRepository(CaseInsuranceRepository caseRepository) { this.caseInsuranceRepository = caseRepository; }


    private static final Logger l = (Logger) LogManager.getLogger(CaseInsuranceService.class);


    @Override
    public List<CaseInsurance> retrieveAllCases(){
        List<CaseInsurance> Cases = (List<CaseInsurance>) caseInsuranceRepository.findAll();
        for (CaseInsurance Case : Cases){
            l.info("Case +++ :"+ Case);
        }
        return Cases;
    }

    @Override
    public CaseInsurance addCase(CaseInsurance c,Integer idClaim){
        c.setFkClaim(claimRepository.findById(Long.valueOf(idClaim)).get());
        return caseInsuranceRepository.save(c);
    }




    @Override
    public CaseInsurance updateCase(CaseInsurance u){
        Integer t = u.getIdCase();
        if (caseInsuranceRepository.findById(Long.valueOf(t)).isPresent()){
            return caseInsuranceRepository.save(u);
        }else{
            return null;
        }
    }


    @Override
    public CaseInsurance retrieveCase(Integer id){
        return caseInsuranceRepository.findById(Long.valueOf(id)).get();

    }

    @Override
    public void setBenefits(Integer idCase){	//Fonction de la mise a jour de l'annuité    INFERIEUR A 3 A
        // MODIFIER SELON CONTEXTE et AFFECTER annuité RESTANT A 0 et condition sur la date d'expiration et status a 0

        CaseInsurance c = new CaseInsurance();
        c = caseInsuranceRepository.findById(Long.valueOf(idCase)).get();
        ClaimType claimty = c.getFkClaim().getClaimType();
        Date today = new Date();
        if(c.getFkContract().getDueDateContract().after(today) && c.getStatus()==0){
            if(claimRepository.getCountClaimType(claimty)<3){
                c.setBenefits(c.getFkContract().getFkInsuredProperty().getPropertyValue()*0.7);
                c.setRemainingBenefits(0.0);
                c.setStatus(1);
                caseInsuranceRepository.save(c);
            }else{
                c.setBenefits(c.getFkContract().getFkInsuredProperty().getPropertyValue()*0.6);
                c.setRemainingBenefits(0.0);
                c.setStatus(1);
                caseInsuranceRepository.save(c);
            }
        }else{
            System.out.println("Contrat expiré OU Dossier Expiré/En Cours");
        }
    }

    public void setBenefitsType(Integer idCase){
        CaseInsurance c = new CaseInsurance();
        c = caseInsuranceRepository.findById(Long.valueOf(idCase)).get();
        Account b = accountRepository.findById(c.getFkContract().getFkClient().getFkBalance().getIdBalance()).get();
        Double temp = c.getBenefits();
        float amount ;
        if (c.getStatus()==1){
            if (caseInsuranceRepository.getCountContractPerCase(c.getFkContract())<3){
                c.setBenefitsType(1);
                amount = (float) (temp*0.7);
                c.setRemainingBenefits(temp*0.3);
                AccountService.addAmount(amount,b);
                //emailService.sendEmailAmountReceived(c.getFkContract().getFkClient().getMailClient(), b.getAmount());
            }else{
                amount = (float) (temp*0.5);
                c.setRemainingBenefits(temp*0.5);
                c.setBenefitsType(2);
                AccountService.addAmount(amount,b);
               // emailService.sendEmailAmountReceived(c.getFkContract().getFkClient().getMailClient(), b.getAmount());
            }
            caseInsuranceRepository.save(c);
        }else{
            System.out.println("Impossible");
        }
    }
    @SuppressWarnings("deprecation")
    public void affectRemainingBenefits(Integer idCase){
        CaseInsurance c = new CaseInsurance();
        User client = new User();
        c = caseInsuranceRepository.findById(Long.valueOf(idCase)).get();
        client = employeeRepository.findById(c.getFkContract().getFkClient().getIdClient()).get();
        Account b = accountRepository.findById(c.getFkContract().getFkClient().getFkBalance().getIdBalance()).get();
        Date today = new Date();
        int tempMonth = today.getMonth() - c.getFkClaim().getClaimDate().getMonth();
        int tempYear = today.getYear() - c.getFkClaim().getClaimDate().getYear();
        if(c.getStatus()==1 && c.getBenefitsType()!=null){
            if (client.getSalary()<600.0){
                AccountService.addAmount((float) c.getRemainingBenefits(),b);
                c.setRemainingBenefits(0.0);
                c.setStatus(2);
                caseInsuranceRepository.save(c);
                //emailService.sendEmailAmountReceived(c.getFkContract().getFkClient().getMailClient(), b.getAmount());
            }else{
                if (tempMonth>2 || tempYear>=1){
                    AccountService.addAmount((float) c.getRemainingBenefits(),b);
                    c.setRemainingBenefits(0.0);
                    c.setStatus(2);
                    caseInsuranceRepository.save(c);
                   // emailService.sendEmailAmountReceived(c.getFkContract().getFkClient().getMailClient(), b.getAmount());
                }else{
                    System.out.println("Trop tot pour lui rendre l'argent");
                }
            }
        }else{
            System.out.println("Le status du dossier est erroné ou pas de beneficeTypes");
        }
    }
    public void refuseCase(Integer idCase){

        CaseInsurance c = new CaseInsurance();
        c = caseInsuranceRepository.findById(Long.valueOf(idCase)).get();
        if(c.getFkContract()!=null){
            if(c.getStatus()!=2){
                c.setBenefits(0.0);
                c.setRemainingBenefits(0.0);
                c.setStatus(2);
                c.setBenefitsType(0);
                caseInsuranceRepository.save(c);
                //emailService.sendEmailRefuseCase(c.getFkContract().getFkClient().getMailClient());
            }else{
                System.out.println("Case already completed");
            }
        }else{
            System.out.println("Affecter d'abord un contrat");
        }

    }
    @Override
    public List<CaseInsurance> retrieveAllUncompletedCases(){
        List<CaseInsurance> Cases = (List<CaseInsurance>) caseInsuranceRepository.getAllUncompletedCases();
        for (CaseInsurance Case : Cases){
            l.info("Case +++ :"+ Case);
        }
        return Cases;
    }


    //Fonction pour retrouver touts les dossiers COMPLETS
    @Override
    public List<CaseInsurance> retrieveAllCompletedCases(){
        List<CaseInsurance> Cases = (List<CaseInsurance>) caseInsuranceRepository.getAllCompletedCases();
        for (CaseInsurance Case : Cases){
            l.info("Case +++ :"+ Case);
        }
        return Cases;
    }

    //Fonction pour retrouver touts les dossiers EN ATTENTE
    @Override
    public List<CaseInsurance> retrieveAllWaitingCases(){
        List<CaseInsurance> Cases = (List<CaseInsurance>) caseInsuranceRepository.getAllWaitingCases();
        for (CaseInsurance Case : Cases){
            l.info("Case +++ :"+ Case);
        }
        return Cases;
    }

    //AFFECTER un EMPLOYE a un dossier
    public void affectEmployeeToCase(Integer idCase,Integer idEmploye){
        CaseInsurance cases = new CaseInsurance();
        cases = caseInsuranceRepository.findById(Long.valueOf(idCase)).get();
        cases.setFkEmployee(employeeRepository.findById(idEmploye).get());
        caseInsuranceRepository.save(cases);
    }


    //AFFECTER un CONTRAT a un dossier
    public void affectContractToCase(Integer idCase,Integer idContrat){
        CaseInsurance cases = new CaseInsurance();
        cases = caseInsuranceRepository.findById(Long.valueOf(idCase)).get();
        cases.setFkContract(insurenceContractRepository.findById(idContrat).get());
        caseInsuranceRepository.save(cases);
    }

}
