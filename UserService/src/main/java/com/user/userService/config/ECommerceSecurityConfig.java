package com.user.userService.config;







import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.user.userService.services.ECommerceUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




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
            .securityMatchers((matchers) -> matchers
                .requestMatchers("/register/**","/internal/**")
             )
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() 
            )
            .csrf(csrf -> csrf.disable()); 

        return http.build();
    }
    @Bean
    @Order(0) 
    public SecurityFilterChain ApiChainFilters(HttpSecurity http) throws Exception {
        http
            .securityMatchers((matchers) -> matchers
                .requestMatchers("/auth", "/auth/", "/auth/**", "/user", "/user/", "/user/**")
             )
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated() 
        )
        .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new BasicAuthEntryPoint())) 
        .csrf(csrf -> csrf.disable())
        .userDetailsService(userDetailsService)
        .exceptionHandling(e -> e
            .accessDeniedHandler((request, response, accessDeniedException) -> { //I'm not sure I really need this, but it'll be a usefull template for later
                
             System.out.println("test here--------------");
            response.sendError(403); 
            }
            
            )
            
            )
            ; 
    return http.build();
    }
    // @Bean
    // @Order(0)
    // public SecurityFilterChain ApiChainFilters(HttpSecurity http) throws Exception {
    //     http
    //         .securityMatchers((matchers) -> matchers
    //             .requestMatchers("/auth", "/auth/", "/auth/**", "/user", "/user/", "/user/**")
    //         )
    //         .addFilterBefore(new OncePerRequestFilter() {
    //             @Override
    //             protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    //                     throws ServletException, IOException {
    //                 System.out.println("ApiChainFilters handling: " + request.getRequestURI());
    //                 filterChain.doFilter(request, response);
    //             }
    //         }, UsernamePasswordAuthenticationFilter.class)
    //         .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
    //         .httpBasic(Customizer.withDefaults())
    //         .csrf(csrf -> csrf.disable())
    //         .exceptionHandling(e -> e
    //             .authenticationEntryPoint((request, response, authException) -> {
    //                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    //             })
    //         )
    //         .userDetailsService(userDetailsService);
    //     return http.build();
    // }

    @Bean
    @Order(10) 
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
        .securityMatcher("/**") 
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated() 
        )
        .formLogin(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .userDetailsService(userDetailsService); 
    return http.build();



    }
} 