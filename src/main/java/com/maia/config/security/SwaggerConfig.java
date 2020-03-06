package com.maia.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket docket () {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.maia.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());		
	}
	
	private ApiInfo apiInfo() {
		return  new ApiInfoBuilder()
				.title("API PDV")
				.description("API do Projeto Sistema de Vendas ")
				.version("2.0")
				.contact(contact())
				.build();		
	}
	
	private Contact contact() {
		return new Contact("Dowglas Maia", 
				"https://github.com/dowglasmaia", 
				"dowglasmaia@live.com");
	}

}
