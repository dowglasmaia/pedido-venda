package com.maia.config.security;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.maia.utils.testIntegrations.TestsIntegrationsDB;

@Configuration
@Profile("dev")
public class DevTestIntegrationsConfig {
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Autowired
	private TestsIntegrationsDB dbTestsServices;

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		if (!"create".equals(strategy)) {
			return false;
		}

		dbTestsServices.instanciateTestDatabase();
		return true;
	}

}
