package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.recipe.AddRecipeDTO;
import com.backend.recipeManagement.services.IAuthenticationService;
import com.backend.recipeManagement.services.IRecipeService;
import com.backend.recipeManagement.util.LogUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/recipe")
@Tag(name = "Recipe Controller", description = "API for User to add and view recipes")
@AllArgsConstructor
public class RecipeController {
  private final IRecipeService recipeService;
  private final IAuthenticationService authenticationService;

  @Operation(summary = "Add new Recipe", description = "POST API for adding new recipe")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully added new recipe",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @PostMapping("")
  public void createRecipe(@RequestBody AddRecipeDTO addRecipeDTO, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "createRecipe");
    UserDTO user = authenticationService.getUserDetails();
    recipeService.createRecipe(addRecipeDTO, user);
  }

  @Operation(
      summary = "Update existing Recipe",
      description = "POST API for update existing recipe")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully updated recipe",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @PostMapping("/{recipeId}")
  public void updateRecipe(
      @PathVariable("recipeId") Long recipeId,
      @RequestBody AddRecipeDTO updateRecipeDTO,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "updateRecipe");
    UserDTO user = authenticationService.getUserDetails();
    recipeService.updateRecipe(recipeId, updateRecipeDTO, user);
  }
}
