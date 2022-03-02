package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.services.interfaces.AccountService;
import tn.microfinance.fincro.services.interfaces.TransactionService;

import java.util.List;

@RestController
public class TransactionRestController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("getAllTransactions")
    public List<Transaction> getTransactions(){
        return transactionService.retrieveAllTransactions();
    }

    @GetMapping("getTransaction/{id}")
    public Transaction getTransactionById(@PathVariable("id") Integer id){
        return transactionService.retrieveTransaction(id);
    }

    @PostMapping("addTransaction")
    public Transaction addAccount(@RequestBody Transaction transaction){
        return transactionService.addTransaction(transaction);
    }


    @DeleteMapping("deleteTransaction/{id}")
    public void deleteTransaction(@PathVariable("id") Integer id){
        transactionService.deleteTransaction(id);
    }
}
