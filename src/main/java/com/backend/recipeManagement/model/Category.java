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

@Data
@Entity(name = "rm_category")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
  @Id
  @Column(name = "category_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;

  private String categoryName;

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
