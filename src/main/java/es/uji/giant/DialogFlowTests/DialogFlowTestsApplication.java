package es.uji.giant.DialogFlowTests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DialogFlowTestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DialogFlowTestsApplication.class, args);
	}

}
