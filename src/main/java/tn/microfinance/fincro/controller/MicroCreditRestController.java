package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.microfinance.fincro.dao.model.CreditStatus;
import tn.microfinance.fincro.dao.model.CreditType;
import tn.microfinance.fincro.dao.model.MicroCredit;
import tn.microfinance.fincro.services.interfaces.MicroCreditService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@RestController
public class MicroCreditRestController {


    @Autowired
    MicroCreditService creditService;

    @GetMapping("getAllCredits")
    public List<MicroCredit> getCredits(){
        return creditService.retrieveAllCredits();
    }

    @GetMapping("getCredit/{id}")
    public MicroCredit getCreditById(@PathVariable("id") Long id){
        return creditService.retrieveCredit(id);
    }

    @PostMapping("addCredit/{idAccount}")
    public MicroCredit addCredit(@RequestBody MicroCredit credit,@PathVariable("idAccount") long idAccount){
        return creditService.addCredit(credit,idAccount);
    }

    @PutMapping("updateCredit")
    public MicroCredit updateCredit(@RequestBody MicroCredit credit){
        return creditService.updateCredit(credit);
    }

    @DeleteMapping("deleteCredit/{id}")
    public void deleteCredit(@PathVariable("id") Long id){
        creditService.deleteCredit(id);
    }

    @GetMapping("getAllCreditsByType/{type}")
    public List<MicroCredit> getCreditsByType(@PathVariable("type") CreditType type){
        return creditService.getCreditsByType(type);
    }

    @GetMapping("getAllCreditsByStatus/{status}")
    public List<MicroCredit> getCreditsByStatus(@PathVariable("status")CreditStatus status){
        return creditService.getCreditsByStatus(status);
    }

    @GetMapping("simulator/{amount}/{period}/{typePeriod}")
    public Hashtable<String, Double> Simulation(@PathVariable double amount, @PathVariable int period,@PathVariable String typePeriod ){
        return creditService.Simulation(amount,period,typePeriod);
    }

    @GetMapping("FailureToPay/{idCredit}/{period}/{interestAmount}")
    public Hashtable<String, Double> FailureToPay(@PathVariable long idCredit,@PathVariable int period,@PathVariable double interestAmount){
        return creditService.FailureToPay(idCredit,period,interestAmount);
    }

    @GetMapping("CapacityToPay/{idCredit}/{newAmount}")
    public int CapacityToPay(@PathVariable long idCredit,@PathVariable double newAmount){
        return creditService.CapacityToPay(idCredit,newAmount);
    }

    @GetMapping("/excel/{amount}/{period}/{typePeriod}")
    public void exportToExcel(HttpServletResponse response,@PathVariable double amount, @PathVariable int period,@PathVariable String typePeriod) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=MicroCredit" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        Hashtable<String, Double> sim = Simulation(amount,period,typePeriod);

        CreditExcelExporter excelExporter = new CreditExcelExporter(sim,period);

        excelExporter.export(response);
    }
}
