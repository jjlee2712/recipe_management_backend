package com.backend.recipeManagement.services.impl;

import com.backend.recipeManagement.constant.CommonConstant;
import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.PaginationResponseDTO;
import com.backend.recipeManagement.dto.admin.AddCategoryDTO;
import com.backend.recipeManagement.dto.admin.CategoryListDTO;
import com.backend.recipeManagement.dto.admin.CategoryListRequestDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.exception.ExceptionCode;
import com.backend.recipeManagement.exception.RecipeManagementException;
import com.backend.recipeManagement.mapper.category.CategoryMapper;
import com.backend.recipeManagement.model.Category;
import com.backend.recipeManagement.repository.jooq.CategoryRepositoryJooq;
import com.backend.recipeManagement.repository.jpa.CategoryRepository;
import com.backend.recipeManagement.services.IAdminService;
import com.backend.recipeManagement.util.LogUtil;
import com.backend.recipeManagement.util.PaginationUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class AdminService implements IAdminService {
  private final CategoryRepository categoryRepository;
  private final CategoryRepositoryJooq categoryRepositoryJooq;

  @Override
  public List<CategoryListDTO> getCategoryList(
      CategoryListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "getCategoryList");
    PaginationRequestDTO pg =
        PaginationUtil.pageSorting(paginationRequestDTO, new CategoryMapper(), "asc");
    return categoryRepositoryJooq.getCategoryList(requestDTO, pg);
  }

  @Override
  public PaginationResponseDTO getCategoryListPages(
      CategoryListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "getCategoryListPages");
    Long total = categoryRepositoryJooq.getCategoryListPages(requestDTO);
    return PaginationUtil.pagination(paginationRequestDTO.size(), total);
  }

  @Override
  @Transactional
  public void createCategory(AddCategoryDTO addCategoryDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "createCategory");

    // Validate Category to prevent duplicated Category
    validateAddCategory(addCategoryDTO);

    // Add Category if validation pass
    Category category = new Category();
    category.setCategoryName(addCategoryDTO.categoryName());
    category.setActiveFlag(
        addCategoryDTO.status() ? CommonConstant.ACTIVE : CommonConstant.INACTIVE);
    category.setCreatedBy(user.userId());
    category.setUpdatedBy(user.userId());
    categoryRepository.save(category);
  }

  private void validateAddCategory(AddCategoryDTO addCategoryDTO) {
    log.info(LogUtil.ENTRY_CONTROLLER, "validateCategory");
    if (addCategoryDTO.categoryName() == null || addCategoryDTO.categoryName().isBlank()) {
      throw new RecipeManagementException(
          "Invalid Category Name", "Category Name is required", ExceptionCode.BAD_REQUEST);
    }

    // Check duplicated
    Category category = categoryRepository.findByCategoryName(addCategoryDTO.categoryName());
    if (category != null) {
      throw new RecipeManagementException(
          "Duplicated", "Category Name already exists", ExceptionCode.CONFLICT);
    }
  }

  @Override
  @Transactional
  public void updateCategory(Long categoryId, AddCategoryDTO updateCategoryDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "updateCategory");
    // Validate Category
    validateUpdateCategory(categoryId, updateCategoryDTO);

    // Update Category if validation pass
    Category category = categoryRepository.getReferenceById(categoryId);
    category.setCategoryName(updateCategoryDTO.categoryName());
    category.setActiveFlag(
        updateCategoryDTO.status() ? CommonConstant.ACTIVE : CommonConstant.INACTIVE);
    category.setUpdatedBy(user.userId());
    categoryRepository.save(category);
  }

  private void validateUpdateCategory(Long categoryId, AddCategoryDTO updateCategoryDTO) {
    log.info(LogUtil.ENTRY_CONTROLLER, "validateUpdateCategory");
    if (updateCategoryDTO.categoryName() == null || updateCategoryDTO.categoryName().isBlank()) {
      throw new RecipeManagementException(
          "Invalid Category Name", "Category Name is required", ExceptionCode.BAD_REQUEST);
    }

    // Check duplicated
    Category category =
        categoryRepository.findByCategoryNameAndCategoryIdNot(
            updateCategoryDTO.categoryName(), categoryId);
    if (category != null) {
      throw new RecipeManagementException(
          "Duplicated", "Category Name already exists", ExceptionCode.CONFLICT);
    }
  }

  @Override
  @Transactional
  public void deleteCategory(Long categoryId, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "deleteCategory");
    Category category = categoryRepository.getReferenceById(categoryId);
    categoryRepository.delete(category);
  }
}
