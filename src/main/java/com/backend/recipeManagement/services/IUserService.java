package com.backend.recipeManagement.services;

import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.users.ChangePasswordDTO;
import com.backend.recipeManagement.dto.users.UpdateUsersDTO;

public interface IUserService {
  void updateUserDetails(UpdateUsersDTO updateUsersDTO, UserDTO userDTO);

  void changePassword(ChangePasswordDTO changePasswordDTO, UserDTO userDTO);
}
