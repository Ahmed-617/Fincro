package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.Claim;
import tn.microfinance.fincro.dao.model.MicroCredit;

import java.util.List;

public interface ClaimService {


    List<Claim> retrieveAllClaims();

    Claim addClaim(Claim cl);

    void deleteClaim(Long id);

    Claim updateClaim(Claim cl);

    Claim retrieveClaim(Long id);
}
