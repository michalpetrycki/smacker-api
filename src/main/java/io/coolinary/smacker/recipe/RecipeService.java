package io.coolinary.smacker.recipe;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    List<Recipe> getAll() {
        return this.recipeRepository.findAll();
    }

    public boolean existsById(Long id) {
        return this.recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id)) != null;
    }

    public Recipe getById(Long id) {
        return this.recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    public List<Recipe> getRecipesByCategoriesId(Long categoryId) {
        return this.recipeRepository.findRecipesByCategoriesId(categoryId);
    }

    public Recipe createRecipe(Recipe recipe) {
        return this.recipeRepository.save(recipe);
    }

    Recipe updateRecipe(Long id, Recipe recipe) {
        return this.recipeRepository.save(recipe);
    }

    Boolean deleteRecipe(Long id) {
        try {
            this.recipeRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    Boolean deleteAllRecipes() {
        this.recipeRepository.deleteAll();
        return true;
    }

}
