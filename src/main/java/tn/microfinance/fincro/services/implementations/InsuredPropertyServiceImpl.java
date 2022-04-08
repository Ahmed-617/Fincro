package tn.microfinance.fincro.services.implementations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.InsuredProperty;
import tn.microfinance.fincro.dao.model.InsurenceContract;
import tn.microfinance.fincro.dao.model.PropertyType;
import tn.microfinance.fincro.dao.repositories.ContractRepository;
import tn.microfinance.fincro.dao.repositories.InsuredPropertyRepository;
import tn.microfinance.fincro.services.interfaces.IPropertyService;


import java.util.List;
import java.util.Optional;

@Service
public class InsuredPropertyServiceImpl implements IPropertyService {
    @Autowired
    InsuredPropertyRepository insuredpropertyRepository;

    @Autowired
    ContractRepository contractRepository;

    public static final Logger L = LogManager.getLogger(InsuredPropertyServiceImpl.class);

    public InsuredPropertyRepository getInsuredProperty() {
        return insuredpropertyRepository;
    }

    public void setInsuredProperty(InsuredPropertyRepository InsuredPropertyRepository) {
        this.insuredpropertyRepository = InsuredPropertyRepository;
    }

    @Override
    public List<InsuredProperty> retrieveAllVisibleInsuredProperties() {
        List<InsuredProperty> InsuredPropertys = (List<InsuredProperty>) insuredpropertyRepository.findAllVisibleInsuredProperties();

        for (InsuredProperty InsuredProperty : InsuredPropertys) {
            L.info("InsuredProperty +++" + InsuredProperty);
            ;
        }
        return InsuredPropertys;
    }

    @Override
    public List<InsuredProperty> retrieveNotVisibleInsuredProperties() {
        List<InsuredProperty> InsuredPropertys = (List<InsuredProperty>) insuredpropertyRepository.findNotVisibleInsuredProperties();

        for (InsuredProperty InsuredProperty : InsuredPropertys) {
            L.info("InsuredProperty +++" + InsuredProperty);
            ;
        }
        return InsuredPropertys;
    }

    @Override
    public InsuredProperty addInsuredProperty(InsuredProperty u) {
        System.out.println(GetLastStockValue());
        return insuredpropertyRepository.save(u);

    }

    @Override
    public void deleteInsuredProperty(String id) {
        Optional<InsuredProperty> InsuredPropertyOp = insuredpropertyRepository.findById(Integer.parseInt(id));
        if (InsuredPropertyOp.isPresent()) {
            insuredpropertyRepository.delete(InsuredPropertyOp.get());
            System.out.println("InsuredProperty deleted");
        } else {
            System.out.println("InsuredProperty not found");
        }

    }
    //

    @Override
    public InsuredProperty updateInsuredProperty(InsuredProperty u) {
        Integer t = u.getIdProperty();
        if(insuredpropertyRepository.findById(t).isPresent()){
            return insuredpropertyRepository.save(u);
        }
        else{
            System.out.println("InsuredProperty doesn't exist !");
            return null;
        }
    }

    @Override
    public InsuredProperty retrieveInsuredProperty(String id){
        Optional<InsuredProperty> InsuredPropertyOp = insuredpropertyRepository.findById(Integer.parseInt(id));
        if (InsuredPropertyOp.isPresent()) {
            InsuredPropertyOp.get();
        } else {
            System.out.println("InsuredProperty not found");
        }
        L.info("InsuredProperty +++" + InsuredPropertyOp.get());
        return InsuredPropertyOp.get();
    }

    @Override
    public void archiveInsuredProperty (InsuredProperty ip){
        insuredpropertyRepository.archiveInsuredProperty(ip.getIdProperty());
        insuredpropertyRepository.save(ip);
    }

    @Override
    public void affecterPropertyAContract(int propertyId, int contractId){
        InsurenceContract contractManagedEntity = contractRepository.findById(contractId).get();
        InsuredProperty propertyManagedEntity = insuredpropertyRepository.findById(propertyId).get();

        contractManagedEntity.setFkInsuredProperty(propertyManagedEntity);
        contractRepository.save(contractManagedEntity);
    }

    @Override
    public Double TauxInsuredProperty(PropertyType type){
        return (double) (insuredpropertyRepository.countInsuredPropertyByType(type)/insuredpropertyRepository.countInsuredProperty());
    }
    public double GetLastStockValue() {
        double value = 0;
        try{
            String excelPath ="E:\\Users\\skand\\Documents\\4EME\\Classeur1.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
            XSSFSheet sheet = workbook.getSheet("Feuil1");
            value = sheet.getRow(3).getCell(1).getNumericCellValue();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return value;
    }
}
