package com.rustam.modern_dentistry;

import com.rustam.modern_dentistry.dao.entity.enums.PermissionAction;
import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.entity.settings.permission.ModulePermissionEntity;
import com.rustam.modern_dentistry.dao.entity.settings.permission.Permission;
import com.rustam.modern_dentistry.dao.entity.users.Admin;
import com.rustam.modern_dentistry.dao.repository.BaseUserRepository;
import com.rustam.modern_dentistry.dao.repository.settings.PermissionRepository;
import com.rustam.modern_dentistry.dto.ModulePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
public class ModernDentistryApplication implements CommandLineRunner {

	private final BaseUserRepository baseUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final PermissionRepository permissionRepository;

	public static void main(String[] args) {
		SpringApplication.run(ModernDentistryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		boolean existsBaseUserByEmail = baseUserRepository.existsBaseUserByEmail("superadmin@example.com");
		if (!existsBaseUserByEmail) {
			Permission superAdminPermission = createIfNotExists();

			Admin admin = Admin.builder()
					.id(UUID.randomUUID())
					.name("Super")
					.surname("Admin")
					.phone("+994501112233")
					.email("superadmin@example.com")
					.username("super_admin")
					.password(passwordEncoder.encode("super1234"))
					.enabled(true)
					.permissions(Set.of(superAdminPermission))
					.build();

			baseUserRepository.save(admin);

			System.out.println("✅ Default SUPER_ADMIN created: superadmin@example.com / super1234");
		}
	}

	private Permission createIfNotExists() {
			return permissionRepository.findByPermissionName("SUPER_ADMIN")
					.orElseGet(() -> {
						Permission newPermission = Permission.builder()
								.permissionName("SUPER_ADMIN")
								.status(Status.ACTIVE)
								.build();
						return permissionRepository.save(newPermission);
				});
	}


}
