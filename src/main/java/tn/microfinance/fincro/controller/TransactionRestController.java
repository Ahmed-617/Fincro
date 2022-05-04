package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.model.TransactionScheme;
import tn.microfinance.fincro.dao.model.TransactionType;
import tn.microfinance.fincro.services.interfaces.AccountService;
import tn.microfinance.fincro.services.interfaces.TransactionService;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class TransactionRestController {
    @Autowired
    TransactionService transactionService;

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("getAllTransactions")
    public List<Transaction> getTransactions(){
        return transactionService.retrieveAllTransactions();
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("getTransaction/{id}")
    public Transaction getTransactionById(@PathVariable("id") Long id){
        return transactionService.retrieveTransaction(id);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @PostMapping("addTransaction")
    public Transaction addAccount(@RequestBody Transaction transaction){
        return transactionService.addTransaction(transaction);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @PostMapping("scheduleTransactions/{rep}")
    public Calendar scheduleTransactions(@RequestBody Transaction transaction, @PathVariable("rep") int rep){
        return transactionService.scheduleTransactions(transaction,rep);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("executeScheduledTransactions")
    public void executeSchedTrans(){
        transactionService.executeScheduledTransactions();
    }


    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @DeleteMapping("deleteTransaction/{id}")
    public void deleteTransaction(@PathVariable("id") Long id){
        transactionService.deleteTransaction(id);
    }

    @DeleteMapping("deleteAllTransactions")
    public void deleteAllTransactions(){
        transactionService.deleteAllTransactions();
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("getUserTranactions/{type}/{id}")
    public List<Transaction> getTransactionsByType(@PathVariable("type") TransactionType transactionType, @PathVariable("id") Long id){
        return transactionService.findAccountTransByType(transactionType,id); }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("getNbreOfTrans/{type}/{id}")
    public int getNbreOfTrans(@PathVariable("type") TransactionType transactionType, @PathVariable("id") Long id){
        return transactionService.getTransNbreByAccountAndType(transactionType,id);
    }

    @CrossOrigin("http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('user:read')")
    @PostMapping("transferMoney")
    public void transferMoney(@RequestBody Transaction transaction) throws IllegalAccessException, InterruptedException {
        transactionService.transferMoney(transaction);
    }
}
