package com.backend.recipeManagement.util;

import static org.jooq.impl.DSL.*;

import org.jooq.SortField;

public class JooqUtil {
  public static SortField<Object> getOrderByField(String sort, String sortDirection) {
    return sortDirection.equals("asc") ? field(name(sort)).asc() : field(name(sort)).desc();
  }
}
