package com.mercadofacil.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/h2-console/**", "/api/auth/**", "/api/teste", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .headers().frameOptions().sameOrigin() // h2 console
            .and()
                .httpBasic().disable();

        // Note: for JWT, you'll add a filter that validates token and sets auth in context.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // dev only! in prod use BCryptPasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }
}
