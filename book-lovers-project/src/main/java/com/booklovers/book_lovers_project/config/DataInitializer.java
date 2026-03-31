package com.booklovers.book_lovers_project.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.booklovers.book_lovers_project.entity.RoleEntity;
import com.booklovers.book_lovers_project.entity.UserEntity;
import com.booklovers.book_lovers_project.repository.RoleRepository;
import com.booklovers.book_lovers_project.repository.UserRepository;

@Configuration
public class DataInitializer {

	@Bean
	CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
		return args -> {
			// 1) Rolları əlavə et (əgər yoxdursa)
			if (roleRepo.count() == 0) {
				RoleEntity userRole = new RoleEntity();
				userRole.setName("ROLE_USER");
				RoleEntity adminRole = new RoleEntity();
				adminRole.setName("ROLE_ADMIN");
				roleRepo.save(userRole);
				roleRepo.save(adminRole);
				System.out.println("Roles created: ROLE_USER, ROLE_ADMIN");
			} else {
				System.out.println("Roles already present");
			}

			// 2) Admin istifadəçi əlavə et (əgər yoxdursa)
			if (userRepo.findByUsername("admin").isEmpty()) {
				RoleEntity adminRole = roleRepo.findByName("ROLE_ADMIN")
						.orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found"));

				UserEntity admin = new UserEntity();
				admin.setUsername("admin");
				admin.setEmail("admin@example.com");
				admin.setPassword(encoder.encode("admin123")); // təhlükəsizlik üçün hash edilmiş şifrə
				admin.setRoles(Set.of(adminRole));
				userRepo.save(admin);
				System.out.println("Admin user created (username=admin, password=admin123)");
			} else {
				System.out.println("Admin user already exists");
			}
		};
	}
}