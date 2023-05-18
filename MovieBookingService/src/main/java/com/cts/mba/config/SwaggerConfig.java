package com.cts.mba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
	 Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cts.mba"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	
	private ApiInfo apiInfo() {
		
		return new ApiInfoBuilder()

                .title("Movie Booking Service of Movie Booking Application")
                .description("This service is responsible for booking movies by users")
                .version("1.0.0")
                .license("Apache 2.0")
                .contact(new Contact("Cognizant Technology Solutions","https://www.cognizant.com", "cognizant@gmail.com"))
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .build();
	}
	

}