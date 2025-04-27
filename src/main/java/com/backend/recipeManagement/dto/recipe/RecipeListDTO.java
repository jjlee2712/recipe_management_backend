package com.backend.recipeManagement.dto.recipe;

public record RecipeListDTO(
    Long recipeId,
    String title,
    String description,
    Long prepareTimeMinutes,
    Long cookTimeMinutes,
    Long servings,
    String difficulty,
    String category) {}
