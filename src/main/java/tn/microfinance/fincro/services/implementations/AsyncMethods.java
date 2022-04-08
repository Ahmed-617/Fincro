package tn.microfinance.fincro.services.implementations;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.repositories.AccountRepository;
import tn.microfinance.fincro.services.interfaces.TransactionService;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class AsyncMethods {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionService transactionService;
    @Async
    public void verifTransferMoney(Transaction t) throws InterruptedException {
        String generatedString = RandomString.make(8);
        Account senderAccount = accountRepository.findById(t.getSenderAccountId()).get();
        transactionService.sendEmail(senderAccount.getUser().getEmail(),"Vérification Code","Your are about to make a transaction, your vérification code is : "+generatedString);
        System.out.println("clear 1 !");
        senderAccount.setAleaCode(null);
        accountRepository.save(senderAccount);
        System.out.println(senderAccount.getAleaCode() == null);
        for (int i =0; i<=60 ; i++){
            System.out.println("code in async  : "+accountRepository.findById(senderAccount.getIdAccount()).get().getAleaCode());
            if(!(accountRepository.findById(senderAccount.getIdAccount()).get().getAleaCode() == null)){
                System.out.println("I'm breaking !");
                break;}
            Thread.sleep(10000);
            System.out.println("counter : "+i);
        }
        if (!(accountRepository.findById(senderAccount.getIdAccount()).get().getAleaCode()==  null)) {
            if (accountRepository.findById(senderAccount.getIdAccount()).get().getAleaCode().equals(generatedString)) {
                transactionService.makeScheduledTransaction(t);
                System.out.println("transaction Made ");
                senderAccount.setAleaCode(null);
                accountRepository.save(senderAccount);
            } else {
                throw new UnsupportedOperationException("Le code de vérification est invalide !!");
            }
        }


    }
}
