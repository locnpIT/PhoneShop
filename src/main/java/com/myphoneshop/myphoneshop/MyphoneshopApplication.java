package com.myphoneshop.myphoneshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@SpringBootApplication
public class MyphoneshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyphoneshopApplication.class, args);
	}

}
