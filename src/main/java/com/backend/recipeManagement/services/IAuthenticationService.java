package com.backend.recipeManagement.services;

import com.backend.recipeManagement.dto.authentication.RegistrationRequestDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;

public interface IAuthenticationService {
  void registration(RegistrationRequestDTO registrationRequestDTO);

  UserDTO getUserDetails();
}
