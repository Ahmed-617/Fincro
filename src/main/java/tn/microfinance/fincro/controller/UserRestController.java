package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.model.User;
import tn.microfinance.fincro.services.interfaces.UserService;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    UserService userService;
    @GetMapping("getAllUsers")
    public List<User>getAllUsers() {
        return userService.retrieveAllUsers();
    }
    @GetMapping("getUser/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.retrieveUser(id);
    }

    @PostMapping("getUsers")
    public User addUser(@RequestBody User user){
    return userService.addUser(user);
    }
   @DeleteMapping("deleteUser/{id}")
    public void deleteUser(@PathVariable("id") Integer id){
        userService.deleteUser(id);
   }

}









