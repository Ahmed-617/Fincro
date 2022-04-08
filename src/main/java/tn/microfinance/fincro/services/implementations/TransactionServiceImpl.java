package tn.microfinance.fincro.services.implementations;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.voice.Sms;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.Configuration.TwilioConfiguration;
import tn.microfinance.fincro.dao.model.*;
import tn.microfinance.fincro.dao.repositories.AccountRepository;
import tn.microfinance.fincro.dao.repositories.TransactionRepository;
import tn.microfinance.fincro.services.interfaces.TransactionService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    AsyncMethods asyncMethods;


    @Override
    public List<Transaction> retrieveAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    @Override
    public Transaction addTransaction(Transaction t) {
        return transactionRepository.save(t);
    }

    @Override
    public void transferMoney(Transaction t) throws InterruptedException {
        Account senderAccount = accountRepository.findById(t.getSenderAccountId()).get();
        if (senderAccount.getBalance()>t.getAmount()){
            if (senderAccount.isSecured()){
                log.info("waiting for verification");
                asyncMethods.verifTransferMoney(t);

            }else{
            makeScheduledTransaction(t);}
        }
        else throw new UnsupportedOperationException("Le crédit de l envoyeur n est pas suffisant !!");
    }



    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }

    @Override
    public Transaction retrieveTransaction(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public Calendar scheduleTransactions(Transaction transaction, int rep) {
        Date date = transaction.getTransactionDate() ;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (transaction.getTransactionScheme()){
            case "Daily":
                for (int i=0;i<rep;i++){
                    System.out.println(c.getTime());
                    transaction.setTransactionDate(c.getTime());
                    System.out.println(transaction.getTransactionDate());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(c.getTime());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.DATE,1);

                }
                break;
            case "Weekly":
                for (int i=0;i<rep;i++){
                    transaction.setTransactionDate(c.getTime());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(transaction.getTransactionDate());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.DATE,7);
                }
                break;
            case "Monthly":
                for (int i=0;i<rep;i++){
                    transaction.setTransactionDate(c.getTime());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(transaction.getTransactionDate());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.MONTH,1);
                }
                break;
            case "Quarterly":
                for (int i=0;i<rep;i++){
                    transaction.setTransactionDate(c.getTime());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(transaction.getTransactionDate());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.MONTH,3);
                }
                break;
            case "Half-Yearly":
                for (int i=0;i<rep;i++){
                    transaction.setTransactionDate(c.getTime());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(transaction.getTransactionDate());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.MONTH,6);
                }
                break;
            case "Yearly":
                for (int i=0;i<rep;i++){
                    transaction.setTransactionDate(c.getTime());
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionScheme(transaction.getTransactionScheme());
                    newTransaction.setTransactionType(transaction.getTransactionType());
                    newTransaction.setTransactionDate(transaction.getTransactionDate());
                    newTransaction.setSenderAccountId(transaction.getSenderAccountId());
                    newTransaction.setReceiverAccountId(transaction.getReceiverAccountId());
                    newTransaction.setAmount(transaction.getAmount());
                    transactionRepository.save(newTransaction);
                    c.add(Calendar.YEAR,1);
                }
                break;
            default:
                break;
            }
            return c;

    }

    @Override
    public List<Transaction> getTransactionByType(TransactionType transactionType) {
        return transactionRepository.findTransactionByTransactionType(transactionType);
    }

    @Override
    public void makeScheduledTransaction(Transaction transaction) {
        Transaction receiverTransaction = transaction;
        Transaction senderTransaction = new Transaction();
        Account senderAccount = accountRepository.findById(receiverTransaction.getSenderAccountId()).get();
        Account receiverAccount = accountRepository.findById(receiverTransaction.getReceiverAccountId()).get();
        senderTransaction.setAmount(receiverTransaction.getAmount());
        senderTransaction.setTransactionType(TransactionType.Transfer);
        senderTransaction.setTransactionScheme("Normal");
        senderTransaction.setTransactionDate(receiverTransaction.getTransactionDate());
        senderTransaction.setAccount(senderAccount);
        senderTransaction.setReceiverAccountId(receiverTransaction.getReceiverAccountId());
        transactionRepository.save(senderTransaction);

        receiverTransaction.setTransactionType(TransactionType.Income);
        receiverTransaction.setTransactionScheme("Normal");
        receiverTransaction.setAccount(receiverAccount);
        transactionRepository.save(receiverTransaction);

        senderAccount.setBalance(senderAccount.getBalance()- receiverTransaction.getAmount());
        receiverAccount.setBalance(receiverAccount.getBalance()+ receiverTransaction.getAmount());
        sendSms(new SmsRequest(senderAccount.getUser().getPhoneNumber().toString(),
                "Une transaction de "+receiverTransaction.getAmount()+" DT a été effectuée au favoris de Mr/Mme " + receiverAccount.getUser().getLastName()+" " + receiverAccount.getUser().getFirstName()+" . Votre solde restant est de "+senderAccount.getBalance()+" DT."));
        sendSms(new SmsRequest(receiverAccount.getUser().getPhoneNumber().toString(),
                "Vous avez reçu un montant de "+receiverTransaction.getAmount()+" DT de la part de Mr/Mme "+ senderAccount.getUser().getLastName()+ " "+senderAccount.getUser().getFirstName()+" . Votre solde total est de "+receiverAccount.getBalance()+" DT."));
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
        }

    @Override
    public void executeScheduledTransactions() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,3);
        Date smsDate = c.getTime();

        List<Transaction> scheduledTransacions =  transactionRepository.findTransactionByTransactionType(TransactionType.Scheduled);
        System.out.println(sdf.format(date));

        for (Transaction tran : scheduledTransacions){
            System.out.println(sdf.format(tran.getTransactionDate()));
            if (sdf.format(date).equals(sdf.format(tran.getTransactionDate()))){
                makeScheduledTransaction(tran);
            }
            else if (sdf.format(smsDate).equals(sdf.format(tran.getTransactionDate()))){
                Account senderAccount = accountRepository.findById(tran.getSenderAccountId()).get();
                Account receiverAccount = accountRepository.findById(tran.getReceiverAccountId()).get();
                sendSms(new SmsRequest(senderAccount.getUser().getPhoneNumber().toString(),
                        "Une transaction de "+tran.getAmount()+" DT sera effectuée après 3 jours au favoris de Mr/Mme "+receiverAccount.getUser().getLastName()+" "+receiverAccount.getUser().getFirstName()+". Assurez-vous que votre solde est supérieur au montant demandé ou vous serez pénaliser"));
            }

        }
    }

    @Override
    public List<Transaction> findAccountTransByType(TransactionType transactionType, long accountId) {
        return transactionRepository.findAccountTransactionsByType(transactionType,accountId);
    }

    @Override
    public int getTransNbreByAccountAndType(TransactionType transactionType, long accountId) {
        return transactionRepository.getTransactionsNbreByAccountAndType(transactionType,accountId);
    }

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TransactionServiceImpl(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        Message.creator(new PhoneNumber("+216"+smsRequest.getPhoneNumber()),new PhoneNumber(twilioConfiguration.getTrialNumber()),smsRequest.getMessage()).create();

    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fincro.service@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Scheduled(cron = "20 51 13 * * *")
    public void executeSchedTrans(){
        executeScheduledTransactions();
    }

}
