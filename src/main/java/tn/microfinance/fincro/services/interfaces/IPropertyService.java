package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.InsuredProperty;
import tn.microfinance.fincro.dao.model.PropertyType;
import tn.microfinance.fincro.dao.repositories.InsuredPropertyRepository;

import java.util.List;

public interface IPropertyService {
    public InsuredPropertyRepository getInsuredProperty();

    public void setInsuredProperty(InsuredPropertyRepository InsuredProperty);

    public List<InsuredProperty> retrieveAllVisibleInsuredProperties();

    public List<InsuredProperty> retrieveNotVisibleInsuredProperties();

    public InsuredProperty addInsuredProperty(InsuredProperty ip);

    public void deleteInsuredProperty(String id);

    public InsuredProperty updateInsuredProperty(InsuredProperty ip);

    public InsuredProperty retrieveInsuredProperty(String id);

    public void archiveInsuredProperty(InsuredProperty ip);

    public void affecterPropertyAContract(int propertyId, int contractId);

    public Double tauxInsuredProperty(PropertyType type);
}
