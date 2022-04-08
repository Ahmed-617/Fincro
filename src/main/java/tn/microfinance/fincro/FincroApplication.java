package tn.microfinance.fincro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tn.microfinance.fincro.services.interfaces.TransactionService;

@EnableWebMvc
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class FincroApplication {

	public static void main(String[] args) {
		SpringApplication.run(FincroApplication.class, args);
	}




}

