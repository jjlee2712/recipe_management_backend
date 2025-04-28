package com.backend.recipeManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class RecipeManagementExceptionHandler {
  public static ResponseEntity<ProblemDetail> handleException(RecipeManagementException e) {
    return switch (e.exceptionCode()) {
      case BAD_REQUEST -> ResponseEntity.badRequest().body(e.response(HttpStatus.BAD_REQUEST));
      case NOT_FOUND -> ResponseEntity.status(204).body(e.response(HttpStatus.NOT_FOUND));
      case CONFLICT -> ResponseEntity.status(409).body(e.response(HttpStatus.CONFLICT));
      case INTERNAL_SERVER_ERROR ->
          ResponseEntity.status(500).body(e.response(HttpStatus.INTERNAL_SERVER_ERROR));
    };
  }
}
