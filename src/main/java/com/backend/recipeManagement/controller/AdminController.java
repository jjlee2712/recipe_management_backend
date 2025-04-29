package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.PaginationResponseDTO;
import com.backend.recipeManagement.dto.admin.AddCategoryDTO;
import com.backend.recipeManagement.dto.admin.CategoryListDTO;
import com.backend.recipeManagement.dto.admin.CategoryListRequestDTO;
import com.backend.recipeManagement.dto.admin.InactiveRecipeDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.exception.ExceptionCode;
import com.backend.recipeManagement.exception.RecipeManagementException;
import com.backend.recipeManagement.services.IAdminService;
import com.backend.recipeManagement.services.IAuthenticationService;
import com.backend.recipeManagement.util.LogUtil;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Duration;
import java.util.List;
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
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin Controller", description = "API for Admin to manage different master data")
public class AdminController {
  private final IAdminService adminService;
  private final IAuthenticationService authenticationService;
  private final Bucket bucket;

  public AdminController(IAdminService adminService, IAuthenticationService authenticationService) {
    this.adminService = adminService;
    this.authenticationService = authenticationService;
    Bandwidth limit = Bandwidth.classic(2, Refill.of(2, Duration.ofMinutes(1)));
    this.bucket = Bucket4j.builder().addLimit(limit).build();
  }

  @Operation(summary = "Get Category List", description = "Returns all recipe category")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "List of category successfully retrieved",
        content =
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = CategoryListDTO.class))))
  })
  @GetMapping("/category")
  public List<CategoryListDTO> getCategoryList(
      @RequestParam(required = false) String categoryName,
      @RequestParam(required = false) Character status,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String sortDirection,
      @RequestParam(required = false) Long page,
      @RequestParam(required = false) Long size,
      Authentication authentication) {
    if (bucket.tryConsume(1)) {
      log.info(LogUtil.ENTRY_CONTROLLER, "getCategoryList");
      UserDTO user = authenticationService.getUserDetails();
      CategoryListRequestDTO requestDTO = new CategoryListRequestDTO(categoryName, status);
      PaginationRequestDTO paginationRequestDTO =
          new PaginationRequestDTO(sort, sortDirection, page, size);
      return adminService.getCategoryList(requestDTO, paginationRequestDTO, user);
    } else {
      throw new RecipeManagementException(
          "429", "Too many requests", ExceptionCode.TOO_MANY_REQUESTS);
    }
  }

  @Operation(
      summary = "Get Category List Pages",
      description = "Return the pagination for the category list")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Pagination successfully retrieved",
        content =
            @Content(
                array =
                    @ArraySchema(schema = @Schema(implementation = PaginationResponseDTO.class))))
  })
  @GetMapping("/category/page")
  public PaginationResponseDTO getCategoryListPages(
      @RequestParam(required = false) String categoryName,
      @RequestParam(required = false) Character status,
      @RequestParam(required = false) Long size,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "getCategoryListPages");
    UserDTO user = authenticationService.getUserDetails();
    CategoryListRequestDTO requestDTO = new CategoryListRequestDTO(categoryName, status);
    PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(null, null, null, size);
    return adminService.getCategoryListPages(requestDTO, paginationRequestDTO, user);
  }

  @Operation(
      summary = "Create new Recipe Category",
      description = "POST API for creating new recipe category")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully added new category",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @PostMapping("/category")
  public void createCategory(
      @RequestBody AddCategoryDTO addCategoryDTO, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "createCategory");
    UserDTO user = authenticationService.getUserDetails();
    adminService.createCategory(addCategoryDTO, user);
  }

  @Operation(
      summary = "Update existing Recipe Category",
      description = "POST API for updating existing recipe category")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully updated category",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @PostMapping("/category/{categoryId}")
  public void updateCategory(
      @PathVariable("categoryId") Long categoryId,
      @RequestBody AddCategoryDTO updateCategoryDTO,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "updateCategory");
    UserDTO user = authenticationService.getUserDetails();
    adminService.updateCategory(categoryId, updateCategoryDTO, user);
  }

  @Operation(
      summary = "Delete existing Recipe Category",
      description = "Delete API for delete existing recipe category")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Successfully deleted category",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = void.class))))
  })
  @DeleteMapping("/category/{categoryId}")
  public void deleteCategory(
      @PathVariable("categoryId") Long categoryId, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "deleteCategory");
    UserDTO user = authenticationService.getUserDetails();
    adminService.deleteCategory(categoryId, user);
  }

  @PostMapping("/recipe/{recipeId}/inactive")
  public void inactiveRecipe(
      @PathVariable("recipeId") Long recipeId,
      @RequestBody InactiveRecipeDTO inactiveRecipeDTO,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "inactiveRecipe");
    UserDTO user = authenticationService.getUserDetails();
    adminService.inactiveRecipe(recipeId, inactiveRecipeDTO, user);
  }

  @PostMapping("/recipe/{recipeId}/active")
  public void activeRecipe(@PathVariable("recipeId") Long recipeId, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "activeRecipe");
    UserDTO user = authenticationService.getUserDetails();
    adminService.activeRecipe(recipeId, user);
  }
}
