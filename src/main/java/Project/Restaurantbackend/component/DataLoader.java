package Project.Restaurantbackend.component;

import Project.Restaurantbackend.entity.Role;
import Project.Restaurantbackend.entity.User;
import Project.Restaurantbackend.entity.enums.RoleName;
import Project.Restaurantbackend.repository.AuthRepository;
import Project.Restaurantbackend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String intiMode;

    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (intiMode.equals("create") || intiMode.equals("create-drop")) {
            for (RoleName value : RoleName.values()) {
                roleRepository.save(new Role(value));
            }

            authRepository.save(
                    User.builder()
                            .name("Admin")
                            .surname("Adminov")
                            .phoneNumber("(99) 000 88 77")
                            .email("restaurant@gmail.com")
                            .password(passwordEncoder.encode("root1234"))
                            .roles(Collections.singleton(roleRepository.findById(1).orElseThrow(() -> new ResourceNotFoundException("getRole"))))
                            .accountNonLocked(true)
                            .accountNonExpired(true)
                            .credentialsNonExpired(true)
                            .enabled(true)
                            .build()
            );
        }
    }
}
