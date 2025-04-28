package com.backend.recipeManagement.services;

import com.backend.recipeManagement.dto.DropdownDTO;
import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.PaginationResponseDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.recipe.AddRecipeDTO;
import com.backend.recipeManagement.dto.recipe.RecipeDTO;
import com.backend.recipeManagement.dto.recipe.RecipeListDTO;
import com.backend.recipeManagement.dto.recipe.RecipeListRequestDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IRecipeService {
  List<RecipeListDTO> getRecipeList(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO, UserDTO user);

  PaginationResponseDTO getRecipeListPages(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO, UserDTO user);

  RecipeDTO getRecipe(Long recipeId, UserDTO user);

  void createRecipe(AddRecipeDTO addRecipeDTO, UserDTO user);

  void updateRecipe(Long recipeId, AddRecipeDTO updateRecipeDTO, UserDTO user);

  void deleteRecipe(Long recipeId);

  List<DropdownDTO> getCategoryList();

  void uploadAttachments(Long recipeId, MultipartFile files, UserDTO user);

  byte[] getAttachments(Long recipeId, Long attachmentId);
}
