package com.backend.recipeManagement.services.impl;

import com.backend.recipeManagement.dto.authentication.RegistrationRequestDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.exception.ExceptionCode;
import com.backend.recipeManagement.exception.RecipeManagementException;
import com.backend.recipeManagement.model.Users;
import com.backend.recipeManagement.repository.jpa.UsersRepository;
import com.backend.recipeManagement.services.IAuthenticationService;
import com.backend.recipeManagement.util.LogUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {
  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void registration(RegistrationRequestDTO registrationRequestDTO) {
    log.info(LogUtil.ENTRY_SERVICES, "registration");
    Users users = new Users();
    validateUser(registrationRequestDTO);
    users.setUsername(registrationRequestDTO.username());
    users.setPassword(passwordEncoder.encode(registrationRequestDTO.password()));
    users.setRoles(registrationRequestDTO.roles());
    users.setCreatedDate(LocalDateTime.now());
    users.setUpdatedDate(LocalDateTime.now());
    users.setFullName(registrationRequestDTO.fullname());
    usersRepository.save(users);
    users.setCreatedBy(users.getUserId());
    users.setUpdatedBy(users.getUserId());
    usersRepository.save(users);
  }

  private void validateUser(RegistrationRequestDTO registrationRequestDTO) {
    log.info(LogUtil.ENTRY_SERVICES, "validateUser");
    Users existingUser =
        usersRepository.findByUsername(registrationRequestDTO.username()).orElse(null);
    if (existingUser != null) {
      throw new RecipeManagementException(
          "Duplicated", "Username already exists", ExceptionCode.CONFLICT);
    }

    if (registrationRequestDTO.roles() == null || registrationRequestDTO.roles().isEmpty()) {
      throw new RecipeManagementException(
          "Invalid Roles", "Roles is required.", ExceptionCode.BAD_REQUEST);
    }
    if (!registrationRequestDTO.roles().equals("ADMIN")
        && !registrationRequestDTO.roles().equals("USER")) {
      throw new RecipeManagementException(
          "Invalid Roles", "Roles must be either ADMIN or USER", ExceptionCode.BAD_REQUEST);
    }
  }

  @Override
  public UserDTO getUserDetails() {
    log.info(LogUtil.ENTRY_SERVICES, "getUserDetails");
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Users users = usersRepository.findByUsername(authentication.getName()).orElse(null);
    if (users == null) {
      throw new RecipeManagementException(
          "User not found", "User not found", ExceptionCode.NOT_FOUND);
    }
    return new UserDTO(
        users.getUserId(), users.getUsername(), users.getFullName(), users.getRoles());
  }
}
