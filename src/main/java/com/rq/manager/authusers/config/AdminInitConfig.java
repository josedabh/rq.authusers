package com.rq.manager.authusers.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.repository.UserRepository;

/**
 * Configuration to initialize an admin user at application startup.
 */
@Configuration
public class AdminInitConfig {

    /**
     * Inits the admin.
     *
     * @param userRepository the user repository
     * @param encoder the encoder
     * @return the command line runner
     */
    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            // Check if admin already exists by email or username
            boolean exists = userRepository.existsByEmail("admin@example.com")
                || userRepository.existsByUsername("admin");
            if (!exists) {
                User admin = new User();
                admin.setEmail("admin@example.com");
                admin.setPassword(encoder.encode("adminexample1234"));
                admin.setName("Admin");
                admin.setLastname("System");
                admin.setUsername("admin");
                admin.setRol(RolEnum.ADMIN);
                admin.setNumPhone("654321987");
                admin.setPoints(10000);
                userRepository.save(admin);
            }
        };
    }
}
