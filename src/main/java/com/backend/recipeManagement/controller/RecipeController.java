package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.PaginationResponseDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.dto.recipe.AddRecipeDTO;
import com.backend.recipeManagement.dto.recipe.RecipeListDTO;
import com.backend.recipeManagement.dto.recipe.RecipeListRequestDTO;
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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/recipe")
@Tag(
    name = "Recipe Controller",
    description = "API for User to add, update, delete and view recipes")
@AllArgsConstructor
public class RecipeController {
  private final IRecipeService recipeService;
  private final IAuthenticationService authenticationService;

  @Operation(summary = "GET Recipe List", description = "GET API for Recipe List")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieve Recipe List",
        content =
            @Content(array = @ArraySchema(schema = @Schema(implementation = RecipeListDTO.class))))
  })
  @GetMapping("")
  public List<RecipeListDTO> getRecipeList(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) Long prepareTimeFrom,
      @RequestParam(required = false) Long prepareTimeTo,
      @RequestParam(required = false) Long cookTimeFrom,
      @RequestParam(required = false) Long cookTimeTo,
      @RequestParam(required = false) Long servingsFrom,
      @RequestParam(required = false) Long servingsTo,
      @RequestParam(required = false) String difficulty,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String sortDirection,
      @RequestParam(required = false) Long page,
      @RequestParam(required = false) Long size,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "getRecipeList");
    UserDTO user = authenticationService.getUserDetails();
    RecipeListRequestDTO requestDTO =
        new RecipeListRequestDTO(
            title,
            prepareTimeFrom,
            prepareTimeTo,
            cookTimeFrom,
            cookTimeTo,
            servingsFrom,
            servingsTo,
            difficulty,
            category);
    PaginationRequestDTO paginationRequestDTO =
        new PaginationRequestDTO(sort, sortDirection, page, size);
    return recipeService.getRecipeList(requestDTO, paginationRequestDTO, user);
  }

  @Operation(summary = "GET Recipe List Pages", description = "GET API for Recipe List Pages")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieve total pages for Recipe List",
        content =
            @Content(
                array =
                    @ArraySchema(schema = @Schema(implementation = PaginationResponseDTO.class))))
  })
  @GetMapping("/page")
  public PaginationResponseDTO getRecipeListPages(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) Long prepareTimeFrom,
      @RequestParam(required = false) Long prepareTimeTo,
      @RequestParam(required = false) Long cookTimeFrom,
      @RequestParam(required = false) Long cookTimeTo,
      @RequestParam(required = false) Long servingsFrom,
      @RequestParam(required = false) Long servingsTo,
      @RequestParam(required = false) String difficulty,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) Long size,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "getRecipeListPages");
    UserDTO user = authenticationService.getUserDetails();
    RecipeListRequestDTO requestDTO =
        new RecipeListRequestDTO(
            title,
            prepareTimeFrom,
            prepareTimeTo,
            cookTimeFrom,
            cookTimeTo,
            servingsFrom,
            servingsTo,
            difficulty,
            category);
    PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(null, null, null, size);
    return recipeService.getRecipeListPages(requestDTO, paginationRequestDTO, user);
  }

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

  @Operation(
      summary = "Delete existing Recipe",
      description = "Delete API for delete existing recipe")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully deleted recipe",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @DeleteMapping("/{recipeId}")
  public void deleteRecipe(@PathVariable("recipeId") Long recipeId) {
    log.info(LogUtil.ENTRY_CONTROLLER, "deleteRecipe");
    recipeService.deleteRecipe(recipeId);
  }
}
