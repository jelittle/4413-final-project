package com.user.userService.config;

import com.user.userService.models.ECommerceUser;
import com.user.userService.repositories.ECommerceUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(ECommerceUserRepository userRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("user").isEmpty()) {
            ECommerceUser user = new ECommerceUser();
            user.setUsername("user");
            user.setPassword(encoder.encode("password"));
            user.setAuthorities(Set.of("ROLE_USER"));
            user.setEnabled(true);
            user.setEmail("user@example.com");
            user.setFirstName("UserFirstName");
            user.setLastName("UserLastName");
            userRepository.save(user);
            }
            if (userRepository.findByUsername("admin").isEmpty()) {
            ECommerceUser admin = new ECommerceUser();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("password"));
            admin.setAuthorities(Set.of("ROLE_USER", "ROLE_ADMIN"));
            admin.setEnabled(true);
            admin.setEmail("admin@example.com");
            admin.setFirstName("AdminFirstName");
            admin.setLastName("AdminLastName");
            userRepository.save(admin);
            }
        };
    }
}