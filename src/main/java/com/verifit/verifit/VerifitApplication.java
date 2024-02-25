package com.verifit.verifit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 스프링 시큐리티 일단 비활성화
public class VerifitApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerifitApplication.class, args);
	}

}
