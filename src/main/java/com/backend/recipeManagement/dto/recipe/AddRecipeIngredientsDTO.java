package com.backend.recipeManagement.dto.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Represents a DTO adding recipe ingredients")
public record AddRecipeIngredientsDTO(
    Long ingredientId, String ingredientName, BigDecimal quantity, String unit) {}
