package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.Account;
import tn.microfinance.fincro.dao.model.FileEntity;

@Repository
public interface FileEntityRepository extends CrudRepository<FileEntity,Long> {

}
