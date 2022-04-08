package tn.microfinance.fincro.services.implementations;

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


    @Override
    public List<Claim> retrieveAllClaims() {
        return (List<Claim>) claimRepository.findAll();
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
//
