package tn.microfinance.fincro.services.interfaces;

import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.User;

import java.util.List;

@Service
public interface UserService {
    List<User>retrieveAllUsers();
    User addUser(User u);
    void deleteUser(Integer id);
    User upadateUser(User u);
    User retrieveUser(Integer id);

    void deleteUserById(int id);

    void updateUserPasswordById(String password, int id);

    void updateUser(User u);

    User getUserById(int id);

    void addUserGoogle(String email, String s);
}
