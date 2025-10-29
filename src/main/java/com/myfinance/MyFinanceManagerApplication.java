package com.myfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyFinanceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFinanceManagerApplication.class, args);
	}

}
