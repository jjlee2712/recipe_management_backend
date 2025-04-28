package com.backend.recipeManagement.services.impl;

import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.users.ChangePasswordDTO;
import com.backend.recipeManagement.dto.users.UpdateUsersDTO;
import com.backend.recipeManagement.exception.ExceptionCode;
import com.backend.recipeManagement.exception.RecipeManagementException;
import com.backend.recipeManagement.model.Users;
import com.backend.recipeManagement.repository.jpa.UsersRepository;
import com.backend.recipeManagement.services.IUserService;
import com.backend.recipeManagement.util.LogUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements IUserService {
  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void updateUserDetails(UpdateUsersDTO updateUsersDTO, UserDTO userDTO) {
    log.info(LogUtil.ENTRY_SERVICES, "updateUserDetails");
    Users users = usersRepository.getReferenceById(userDTO.userId());
    users.setFullName(updateUsersDTO.fullName());
    usersRepository.save(users);
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordDTO changePasswordDTO, UserDTO userDTO) {
    log.info(LogUtil.ENTRY_SERVICES, "changePassword");
    Users users = usersRepository.getReferenceById(userDTO.userId());
    if (!passwordEncoder.matches(changePasswordDTO.oldPassword(), users.getPassword())) {
      throw new RecipeManagementException(
          "Invalid Old Password", "Old Password is incorrect", ExceptionCode.BAD_REQUEST);
    }
    if (passwordEncoder.matches(changePasswordDTO.newPassword(), users.getPassword())) {
      throw new RecipeManagementException(
          "Invalid New Password",
          "New Password is same as old password",
          ExceptionCode.BAD_REQUEST);
    }
    users.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
    users.setUpdatedBy(userDTO.userId());
    usersRepository.save(users);
  }
}
