package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.InsuredProperty;

public interface IPropertyService {
    public InsuredProperty addInsuredProperty(InsuredProperty ip);
    public void deleteInsuredProperty(String id);
    public InsuredProperty updateInsuredProperty(InsuredProperty ip);
    public InsuredProperty retrieveInsuredProperty(String id);
}
//