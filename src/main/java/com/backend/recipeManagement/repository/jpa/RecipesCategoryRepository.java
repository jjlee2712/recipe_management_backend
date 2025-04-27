package com.backend.recipeManagement.repository.jpa;

import com.backend.recipeManagement.model.RecipesCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesCategoryRepository extends JpaRepository<RecipesCategory, Long> {
  List<RecipesCategory> findByRecipeId(Long recipeId);
}
