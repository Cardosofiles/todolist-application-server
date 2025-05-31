package br.com.cardosofiles.todolist_application_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistApplicationServerApplication {

	private static final Logger logger =
			LoggerFactory.getLogger(TodolistApplicationServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplicationServerApplication.class, args);
		logger.info("🛠️ Aplicação iniciada com sucesso");
	}
}

