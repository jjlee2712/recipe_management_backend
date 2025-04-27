package com.backend.recipeManagement.services;

import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.recipe.AddRecipeDTO;

public interface IRecipeService {
  void createRecipe(AddRecipeDTO addRecipeDTO, UserDTO user);

  void updateRecipe(Long recipeId, AddRecipeDTO updateRecipeDTO, UserDTO user);

  void deleteRecipe(Long recipeId);
}
