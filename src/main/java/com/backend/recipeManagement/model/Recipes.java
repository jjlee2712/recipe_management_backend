package com.backend.recipeManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "rm_recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipes {
  @Id
  @Column(name = "recipe_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long recipeId;

  private String title;

  private String description;

  private String instructions;

  private Long prepareTimeMinutes;

  private Long cookTimeMinutes;

  private Long servings;

  private String difficulty;

  private Character status;

  private LocalDateTime createdDate;

  private LocalDateTime updatedDate;

  private Long createdBy;

  private Long updatedBy;

  private Character activeFlag;

  @PrePersist
  protected void onCreate() {
    createdDate = LocalDateTime.now();
    updatedDate = createdDate;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedDate = LocalDateTime.now();
  }
}
