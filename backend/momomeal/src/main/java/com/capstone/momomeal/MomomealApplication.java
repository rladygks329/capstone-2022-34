package com.capstone.momomeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MomomealApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomomealApplication.class, args);
	}

}
