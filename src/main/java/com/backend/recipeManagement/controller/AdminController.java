package com.backend.recipeManagement.controller;

import com.backend.recipeManagement.dto.PaginationRequestDTO;
import com.backend.recipeManagement.dto.PaginationResponseDTO;
import com.backend.recipeManagement.dto.admin.AddCategoryDTO;
import com.backend.recipeManagement.dto.admin.CategoryListDTO;
import com.backend.recipeManagement.dto.admin.CategoryListRequestDTO;
import com.backend.recipeManagement.dto.authentication.UserDTO;
import com.backend.recipeManagement.services.IAdminService;
import com.backend.recipeManagement.services.IAuthenticationService;
import com.backend.recipeManagement.util.LogUtil;
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
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
  private final IAdminService adminService;
  private final IAuthenticationService authenticationService;

  @GetMapping("/category")
  public List<CategoryListDTO> getCategoryList(
      @RequestParam(required = false) String categoryName,
      @RequestParam(required = false) Character status,
      @RequestParam(required = false) String sort,
      @RequestParam(required = false) String sortDirection,
      @RequestParam(required = false) Long page,
      @RequestParam(required = false) Long size,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "getCategoryList");
    UserDTO user = authenticationService.getUserDetails();
    CategoryListRequestDTO requestDTO = new CategoryListRequestDTO(categoryName, status);
    PaginationRequestDTO paginationRequestDTO =
        new PaginationRequestDTO(sort, sortDirection, page, size);
    return adminService.getCategoryList(requestDTO, paginationRequestDTO, user);
  }

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

  @PostMapping("/category")
  public void createCategory(
      @RequestBody AddCategoryDTO addCategoryDTO, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "createCategory");
    UserDTO user = authenticationService.getUserDetails();
    adminService.createCategory(addCategoryDTO, user);
  }

  @PostMapping("/category/{categoryId}")
  public void updateCategory(
      @PathVariable("categoryId") Long categoryId,
      @RequestBody AddCategoryDTO updateCategoryDTO,
      Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "updateCategory");
    UserDTO user = authenticationService.getUserDetails();
    adminService.updateCategory(categoryId, updateCategoryDTO, user);
  }

  @DeleteMapping("/category/{categoryId}")
  public void deleteCategory(
      @PathVariable("categoryId") Long categoryId, Authentication authentication) {
    log.info(LogUtil.ENTRY_CONTROLLER, "deleteCategory");
    UserDTO user = authenticationService.getUserDetails();
    adminService.deleteCategory(categoryId, user);
  }
}
