package com.backend.recipeManagement.dto.recipe;

import java.util.List;

public record AddRecipeDTO(
    String title,
    String description,
    String instruction,
    Long prepareTimeMinutes,
    Long cookTimeMinutes,
    Long servings,
    String difficulty,
    Character status,
    List<Long> categoryId,
    List<AddRecipeIngredientsDTO> ingredientList) {}
