package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByPhoneNumber(String phoneNumber);

    User findUserByEmail(String email);

    List<User> findAllByScoreIsGreaterThanEqual(int score);

    List<User> findAllByGuarantorSalaryIsGreaterThanEqual(int salary);
    User findByCin( int  cin );
}
