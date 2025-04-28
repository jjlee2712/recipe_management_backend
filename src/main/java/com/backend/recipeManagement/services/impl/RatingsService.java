package com.backend.recipeManagement.services.impl;

import com.backend.recipeManagement.constant.CommonConstant;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.ratings.AddRatingsDTO;
import com.backend.recipeManagement.exception.ExceptionCode;
import com.backend.recipeManagement.exception.RecipeManagementException;
import com.backend.recipeManagement.model.Ratings;
import com.backend.recipeManagement.model.Recipes;
import com.backend.recipeManagement.repository.jooq.RatingsRepositoryJooq;
import com.backend.recipeManagement.repository.jpa.RatingsRepository;
import com.backend.recipeManagement.repository.jpa.RecipeRepository;
import com.backend.recipeManagement.services.IRatingsService;
import com.backend.recipeManagement.util.LogUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class RatingsService implements IRatingsService {
  private final RatingsRepositoryJooq ratingsRepositoryJooq;
  private final RecipeRepository recipeRepository;
  private final RatingsRepository ratingsRepository;

  @Override
  @Transactional
  public void rateRecipe(Long recipeId, AddRatingsDTO ratingsDTO, UserDTO user) {
    log.info(LogUtil.ENTRY_SERVICES, "rateRecipe");
    validateRatings(ratingsDTO);
    Ratings ratings = new Ratings();
    Recipes recipes = recipeRepository.getReferenceById(recipeId);
    ratings.setRating(ratingsDTO.ratings());
    ratings.setRemarks(ratingsDTO.remarks());
    ratings.setRecipes(recipes);
    ratings.setActiveFlag(CommonConstant.ACTIVE);
    ratings.setCreatedBy(user.userId());
    ratings.setUpdatedBy(user.userId());
    ratingsRepository.save(ratings);
  }

  private void validateRatings(AddRatingsDTO ratingsDTO) {
    log.info(LogUtil.ENTRY_SERVICES, "validateRatings");
    if (ratingsDTO.ratings() == null) {
      throw new RecipeManagementException(
          "Invalid Ratings", "Ratings is required", ExceptionCode.BAD_REQUEST);
    }

    if (ratingsDTO.ratings() < 1 || ratingsDTO.ratings() > 5) {
      throw new RecipeManagementException(
          "Invalid Ratings", "Ratings must be between 1 and 5", ExceptionCode.BAD_REQUEST);
    }
  }
}
