package com.backend.recipeManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "rm_ratings")
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {
  @Id
  @Column(name = "ratings_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ratingsId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recipe_id", insertable = true, updatable = true)
  private Recipes recipes;

  @Column(name = "recipe_id", insertable = false, updatable = false)
  private Long recipeId;
}
