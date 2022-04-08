package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.services.implementations.UserService;
import tn.microfinance.fincro.services.interfaces.AccountService;

import java.util.List;

@RestController
public class AccountRestController {
    @Autowired
            //
    AccountService accountService;
    @Autowired
    UserService userService;

    @GetMapping("getAllAccounts")
    public List<Account> getAccounts(){
        return accountService.retrieveAllAccounts();
    }

    @GetMapping("getAccount/{id}")
    public Account getAccoutById(@PathVariable("id") Long id){
        return accountService.retrieveAccount(id);
    }

    @PostMapping("addAccount/{id}")
    public Account addAccount(@RequestBody Account account,@PathVariable("id") Long id){
        account.setUser(userService.findUserById(id));
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

    @GetMapping("getBalance/{id}")
    public double getBalance(@PathVariable("id") Long id){return accountService.getBalance(id);}

    @PutMapping("verifictionCode/{id}/{code}")
    public void verifCode(@PathVariable("id") Long id, @PathVariable("code") String code){
        accountService.setAleaCode(id,code);
    }
}
