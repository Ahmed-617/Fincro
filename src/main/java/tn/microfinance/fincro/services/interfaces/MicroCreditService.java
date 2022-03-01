package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.MicroCredit;

import java.util.List;

public interface MicroCreditService {

    List<MicroCredit> retrieveAllCredits();

    MicroCredit addCredit(MicroCredit c);

    void deleteCredit(Long id);

    MicroCredit updateCredit(MicroCredit c);

    MicroCredit retrieveCredit(Long id);
}
