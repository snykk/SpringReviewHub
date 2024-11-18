package com.example.springreviewhub.infrastructure.config;

import com.example.springreviewhub.infrastructure.security.JWTAuthenticationFilter;
import com.example.springreviewhub.infrastructure.security.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTAuthenticationFilter jwtFilter, CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
        // Konfigurasi HTTP Security
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/movies").hasAuthority("admin")
                .requestMatchers(HttpMethod.PUT, "/api/movies").hasAuthority("admin")
                .requestMatchers(HttpMethod.DELETE, "/api/movies").hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
        return http.build();
    }

    // AuthenticationManager untuk otentikasi
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // PasswordEncoder untuk hashing password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
