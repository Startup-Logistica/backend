package br.portela.startuplogistica.security.config;

import br.portela.startuplogistica.entities.User;
import br.portela.startuplogistica.enums.UserRole;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepositoryImpl userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin() {
        String adminEmail = "admin@teste.com";

        if (userRepository.findByEmailIncludeInactive(adminEmail).isEmpty()) {
            User u = new User();
            u.setName("Admin Padrão");
            u.setEmail(adminEmail);
            u.setPassword(passwordEncoder.encode("admin123"));
            u.setRole(UserRole.ADMIN);
            u.setEmailValidatedAt(LocalDateTime.now());
            userRepository.save(u);
        }
    }
}
