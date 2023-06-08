package com.rj.bookordersystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringWithJwtSecurityApplication {
	@Bean
	ModelMapper modelMapper(){ return new ModelMapper(); }

	public static void main(String[] args) {
		SpringApplication.run(SpringWithJwtSecurityApplication.class, args);
		System.out.println("Starting...");
	}

}
