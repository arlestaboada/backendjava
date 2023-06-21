package com.arles.backendjava;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.arles.backendjava.applicationContext.SpringApplicationContext;
import com.arles.backendjava.models.responses.UserRest;
import com.arles.backendjava.security.AppProperties;
import com.arles.backendjava.shared.dto.UserDto;

@SpringBootApplication
@EnableJpaAuditing
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

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean(name = "AppProperties")
	public AppProperties getAppProperties() {

		return new AppProperties();
	}

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper mapper = new ModelMapper();
		mapper.typeMap(UserDto.class, UserRest.class).addMappings(m -> m.skip(UserRest::setPosts));
		return mapper;
	}

}
