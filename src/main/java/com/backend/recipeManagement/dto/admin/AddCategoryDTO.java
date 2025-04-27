package com.backend.recipeManagement.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a DTO for Adding new Category")
public record AddCategoryDTO(String categoryName, Boolean status) {}
