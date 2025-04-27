package com.backend.recipeManagement.services;

import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.PaginationResponseDTO;
import com.backend.recipeManagement.dto.admin.AddCategoryDTO;
import com.backend.recipeManagement.dto.admin.CategoryListDTO;
import com.backend.recipeManagement.dto.admin.CategoryListRequestDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import java.util.List;

public interface IAdminService {

  List<CategoryListDTO> getCategoryList(
      CategoryListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO, UserDTO user);

  PaginationResponseDTO getCategoryListPages(
      CategoryListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO, UserDTO user);

  void createCategory(AddCategoryDTO addCategoryDTO, UserDTO user);

  void updateCategory(Long categoryId, AddCategoryDTO updateCategoryDTO, UserDTO user);

  void deleteCategory(Long categoryId, UserDTO user);
}
