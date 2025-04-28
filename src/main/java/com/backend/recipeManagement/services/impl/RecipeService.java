package com.backend.recipeManagement.services.impl;

import com.backend.recipeManagement.constant.CommonConstant;
import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.PaginationResponseDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.recipe.AddRecipeDTO;
import com.backend.recipeManagement.dto.recipe.AddRecipeIngredientsDTO;
import com.backend.recipeManagement.dto.recipe.RecipeDTO;
import com.backend.recipeManagement.dto.recipe.RecipeListDTO;
import com.backend.recipeManagement.dto.recipe.RecipeListRequestDTO;
import com.backend.recipeManagement.exception.ExceptionCode;
import com.backend.recipeManagement.exception.RecipeManagementException;
import com.backend.recipeManagement.mapper.recipe.RecipeMapper;
import com.backend.recipeManagement.model.Category;
import com.backend.recipeManagement.model.Ingredients;
import com.backend.recipeManagement.model.Recipes;
import com.backend.recipeManagement.model.RecipesCategory;
import com.backend.recipeManagement.repository.jooq.RecipeRepositoryJooq;
import com.backend.recipeManagement.repository.jpa.CategoryRepository;
import com.backend.recipeManagement.repository.jpa.IngredientsRepository;
import com.backend.recipeManagement.repository.jpa.RecipeRepository;
import com.backend.recipeManagement.repository.jpa.RecipesCategoryRepository;
import com.backend.recipeManagement.services.IRecipeService;
import com.backend.recipeManagement.util.LogUtil;
import com.backend.recipeManagement.util.PaginationUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class RecipeService implements IRecipeService {
  private final RecipeRepositoryJooq recipeRepositoryJooq;
  private final RecipeRepository recipeRepository;
  private final RecipesCategoryRepository recipesCategoryRepository;
  private final CategoryRepository categoryRepository;
  private final IngredientsRepository ingredientsRepository;

  @Override
  public List<RecipeListDTO> getRecipeList(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "getRecipeList");
    PaginationRequestDTO pg =
        PaginationUtil.pageSorting(paginationRequestDTO, new RecipeMapper(), "asc");
    return recipeRepositoryJooq.getRecipeList(requestDTO, pg);
  }

  @Override
  public PaginationResponseDTO getRecipeListPages(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO paginationRequestDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "getRecipeListPages");
    Long total = recipeRepositoryJooq.getRecipeListPages(requestDTO);
    return PaginationUtil.pagination(paginationRequestDTO.size(), total);
  }

  @Override
  public RecipeDTO getRecipe(Long recipeId, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "getRecipe");
    return recipeRepositoryJooq.getRecipe(recipeId);
  }

  @Override
  @Transactional
  public void createRecipe(AddRecipeDTO addRecipeDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "createRecipe");
    validateRecipe(addRecipeDTO);
    Recipes recipes = new Recipes();
    recipes.setTitle(addRecipeDTO.title());
    recipes.setDescription(addRecipeDTO.description());
    recipes.setInstructions(addRecipeDTO.instruction());
    recipes.setPrepareTimeMinutes(addRecipeDTO.prepareTimeMinutes());
    recipes.setCookTimeMinutes(addRecipeDTO.cookTimeMinutes());
    recipes.setServings(addRecipeDTO.servings());
    recipes.setDifficulty(addRecipeDTO.difficulty());
    recipes.setStatus(addRecipeDTO.status());
    recipes.setActiveFlag(CommonConstant.ACTIVE);
    recipes.setCreatedBy(user.userId());
    recipes.setUpdatedBy(user.userId());
    recipeRepository.save(recipes);

    if (addRecipeDTO.categoryId() != null && addRecipeDTO.categoryId().size() > 0) {
      for (Long categoryId : addRecipeDTO.categoryId()) {
        RecipesCategory recipesCategory = new RecipesCategory();
        Category category = categoryRepository.getReferenceById(categoryId);
        recipesCategory.setRecipes(recipes);
        recipesCategory.setCategory(category);
        recipesCategory.setActiveFlag(CommonConstant.ACTIVE);
        recipesCategory.setCreatedBy(user.userId());
        recipesCategory.setUpdatedBy(user.userId());
        recipesCategoryRepository.save(recipesCategory);
      }
    } else {
      throw new RecipeManagementException(
          "Invalid Category", "Please add at least one category.", ExceptionCode.BAD_REQUEST);
    }

    if (addRecipeDTO.ingredientList() != null && addRecipeDTO.ingredientList().size() > 0) {
      for (AddRecipeIngredientsDTO ingredient : addRecipeDTO.ingredientList()) {
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredientName(ingredient.ingredientName());
        ingredients.setQuantity(ingredient.quantity());
        ingredients.setUnit(ingredient.unit());
        ingredients.setRecipes(recipes);
        ingredients.setActiveFlag(CommonConstant.ACTIVE);
        ingredients.setCreatedBy(user.userId());
        ingredients.setUpdatedBy(user.userId());
        ingredientsRepository.save(ingredients);
      }
    } else {
      throw new RecipeManagementException(
          "Invalid Ingredient",
          "Please add ingredients for this recipe.",
          ExceptionCode.BAD_REQUEST);
    }
  }

  private void validateRecipe(AddRecipeDTO addRecipeDTO) {
    log.info(LogUtil.ENTRY_SERVICES, "validateRecipe");
    if (addRecipeDTO.title() == null) {
      throw new RecipeManagementException(
          "Invalid Recipe Title", "Recipe Title is required", ExceptionCode.BAD_REQUEST);
    }
    if (addRecipeDTO.instruction() == null) {
      throw new RecipeManagementException(
          "Invalid Instruction", "Instruction is required", ExceptionCode.BAD_REQUEST);
    }
    if (addRecipeDTO.prepareTimeMinutes() == null) {
      throw new RecipeManagementException(
          "Invalid Prepare Time", "Prepare Time is required", ExceptionCode.BAD_REQUEST);
    }
    if (addRecipeDTO.cookTimeMinutes() == null) {
      throw new RecipeManagementException(
          "Invalid Cook Time", "Cook Time is required", ExceptionCode.BAD_REQUEST);
    }
    if (addRecipeDTO.difficulty() == null) {
      throw new RecipeManagementException(
          "Invalid Difficulty", "Difficulty is required", ExceptionCode.BAD_REQUEST);
    }
    if (!addRecipeDTO.difficulty().equals("EASY")
        && !addRecipeDTO.difficulty().equals("MEDIUM")
        && !addRecipeDTO.difficulty().equals("HARD")) {
      throw new RecipeManagementException(
          "Invalid Difficulty",
          "Difficulty must be either EASY, MEDIUM or HARD",
          ExceptionCode.BAD_REQUEST);
    }
  }

  @Override
  public void updateRecipe(Long recipeId, AddRecipeDTO updateRecipeDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "updateRecipe");
    validateRecipe(updateRecipeDTO);
    Recipes recipes = recipeRepository.getReferenceById(recipeId);
    recipes.setTitle(updateRecipeDTO.title());
    recipes.setDescription(updateRecipeDTO.description());
    recipes.setInstructions(updateRecipeDTO.instruction());
    recipes.setPrepareTimeMinutes(updateRecipeDTO.prepareTimeMinutes());
    recipes.setCookTimeMinutes(updateRecipeDTO.cookTimeMinutes());
    recipes.setServings(updateRecipeDTO.servings());
    recipes.setDifficulty(updateRecipeDTO.difficulty());
    recipes.setStatus(updateRecipeDTO.status());
    recipes.setCreatedBy(user.userId());
    recipes.setUpdatedBy(user.userId());
    recipeRepository.save(recipes);

    if (updateRecipeDTO.categoryId() != null && updateRecipeDTO.categoryId().size() > 0) {
      // Get all categories for this recipe
      List<RecipesCategory> recipesCategorieList =
          recipesCategoryRepository.findByRecipeId(recipeId);

      // Get all categories that are removed
      List<Long> removedCategory =
          recipesCategorieList.stream()
              .filter(
                  rc ->
                      !updateRecipeDTO
                          .categoryId()
                          .contains(rc.getCategoryId())) // if categoryId is removed
              .map(RecipesCategory::getRecipeCategoryId) // get recipeCategoryId
              .collect(Collectors.toList());
      if (removedCategory.size() > 0) {
        recipesCategoryRepository.deleteAllById(removedCategory);
      }

      // Get all categoryId from this recipe category
      Set<Long> existingCategoryId =
          recipesCategorieList.stream()
              .map(RecipesCategory::getCategoryId)
              .collect(Collectors.toSet());

      // Get all newly added categories
      List<Long> newCategoryIdList =
          updateRecipeDTO.categoryId().stream()
              .filter(categoryId -> !existingCategoryId.contains(categoryId))
              .collect(Collectors.toList());

      // Add new categories
      for (Long categoryId : newCategoryIdList) {
        RecipesCategory recipesCategory = new RecipesCategory();
        Category category = categoryRepository.getReferenceById(categoryId);
        recipesCategory.setRecipes(recipes);
        recipesCategory.setCategory(category);
        recipesCategory.setActiveFlag(CommonConstant.ACTIVE);
        recipesCategory.setCreatedBy(user.userId());
        recipesCategory.setUpdatedBy(user.userId());
        recipesCategoryRepository.save(recipesCategory);
      }
    } else {
      throw new RecipeManagementException(
          "Invalid Category", "Please add at least one category.", ExceptionCode.BAD_REQUEST);
    }

    if (updateRecipeDTO.ingredientList() != null && updateRecipeDTO.ingredientList().size() > 0) {
      // Get all ingredients for this recipe
      List<Ingredients> ingredientsList = ingredientsRepository.findByRecipeId(recipeId);

      // Get all ingredients that are removed
      List<Long> removedIngredient =
          ingredientsList.stream()
              .filter(
                  ingredient ->
                      !updateRecipeDTO.ingredientList().stream()
                          .map(AddRecipeIngredientsDTO::ingredientId)
                          .collect(Collectors.toList())
                          .contains(ingredient.getIngredientId()))
              .map(Ingredients::getIngredientId)
              .collect(Collectors.toList());
      if (removedIngredient.size() > 0) {
        ingredientsRepository.deleteAllById(removedIngredient);
      }

      for (AddRecipeIngredientsDTO addRecipeIngredientsDTO : updateRecipeDTO.ingredientList()) {
        Ingredients ingredients = new Ingredients();
        if (addRecipeIngredientsDTO.ingredientId() == null) {
          ingredients.setRecipes(recipes);
          ingredients.setActiveFlag(CommonConstant.ACTIVE);
          ingredients.setCreatedBy(user.userId());
          ingredients.setUpdatedBy(user.userId());
        } else {
          ingredients =
              ingredientsRepository.getReferenceById(addRecipeIngredientsDTO.ingredientId());
        }
        ingredients.setIngredientName(addRecipeIngredientsDTO.ingredientName());
        ingredients.setQuantity(addRecipeIngredientsDTO.quantity());
        ingredients.setUnit(addRecipeIngredientsDTO.unit());
        ingredientsRepository.save(ingredients);
      }
    } else {
      throw new RecipeManagementException(
          "Invalid Ingredient",
          "Please add ingredients for this recipe.",
          ExceptionCode.BAD_REQUEST);
    }
  }

  @Override
  @Transactional
  public void deleteRecipe(Long recipeId) {
    log.info(LogUtil.ENTRY_SERVICES, "deleteRecipe");
    // Delete all ingredients for this recipe
    // Cascade is set in Database therefore child data will be removed too
    recipeRepository.deleteById(recipeId);
  }
}
