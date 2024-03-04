package com.mindae.secjpa.config.sercurity.SecurityConfig;

import com.mindae.secjpa.model.User;
import com.mindae.secjpa.repo.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class AppSecurity {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http
                .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers("/").permitAll().anyRequest().authenticated();
                })
                .headers(header->header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
    @Bean
    UserDetailsService userDetailsService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        UserDetailsService userDetailsService = new UserDetailsService() {
            @Override
            public User loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepo
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));
            }
        };
        return userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
