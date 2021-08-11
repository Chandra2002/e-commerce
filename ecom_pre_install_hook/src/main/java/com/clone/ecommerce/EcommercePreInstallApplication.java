package com.clone.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.clone.ecommerce.*"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class EcommercePreInstallApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EcommercePreInstallApplication.class, args);
		System.exit(SpringApplication.exit(context, () -> 0));
	}

}
