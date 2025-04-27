package com.backend.recipeManagement.dto.recipe;

import java.math.BigDecimal;

public record UpdateRecipeIngredientDTO(
    Long ingredientId, String ingredientName, BigDecimal quantity, String unit) {}
