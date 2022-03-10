package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.services.interfaces.AccountService;

import java.util.List;

@RestController
public class AccountRestController {
    @Autowired
    AccountService accountService;

    @GetMapping("getAllAccounts")
    public List<Account> getAccounts(){
        return accountService.retrieveAllAccounts();
    }

    @GetMapping("getAccount/{id}")
    public Account getAccoutById(@PathVariable("id") Long id){
        return accountService.retrieveAccount(id);
    }

    @PostMapping("addAccount")
    public Account addAccount(@RequestBody Account account){
        return accountService.addAccount(account);
    }

    @PutMapping("updateAccount")
    public Account updateAccount(@RequestBody Account account){
        return accountService.updateAccount(account);
    }

    @DeleteMapping("deleteAccount/{id}")
    public void deleteAccount(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
    }
}
