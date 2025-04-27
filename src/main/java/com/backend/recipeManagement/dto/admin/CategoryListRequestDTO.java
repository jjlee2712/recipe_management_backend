package com.backend.recipeManagement.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for Storing Category List RequestParam")
public record CategoryListRequestDTO(String categoryName, Character status) {}
