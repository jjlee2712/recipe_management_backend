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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "rm_ingredients")
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients {
  @Id
  @Column(name = "ingredient_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ingredientId;

  private String ingredientName;

  private BigDecimal quantity;

  private String unit;

  private LocalDateTime createdDate;

  private LocalDateTime updatedDate;

  private Long createdBy;

  private Long updatedBy;

  private Character activeFlag;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recipe_id", insertable = true, updatable = true)
  private Recipes recipes;

  @Column(name = "recipe_id", insertable = false, updatable = false)
  private Long recipeId;

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
