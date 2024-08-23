package io.coolinary.smacker.recipeCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {

    List<RecipeCategory> findCategoriesByRecipesPublicId(UUID recipePublicId);

    Optional<RecipeCategory> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID publicId);

}
