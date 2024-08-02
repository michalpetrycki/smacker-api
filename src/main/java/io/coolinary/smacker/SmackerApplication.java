package io.coolinary.smacker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmackerApplication {

	public static void main(String[] args) {
		SpringApplication springApp = new SpringApplication(SmackerApplication.class);
		springApp.setAdditionalProfiles("dev");
		springApp.run(args);

	}

}
