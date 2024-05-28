package com.ddukddak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class DdukddakApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdukddakApplication.class, args);
	}

}
