package com.backend.recipeManagement.repository.jpa;

import com.backend.recipeManagement.model.Ingredients;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
  List<Ingredients> findByRecipeId(Long recipeId);
}
