package com.neighborhood.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.neighborhood.configuration.properties.AppProperties;

@SpringBootApplication
@ComponentScan("com.neighborhood")
@EntityScan("com.neighborhood.model")
@EnableJpaRepositories("com.neighborhood.repository")
@EnableConfigurationProperties(AppProperties.class)
public class NeighborhoodApplication {
	public static void main(String[] args) {
		SpringApplication.run(NeighborhoodApplication.class, args);
	}
}
