package com.backend.recipeManagement.dto.recipe;


public record RecipeDTO(
    String title,
    String description,
    String instructions,
    Long prepareTimeMinutes,
    Long cookTimeMinutes,
    Long servings,
    String difficulty,
    String category,
    RecipeIngredientListDTO[] ingredientList) {}
