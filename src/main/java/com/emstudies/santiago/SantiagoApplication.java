package com.emstudies.santiago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SantiagoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SantiagoApplication.class, args);
	}

}
