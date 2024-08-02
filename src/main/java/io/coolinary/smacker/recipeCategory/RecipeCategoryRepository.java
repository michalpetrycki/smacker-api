package io.coolinary.smacker.recipeCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {

    List<RecipeCategory> findCategoriesByRecipesId(Long recipeId);

}
