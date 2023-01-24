package com.arles.backendjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendjavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendjavaApplication.class, args);
		String mensaje = "meloncita ya";
		System.out.print(mensaje);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
