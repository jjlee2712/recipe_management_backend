package com.backend.recipeManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for Storing Pagination RequestParam")
public record PaginationRequestDTO(String sort, String sortDirection, Long page, Long size) {}
