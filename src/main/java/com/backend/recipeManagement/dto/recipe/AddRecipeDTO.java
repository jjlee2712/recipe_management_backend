package com.backend.recipeManagement.dto.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Represents a DTO for Adding new Recipe")
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
