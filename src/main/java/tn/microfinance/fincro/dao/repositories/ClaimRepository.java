package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.Claim;
@Repository
public interface ClaimRepository extends CrudRepository<Claim,Long> {
}
