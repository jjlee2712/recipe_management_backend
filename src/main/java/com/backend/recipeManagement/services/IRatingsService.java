package com.backend.recipeManagement.services;

import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.ratings.AddRatingsDTO;

public interface IRatingsService {
  void rateRecipe(Long recipeId, AddRatingsDTO ratingsDTO, UserDTO user);
}
