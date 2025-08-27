package com.autoshopping.stock_control;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableJpaRepositories
@EnableWebMvc
@SpringBootApplication
@EnableScheduling
public class StockControlApplication {

	public static final Logger logger= LoggerFactory.getLogger(StockControlApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StockControlApplication.class, args);
		logger.info("Api iniciada.");
	}
}


