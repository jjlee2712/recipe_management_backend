package com.backend.recipeManagement.dto.recipe;

import java.math.BigDecimal;

public record RecipeIngredientListDTO(
    Long ingredientId, String ingredientName, BigDecimal quantity, String unit) {}
