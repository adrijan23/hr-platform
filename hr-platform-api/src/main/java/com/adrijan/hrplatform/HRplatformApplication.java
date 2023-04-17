package com.adrijan.hrplatform;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@OpenAPIDefinition(			//Swagger localhost:8080/swagger-ui/index.html
		info = @Info(
				title = "HR platform",
				description = " HR platform for adding and monitoring job candidates and their skills",
				version = "1.0.0",
				contact = @Contact(
						name = "Adrijan Radjevic",
						email = "adrijan.radjevic@gmail.com"
				)
		)
)
public class HRplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(HRplatformApplication.class, args);
	}

}
