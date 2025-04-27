package com.backend.recipeManagement.dto;

public record PaginationRequestDTO(String sort, String sortDirection, Long page, Long size) {}
