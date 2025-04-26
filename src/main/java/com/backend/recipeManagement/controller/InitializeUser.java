package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.model.Users;
import com.backend.recipeManagement.repository.jpa.UsersRepository;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitializeUser {
  @Bean
  public CommandLineRunner createAdminUser(
      UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      if (usersRepository.findByUsername("admin").isEmpty()) {
        Users users = new Users();
        users.setUsername("admin");
        users.setPassword(passwordEncoder.encode("admin123"));
        users.setRoles("ADMIN");
        users.setCreatedDate(LocalDateTime.now());
        users.setUpdatedDate(LocalDateTime.now());
        usersRepository.save(users);
        System.out.println("User Created");
      }
    };
  }
}
