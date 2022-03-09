package tn.microfinance.fincro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class FincroApplication {

	public static void main(String[] args) {
		SpringApplication.run(FincroApplication.class, args);
	}

}
