package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.microfinance.fincro.dao.model.User;
import tn.microfinance.fincro.services.interfaces.UserService;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    UserService userService;
    @GetMapping("getAllUsers")
    public List<User>getAllUsers(){
        return userService.retrieveAllUsers();
    }

}
