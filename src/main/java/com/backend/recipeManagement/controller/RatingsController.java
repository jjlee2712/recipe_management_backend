package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.ratings.AddRatingsDTO;
import com.backend.recipeManagement.services.IAuthenticationService;
import com.backend.recipeManagement.services.IRatingsService;
import com.backend.recipeManagement.util.LogUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/ratings")
public class RatingsController {
  private final IRatingsService ratingsService;
  private final IAuthenticationService authenticationService;

  @Operation(summary = "POST Ratings", description = "POST API for Add Ratings")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully rated recipe",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @PostMapping("{recipeId}")
  public void rateRecipe(
      @PathVariable("recipeId") Long recipeId,
      @RequestBody AddRatingsDTO ratingsDTO,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "rateRecipe");
    UserDTO user = authenticationService.getUserDetails();
    ratingsService.rateRecipe(recipeId, ratingsDTO, user);
  }
}
