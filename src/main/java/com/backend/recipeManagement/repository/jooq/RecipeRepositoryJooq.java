package com.backend.recipeManagement.repository.jooq;

import static org.jooq.impl.DSL.*;

import com.backend.recipeManagement.constant.CommonConstant;
import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.recipe.RecipeDTO;
import com.backend.recipeManagement.dto.recipe.RecipeListDTO;
import com.backend.recipeManagement.dto.recipe.RecipeListRequestDTO;
import com.backend.recipeManagement.util.CommonUtil;
import com.backend.recipeManagement.util.JooqUtil;
import com.backend.recipeManagement.util.LogUtil;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.Result;
import org.jooq.SelectHavingStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitPercentAfterOffsetStep;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class RecipeRepositoryJooq {
  DSLContext dsl;

  public List<RecipeListDTO> getRecipeList(
      RecipeListRequestDTO requestDTO, PaginationRequestDTO pg) {
    log.info(LogUtil.ENTRY_REPOSITORY, "getRecipeList");
    Condition condition = noCondition();
    condition = condition.and(field("REC.status").eq(CommonConstant.ACTIVE.toString()));
    condition = condition.and(field("REC.active_flag").eq(CommonConstant.ACTIVE.toString()));

    if (CommonUtil.isNotEmpty(requestDTO.title())) {
      condition = condition.and(field("REC.title").containsIgnoreCase(requestDTO.title()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.prepareTimeFrom())) {
      condition = condition.and(field("REC.prepare_time_minutes").ge(requestDTO.prepareTimeFrom()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.prepareTimeTo())) {
      condition = condition.and(field("REC.prepare_time_minutes").le(requestDTO.prepareTimeTo()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.cookTimeFrom())) {
      condition = condition.and(field("REC.cook_time_minutes").ge(requestDTO.cookTimeFrom()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.cookTimeTo())) {
      condition = condition.and(field("REC.cook_time_minutes").le(requestDTO.cookTimeTo()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.servingsFrom())) {
      condition = condition.and(field("REC.servings").ge(requestDTO.servingsFrom()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.servingsTo())) {
      condition = condition.and(field("REC.servings").le(requestDTO.servingsTo()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.difficulty())) {
      condition = condition.and(field("REC.difficulty").eq(requestDTO.difficulty()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.category())) {
      condition = condition.and(field("CAT.category_name").in(requestDTO.category()));
    }

    Field<Long> recipeId = field("REC.recipe_id", Long.class).as("recipeId");
    Field<String> title = field("REC.title", String.class).as("title");
    Field<String> description = field("REC.description", String.class).as("description");
    Field<Long> prepareTimeMinutes =
        field("REC.prepare_time_minutes", Long.class).as("prepareTimeMinutes");
    Field<Long> cookTimeMinutes = field("REC.cook_time_minutes", Long.class).as("cookTimeMinutes");
    Field<Long> servings = field("REC.servings", Long.class).as("servings");
    Field<String> difficulty = field("REC.difficulty", String.class).as("difficulty");
    Field<String> category =
        groupConcat(field("CAT.category_name"))
            .orderBy(field("CAT.category_name").asc())
            .separator(", ")
            .as("category");

    SelectLimitPercentAfterOffsetStep<
            Record8<Long, String, String, Long, Long, Long, String, String>>
        query =
            dsl.select(
                    recipeId,
                    title,
                    description,
                    prepareTimeMinutes,
                    cookTimeMinutes,
                    servings,
                    difficulty,
                    category)
                .from(table("rm_recipes REC"))
                .leftJoin(table("rm_recipes_category RC"))
                .on(field("REC.recipe_id").eq(field("RC.recipe_id")))
                .leftJoin(table("rm_category CAT"))
                .on(field("RC.category_id").eq(field("CAT.category_id")))
                .where(condition)
                .groupBy(field("REC.recipe_id"))
                .orderBy(JooqUtil.getOrderByField(pg.sort(), pg.sortDirection()))
                .offset((pg.page() - 1) * pg.size())
                .limit(pg.size());

    log.info(LogUtil.QUERY, query);

    return query.fetchInto(RecipeListDTO.class);
  }

  public Long getRecipeListPages(RecipeListRequestDTO requestDTO) {
    log.info(LogUtil.ENTRY_REPOSITORY, "getRecipeListPages");
    Condition condition = noCondition();
    condition = condition.and(field("REC.status").eq(CommonConstant.ACTIVE.toString()));
    condition = condition.and(field("REC.active_flag").eq(CommonConstant.ACTIVE.toString()));

    if (CommonUtil.isNotEmpty(requestDTO.title())) {
      condition = condition.and(field("REC.title").containsIgnoreCase(requestDTO.title()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.prepareTimeFrom())) {
      condition = condition.and(field("REC.prepare_time_minutes").ge(requestDTO.prepareTimeFrom()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.prepareTimeTo())) {
      condition = condition.and(field("REC.prepare_time_minutes").le(requestDTO.prepareTimeTo()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.cookTimeFrom())) {
      condition = condition.and(field("REC.cook_time_minutes").ge(requestDTO.cookTimeFrom()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.cookTimeTo())) {
      condition = condition.and(field("REC.cook_time_minutes").le(requestDTO.cookTimeTo()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.servingsFrom())) {
      condition = condition.and(field("REC.servings").ge(requestDTO.servingsFrom()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.servingsTo())) {
      condition = condition.and(field("REC.servings").le(requestDTO.servingsTo()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.difficulty())) {
      condition = condition.and(field("REC.difficulty").eq(requestDTO.difficulty()));
    }
    if (CommonUtil.isNotEmpty(requestDTO.category())) {
      condition = condition.and(field("CAT.category_name").eq(requestDTO.category()));
    }
    Field<Long> recipeId = field("REC.recipe_id", Long.class).as("recipeId");
    Field<String> category =
        groupConcat(field("CAT.category_name"))
            .orderBy(field("CAT.category_name").asc())
            .separator(", ")
            .as("category");

    SelectJoinStep<Record1<Integer>> query =
        dsl.selectCount()
            .from(
                select(recipeId, category)
                    .from(table("rm_recipes REC"))
                    .leftJoin(table("rm_recipes_category RC"))
                    .on(field("REC.recipe_id").eq(field("RC.recipe_id")))
                    .leftJoin(table("rm_category CAT"))
                    .on(field("RC.category_id").eq(field("CAT.category_id")))
                    .where(condition)
                    .groupBy(field("REC.recipe_id")));

    log.info(LogUtil.QUERY, query);

    return query.fetchOneInto(Long.class);
  }

  public RecipeDTO getRecipe(Long recipeId) {
    log.info(LogUtil.ENTRY_REPOSITORY, "getRecipe");
    Condition condition = noCondition();
    condition = condition.and(field("REC.recipe_id").eq(recipeId));

    Field<String> title = field("REC.title", String.class).as("title");
    Field<String> description = field("REC.description", String.class).as("description");
    Field<String> instructions = field("REC.instructions", String.class).as("instructions");
    Field<Long> prepareTimeMinutes =
        field("REC.prepare_time_minutes", Long.class).as("prepareTimeMinutes");
    Field<Long> cookTimeMinutes = field("REC.cook_time_minutes", Long.class).as("cookTimeMinutes");
    Field<Long> servings = field("REC.servings", Long.class).as("servings");
    Field<String> difficulty = field("REC.difficulty", String.class).as("difficulty");
    Field<String> category =
        groupConcat(field("CAT.category_name"))
            .orderBy(field("CAT.category_name").asc())
            .separator(", ")
            .as("category");
    Field<Long> ingredientId = field("ING.ingredient_id", Long.class).as("ingredientId");
    Field<String> ingredientName = field("ING.ingredient_name", String.class).as("ingredientName");
    Field<BigDecimal> quantity = round(field("ING.quantity", BigDecimal.class), 2).as("quantity");
    Field<String> unit = field("ING.unit", String.class).as("unit");

    SelectHavingStep<
            Record9<
                String,
                String,
                String,
                Long,
                Long,
                Long,
                String,
                String,
                Result<Record4<Long, String, BigDecimal, String>>>>
        query =
            dsl.select(
                    title,
                    description,
                    instructions,
                    prepareTimeMinutes,
                    cookTimeMinutes,
                    servings,
                    difficulty,
                    category,
                    multiset(
                        select(ingredientId, ingredientName, quantity, unit)
                            .from(table("rm_ingredients ING"))
                            .where(field("ING.recipe_Id").eq(field("REC.recipe_id")))))
                .from(table("rm_recipes REC"))
                .leftJoin(table("rm_recipes_category RC"))
                .on(field("REC.recipe_id").eq(field("RC.recipe_id")))
                .leftJoin(table("rm_category CAT"))
                .on(field("RC.category_id").eq(field("CAT.category_id")))
                .where(condition)
                .groupBy(field("REC.recipe_id"));

    log.info(LogUtil.QUERY, query);
    return query.fetchOneInto(RecipeDTO.class);
  }
}
