package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.users.ChangePasswordDTO;
import com.backend.recipeManagement.dto.users.UpdateUsersDTO;
import com.backend.recipeManagement.services.IAuthenticationService;
import com.backend.recipeManagement.services.IUserService;
import com.backend.recipeManagement.util.LogUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
  private final IUserService userService;
  private final IAuthenticationService authenticationService;

  @GetMapping("")
  public UserDTO getUserDetails(Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "getUserDetails");
    UserDTO user = authenticationService.getUserDetails();
    return user;
  }

  @PostMapping("")
  public void updateUserDetails(
      @RequestBody UpdateUsersDTO updateUsersDTO, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "updateUserDetails");
    UserDTO user = authenticationService.getUserDetails();
    userService.updateUserDetails(updateUsersDTO, user);
  }

  @PostMapping("/change_password")
  public void changePassword(
      @RequestBody ChangePasswordDTO changePasswordDTO, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "changePassword");
    UserDTO user = authenticationService.getUserDetails();
    userService.changePassword(changePasswordDTO, user);
  }
}
