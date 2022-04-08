package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.InsuredProperty;
import tn.microfinance.fincro.dao.model.PropertyType;
import tn.microfinance.fincro.dao.repositories.InsuredPropertyRepository;

import java.util.List;

public interface IPropertyService {
    InsuredPropertyRepository getInsuredProperty();

   void setInsuredProperty(InsuredPropertyRepository InsuredProperty);

   List<InsuredProperty> retrieveAllVisibleInsuredProperties();

   List<InsuredProperty> retrieveNotVisibleInsuredProperties();

   InsuredProperty addInsuredProperty(InsuredProperty ip);

   void deleteInsuredProperty(String id);

   InsuredProperty updateInsuredProperty(InsuredProperty ip);

    InsuredProperty retrieveInsuredProperty(String id);

   void archiveInsuredProperty(InsuredProperty ip);

   void affecterPropertyAContract(int propertyId, int contractId);

  Double TauxInsuredProperty(PropertyType type);
}
//