package com.backend.recipeManagement.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a DTO for returning the List of Category")
public record CategoryListDTO(Long categoryId, String categoryName, String status) {}
