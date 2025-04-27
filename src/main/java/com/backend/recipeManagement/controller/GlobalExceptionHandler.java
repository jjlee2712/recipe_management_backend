package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.exception.RecipeManagementException;
import com.backend.recipeManagement.exception.RecipeManagementExceptionHandler;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RecipeManagementException.class)
  public ResponseEntity<ProblemDetail> handleException(RecipeManagementException e) {
    return RecipeManagementExceptionHandler.handleException(e);
  }
}
