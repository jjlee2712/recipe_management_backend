package com.backend.recipeManagement.dto.authentication;

public record UserDTO(Long userId, String username, String fullName, String roles) {}
