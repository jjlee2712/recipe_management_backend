package com.backend.recipeManagement.mapper;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.LinkedHashMap;
import java.util.Map;

@Schema(description = "Represents a column mapper functionality to map key pair value")
public class ColumnMapper {
  protected Map<String, String> COLUMN_MAP = new LinkedHashMap<>();

  public String getColumnName(String variableName) {
    if (variableName == null || variableName.isEmpty()) {
      return COLUMN_MAP.values().iterator().next();
    }

    String variableNameStr = variableName;
    String result = COLUMN_MAP.get(variableNameStr);
    if (result == null) {
      return COLUMN_MAP.values().iterator().next();
    }
    return result;
  }
}
