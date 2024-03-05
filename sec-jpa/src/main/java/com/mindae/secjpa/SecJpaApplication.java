package com.mindae.secjpa;

import com.mindae.secjpa.model.User;
import com.mindae.secjpa.repo.UserRepo;
import com.mindae.secjpa.service.JwtService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepo userRepo, PasswordEncoder passwordEncoder) {
		return args -> {
			userRepo.save(new User("user", passwordEncoder.encode("user"), "ROLE_USER"));
			userRepo.save(new User("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
		};
	}

	@Bean
	CommandLineRunner commandLineRunner2(JwtService jwtService) {
		return args -> {
			String token = jwtService.generateToken("john");
			System.out.println("TOKEN: "+token);
			System.out.println("IS VALID: "+jwtService.validateToken(token));
			jwtService.extractJwtComponents(token);
		};
	}
}
