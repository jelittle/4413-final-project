package com.user.userService.config;







import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.user.userService.services.ECommerceUserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class ECommerceSecurityConfig {


    private final ECommerceUserDetailsService userDetailsService;

    public ECommerceSecurityConfig(ECommerceUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    @Order(1) 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/register/") 
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() 
            )
            .csrf(csrf -> csrf.disable()); 

        return http.build();
    }

    @Bean
    @Order(2) //this is lazy, should be in the save func as above
    public SecurityFilterChain InternalFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/internal/") 
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() 
            )
            .csrf(csrf -> csrf.disable()); 

        return http.build();
    }

    @Bean
    @Order(3) 
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
        .securityMatcher("/**") 
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated() 
        )
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())  //TODO: remove when id prod
        .csrf(csrf -> csrf.disable())
        .userDetailsService(userDetailsService); // Register your custom UserDetailsService
    return http.build();



    }
}