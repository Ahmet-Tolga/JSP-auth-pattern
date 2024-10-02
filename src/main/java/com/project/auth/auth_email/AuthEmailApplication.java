package com.project.auth.auth_email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AuthEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthEmailApplication.class, args);
	}

}
