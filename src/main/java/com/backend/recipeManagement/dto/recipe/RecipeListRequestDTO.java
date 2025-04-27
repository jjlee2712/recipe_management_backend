package com.backend.recipeManagement.dto.recipe;

public record RecipeListRequestDTO(
    String title,
    Long prepareTimeFrom,
    Long prepareTimeTo,
    Long cookTimeFrom,
    Long cookTimeTo,
    Long servingsFrom,
    Long servingsTo,
    String difficulty,
    String category) {}
