package com.backend.recipeManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class RecipeManagementException extends RuntimeException {
  private String title;
  private String message;
  private ExceptionCode exceptionCode;

  public RecipeManagementException(String title, String message, ExceptionCode exceptionCode) {
    this.title = title;
    this.message = message;
    this.exceptionCode = exceptionCode;
  }

  public String title() {
    return title;
  }

  public String message() {
    return message;
  }

  public ExceptionCode exceptionCode() {
    return exceptionCode;
  }

  public ProblemDetail response(HttpStatus status) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.valueOf(status.name()));

    problemDetail.setTitle(title);
    problemDetail.setDetail(message);
    problemDetail.setProperty("code", exceptionCode.name());
    problemDetail.setProperty("Error", "Recipe Management Error");
    return problemDetail;
  }
}
