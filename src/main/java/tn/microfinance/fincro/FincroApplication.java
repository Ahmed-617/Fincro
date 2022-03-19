package tn.microfinance.fincro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tn.microfinance.fincro.services.interfaces.TransactionService;
import tn.microfinance.fincro.services.interfaces.UserService;

import java.util.Date;

@EnableWebMvc
@EnableScheduling
@SpringBootApplication
public class FincroApplication {
	@Autowired
	TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(FincroApplication.class, args);
	}

	@Scheduled(cron = "0 47 22 * * *")
	void executeScheduledTransactions(){
		transactionService.executeScheduledTransactions();
	}
 //commandLineRunner run (UserService userService){
	//	return  arg->{
	//		userService.saveRole()
	//	}
 //}
}

