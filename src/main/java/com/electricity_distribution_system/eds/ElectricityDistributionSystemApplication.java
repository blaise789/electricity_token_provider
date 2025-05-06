package com.electricity_distribution_system.eds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ElectricityDistributionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricityDistributionSystemApplication.class, args);
	}

}
