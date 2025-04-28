package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.users.ChangePasswordDTO;
import com.backend.recipeManagement.dto.users.UpdateUsersDTO;
import com.backend.recipeManagement.services.IAuthenticationService;
import com.backend.recipeManagement.services.IUserService;
import com.backend.recipeManagement.util.LogUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "GET User Details", description = "GET API for User Details")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved User Details",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))))
  })
  @GetMapping("")
  public UserDTO getUserDetails(Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "getUserDetails");
    UserDTO user = authenticationService.getUserDetails();
    return user;
  }

  @Operation(summary = "Update User Details", description = "POST API for Update User Details")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully updated User Details",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @PostMapping("")
  public void updateUserDetails(
      @RequestBody UpdateUsersDTO updateUsersDTO, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "updateUserDetails");
    UserDTO user = authenticationService.getUserDetails();
    userService.updateUserDetails(updateUsersDTO, user);
  }

  @Operation(summary = "Change Password", description = "POST API for change password")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully changed user password",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @PostMapping("/change_password")
  public void changePassword(
      @RequestBody ChangePasswordDTO changePasswordDTO, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "changePassword");
    UserDTO user = authenticationService.getUserDetails();
    userService.changePassword(changePasswordDTO, user);
  }
}
