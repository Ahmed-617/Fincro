package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.User;
import tn.microfinance.fincro.dao.repositories.UserRepository;
import tn.microfinance.fincro.services.interfaces.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
@Autowired
    UserRepository userRepository;

    @Override
    public List<User> retrieveAllUsers() {
        return null;
    }

    @Override
    public User addUser(User u) {
        return userRepository.save(u);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);

    }

    @Override
    public User upadateUser(User u) {
        return null;
    }

    @Override
    public User retrieveUser(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public void updateUserPasswordById(String password, int id) {

    }

    @Override
    public void updateUser(User u) {

    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public void addUserGoogle(String email, String s) {

    }
}
