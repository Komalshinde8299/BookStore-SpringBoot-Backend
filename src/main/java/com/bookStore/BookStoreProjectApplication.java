package com.bookStore;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;

@SpringBootApplication
public class BookStoreProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreProjectApplication.class, args);
	}
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	@Bean
	public SimpleMailMessage getSimpleMailMessage() {
		return new SimpleMailMessage();
	}

}
