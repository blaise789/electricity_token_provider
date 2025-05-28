package com.vehicle_tracking;

import com.vehicle_tracking.enums.ERole;
import com.vehicle_tracking.models.Role;
import com.vehicle_tracking.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "")
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
//@EnableCaching
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class VehicleTrackingApplication {
	private final RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(VehicleTrackingApplication.class, args);

	}


	@Autowired
	public VehicleTrackingApplication(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;


	}
	@Bean
	public boolean registerRoles() {
		Set<ERole> roles = new HashSet<>();
		roles.add(ERole.ROLE_STANDARD);
		roles.add(ERole.ROLE_ADMIN);

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
