package com.backend.recipeManagement.dto.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a DTO for Retrieving Recipe List")
public record RecipeListDTO(
    Long recipeId,
    String title,
    String description,
    Long prepareTimeMinutes,
    Long cookTimeMinutes,
    Long servings,
    String difficulty,
    String category) {}
