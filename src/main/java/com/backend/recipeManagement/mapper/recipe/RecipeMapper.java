package com.backend.recipeManagement.mapper.recipe;

import com.backend.recipeManagement.mapper.ColumnMapper;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a mapper Recipe List for mapping between requestParam and query")
public class RecipeMapper extends ColumnMapper {
  public RecipeMapper() {
    COLUMN_MAP.put("title", "title");
    COLUMN_MAP.put("description", "description");
    COLUMN_MAP.put("prepareTimeMinutes", "prepareTimeMinutes");
    COLUMN_MAP.put("cookTimeMinutes", "cookTimeMinutes");
    COLUMN_MAP.put("servings", "servings");
    COLUMN_MAP.put("difficulty", "difficulty");
    COLUMN_MAP.put("category", "category");
  }
}
