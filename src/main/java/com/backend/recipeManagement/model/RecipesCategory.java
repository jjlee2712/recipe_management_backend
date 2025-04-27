package com.backend.recipeManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "rm_recipes_category")
@AllArgsConstructor
@NoArgsConstructor
public class RecipesCategory {
  @Id
  @Column(name = "recipe_category_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long recipeCategoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recipe_id", insertable = true, updatable = true)
  private Recipes recipes;

  @Column(name = "recipe_id", insertable = false, updatable = false)
  private Long recipeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", insertable = true, updatable = true)
  private Category category;

  @Column(name = "category_id", insertable = false, updatable = false)
  private Long categoryId;

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
