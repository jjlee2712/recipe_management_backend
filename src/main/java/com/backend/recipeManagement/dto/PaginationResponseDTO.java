package com.backend.recipeManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for Returned Pagination result")
public record PaginationResponseDTO(Long totalPages, Long total, Long size) {}
