package com.verinite.cla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(com.verinite.commons.config.SwaggerConfig.class)
@ComponentScan("com.verinite.commons")
public class ClaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaApplication.class, args);
	}

}
