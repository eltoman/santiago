package com.emstudies.santiago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.emstudies")
@EnableSwagger2
public class SantiagoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SantiagoApplication.class, args);
	}

}
