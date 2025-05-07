package com.electricity_distribution_system.eds;

import com.electricity_distribution_system.eds.enums.ERole;
import com.electricity_distribution_system.eds.models.Role;
import com.electricity_distribution_system.eds.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
public class ElectricityDistributionSystemApplication {
	private  RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ElectricityDistributionSystemApplication.class, args);

	}


	@Autowired
	public ElectricityDistributionSystemApplication(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;


	}
	@Bean
	public boolean registerRoles() {
		Set<ERole> roles = new HashSet<>();
		roles.add(ERole.ADMIN);
		roles.add(ERole.CUSTOMER);

		for (ERole role : roles) {
			Optional<Role> roleByName = roleRepository.findByName(role);
			if (roleByName.isEmpty()) {
				Role newRole = new Role(role, role.toString());
				roleRepository.save(newRole);
				System.out.println("Created: " + role);

			}
		}
		return true;
	}

}
