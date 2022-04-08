package tn.microfinance.fincro.services.implementations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.Claim;
import tn.microfinance.fincro.dao.repositories.ClaimRepository;
import tn.microfinance.fincro.services.interfaces.ClaimService;

import java.util.List;
@Service
public class ClaimServiceImpl implements ClaimService {
    @Autowired
    ClaimRepository claimRepository;



    public ClaimRepository getClaimRepository() {
        return claimRepository; }

    public void setClaimRepository(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository; }


    private static final Logger l = (Logger) LogManager.getLogger(ClaimServiceImpl.class);

    @Override
    public List<Claim> retrieveAllClaims(){
        List<Claim> claims = (List<Claim>) claimRepository.getAllClaims();
        for (Claim claim : claims){
            l.info("Claim +++ :"+ claim);
        }
        return claims;
    }

    @Override
    public Claim addClaim(Claim cl) {
        return claimRepository.save(cl);
    }

    @Override
    public void deleteClaim(Long id) {
        claimRepository.deleteById(id);
    }

    @Override
    public Claim updateClaim(Claim cl) {
        return claimRepository.save(cl);
    }

    @Override
    public Claim retrieveClaim (Long id) {
        return claimRepository.findById(id).get();
    }
}

