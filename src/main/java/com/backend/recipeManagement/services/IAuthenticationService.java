package com.backend.recipeManagement.services;

import com.backend.recipeManagement.dto.authentication.RegistrationRequestDTO;

public interface IAuthenticationService {
  void registration(RegistrationRequestDTO registrationRequestDTO);
}
