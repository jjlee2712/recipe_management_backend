package com.backend.recipeManagement.repository.jooq;

import static org.jooq.impl.DSL.*;

import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.admin.CategoryListDTO;
import com.backend.recipeManagement.dto.admin.CategoryListRequestDTO;
import com.backend.recipeManagement.util.CommonUtil;
import com.backend.recipeManagement.util.JooqUtil;
import com.backend.recipeManagement.util.LogUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.SelectConditionStep;
import org.jooq.SelectLimitPercentAfterOffsetStep;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class CategoryRepositoryJooq {
  DSLContext dsl;

  public List<CategoryListDTO> getCategoryList(
      CategoryListRequestDTO requestDTO, PaginationRequestDTO pgDto) {
    log.info(LogUtil.ENTRY_REPOSITORY, "getCategoryList");
    Condition condition = noCondition();
    if (CommonUtil.isNotEmpty(requestDTO.categoryName())) {
      condition =
          condition.and(field("CAT.category_name").containsIgnoreCase(requestDTO.categoryName()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.status())) {
      condition = condition.and(field("CAT.active_flag").eq(requestDTO.status().toString()));
    }

    Field<Long> categoryId = field("CAT.category_id", Long.class).as("categoryId");
    Field<String> categoryName = field("CAT.category_name", String.class).as("categoryName");
    Field<String> status =
        when(field("CAT.active_flag").eq("A"), val("Active"))
            .otherwise(val("Inactive"))
            .as("status");

    SelectLimitPercentAfterOffsetStep<Record3<Long, String, String>> query =
        dsl.select(categoryId, categoryName, status)
            .from(table("rm_category CAT"))
            .where(condition)
            .orderBy(JooqUtil.getOrderByField(pgDto.sort(), pgDto.sortDirection()))
            .offset((pgDto.page() - 1) * pgDto.size())
            .limit(pgDto.size());

    log.info(LogUtil.QUERY, query);

    return query.fetchInto(CategoryListDTO.class);
  }

  public Long getCategoryListPages(CategoryListRequestDTO requestDTO) {
    log.info(LogUtil.ENTRY_REPOSITORY, "getCategoryListPages");
    Condition condition = noCondition();
    if (CommonUtil.isNotEmpty(requestDTO.categoryName())) {
      condition =
          condition.and(field("CAT.category_name").containsIgnoreCase(requestDTO.categoryName()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.status())) {
      condition = condition.and(field("CAT.active_flag").eq(requestDTO.status().toString()));
    }

    SelectConditionStep<Record1<Integer>> query =
        dsl.selectCount().from(table("rm_category CAT")).where(condition);

    log.info(LogUtil.QUERY, query);

    return query.fetchOneInto(Long.class);
  }
}
