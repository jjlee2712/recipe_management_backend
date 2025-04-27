package com.backend.recipeManagement.dto.recipe;

import java.math.BigDecimal;

public record AddRecipeIngredientsDTO(
    Long ingredientId, String ingredientName, BigDecimal quantity, String unit) {}
