package tn.microfinance.fincro.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.microfinance.fincro.dao.model.MicroCredit;

public interface MicroCreditRepository extends CrudRepository<MicroCredit,Long> {
}
