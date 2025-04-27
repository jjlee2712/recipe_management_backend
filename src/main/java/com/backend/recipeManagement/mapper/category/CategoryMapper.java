package com.backend.recipeManagement.mapper.category;

import com.backend.recipeManagement.mapper.ColumnMapper;

public class CategoryMapper extends ColumnMapper {
  public CategoryMapper() {
    COLUMN_MAP.put("status", "status");
    COLUMN_MAP.put("categoryName", "categoryName");
  }
}
