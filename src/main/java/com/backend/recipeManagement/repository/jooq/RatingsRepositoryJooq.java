package com.backend.recipeManagement.repository.jooq;

import static org.jooq.impl.DSL.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class RatingsRepositoryJooq {
  DSLContext dsl;
}
