package io.coolinary.smacker.recipe;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findRecipesByCategoriesId(Long categoryId);

}
