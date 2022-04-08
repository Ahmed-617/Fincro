package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.User;
@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findUserByPhoneNumber(String phoneNumber);

    User findUserByEmail(String email);

    List<User> findAllByScoreIsGreaterThanEqual(int score);
    User findByCin( int  cin );
    List<User> findAllByGuarantorSalaryIsGreaterThanEqual(int salary);
}
