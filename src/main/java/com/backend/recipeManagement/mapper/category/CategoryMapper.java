package com.backend.recipeManagement.mapper.category;

import com.backend.recipeManagement.mapper.ColumnMapper;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "Represents a mapper Category List for mapping between requestParam and query")
public class CategoryMapper extends ColumnMapper {
  public CategoryMapper() {
    COLUMN_MAP.put("status", "status");
    COLUMN_MAP.put("categoryName", "categoryName");
  }
}
