package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.authentication.AuthenticationRequestDTO;
import com.backend.recipeManagement.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @PostMapping("/authenticate")
  public String generateToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
    System.out.println("CALLED");
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequestDTO.username(), authenticationRequestDTO.password()));
    System.out.println("User Authenticated");
    return jwtUtil.generateToken(authenticationRequestDTO.username());
  }
}
