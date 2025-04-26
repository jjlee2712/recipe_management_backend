package com.backend.recipeManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class TestController {

  // private final AuthenticationManager authenticationManager;
  // @GetMapping("")
  // public String test() {
  //   authenticationManager.authenticate(new
  // UsernamePasswordAuthenticationToken(authenticationManager, authenticationManager))
  //     return "test";
  // }

  @GetMapping("/test")
  public String test() {
    return "test";
  }

  @GetMapping("/authentication")
  public String test2() {
    return "authentication";
  }
}
