package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    public User findByCin( int  cin );

}
