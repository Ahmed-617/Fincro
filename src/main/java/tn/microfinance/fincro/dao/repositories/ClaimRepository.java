package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.microfinance.fincro.dao.model.Claim;
import tn.microfinance.fincro.dao.model.ClaimType;

import java.util.List;

@Repository
public interface ClaimRepository extends CrudRepository<Claim,Long> {
    @Query("Select DISTINCT u from Claim u WHERE u.visibility=TRUE ")
    public List<Claim> getAllClaims();


    @Query("Select count(u) from Claim u WHERE u.claimType= ?1")
    public int getCountClaimType(ClaimType claimType);
}
