package com.ddukddak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
@EnableAsync
public class DdukddakApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdukddakApplication.class, args);
	}

}
