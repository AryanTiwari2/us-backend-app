package com.aryan.us_backend_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UsBackendAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsBackendAppApplication.class, args);
	}

}
