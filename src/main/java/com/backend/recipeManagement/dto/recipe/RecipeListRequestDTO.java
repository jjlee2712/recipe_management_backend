package com.backend.recipeManagement.dto.recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a DTO for storing recipe list request for filtering")
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
