package com.backend.recipeManagement.repository.jpa;

import com.backend.recipeManagement.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Long> {

  // @Lock(LockModeType.PESSIMISTIC_WRITE)
  // @Query("SELECT r FROM rm_recipes r WHERE r.recipe_id = :recipeId")
  // Recipes findByRecipeId(@Param("recipeId") Long recipeId);
}
