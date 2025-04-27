package com.backend.recipeManagement.repository.jpa;

import com.backend.recipeManagement.model.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {}
