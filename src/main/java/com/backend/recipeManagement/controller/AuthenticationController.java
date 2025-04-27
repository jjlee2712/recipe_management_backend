package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.authentication.AuthenticationRequestDTO;
import com.backend.recipeManagement.dto.authentication.RegistrationRequestDTO;
import com.backend.recipeManagement.security.CustomUserDetailsService;
import com.backend.recipeManagement.security.JwtUtil;
import com.backend.recipeManagement.services.IAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
  private final IAuthenticationService authenticationService;
  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService customUserDetailsService;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public String generateToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequestDTO.username(), authenticationRequestDTO.password()));
    UserDetails userDetails =
        customUserDetailsService.loadUserByUsername(authenticationRequestDTO.username());
    return jwtUtil.generateToken(userDetails);
  }

  @PostMapping("/registration")
  public void registration(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
    authenticationService.registration(registrationRequestDTO);
  }
}
