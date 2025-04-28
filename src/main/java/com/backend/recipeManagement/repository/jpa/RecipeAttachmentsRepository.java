package com.backend.recipeManagement.repository.jpa;

import com.backend.recipeManagement.model.RecipeAttachments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeAttachmentsRepository extends JpaRepository<RecipeAttachments, Long> {}
