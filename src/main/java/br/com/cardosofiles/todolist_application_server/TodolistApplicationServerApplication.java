package br.com.cardosofiles.todolist_application_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import br.com.cardosofiles.todolist_application_server.filter.FilterTackAuth;

@SpringBootApplication
public class TodolistApplicationServerApplication {

	private static final Logger logger =
			LoggerFactory.getLogger(TodolistApplicationServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplicationServerApplication.class, args);
		logger.info("🛠️ Aplicação iniciada com sucesso");
	}

	@Bean
	public FilterRegistrationBean<FilterTackAuth> filterTackAuthRegistration(
			FilterTackAuth filter) {
		FilterRegistrationBean<FilterTackAuth> registration = new FilterRegistrationBean<>();
		registration.setFilter(filter);
		registration.addUrlPatterns("/tasks/*"); // Aplica o filtro apenas nas rotas /tasks
		registration.setOrder(1); // Prioridade do filtro
		return registration;
	}
}
