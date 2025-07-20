package net.bounceme.chronos.rulemanager.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableJpaRepositories(basePackages = {"net.bounceme.chronos.app.usuarios.commons.repository"})
@EntityScan(basePackages = "net.bounceme.chronos.app.usuarios.commons.model") 
public class GenericConfiguration {
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
