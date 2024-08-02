package io.coolinary.smacker.recipeCategory;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RecipeCategoryService {

    @Autowired
    RecipeCategoryRepository recipeCategoryRepository;

    List<RecipeCategory> getAll() {
        return this.recipeCategoryRepository.findAll();
    }

    public boolean existsById(Long id) {
        return this.recipeCategoryRepository.findById(id).orElseThrow(() -> new RecipeCategoryNotFoundException(id)) != null;
    }

    public RecipeCategory getById(Long id) {
        return this.recipeCategoryRepository.findById(id).orElseThrow(() -> new RecipeCategoryNotFoundException(id));
    }

    public List<RecipeCategory> getCategoriesByRecipeId(Long recipeId) {
        return this.recipeCategoryRepository.findCategoriesByRecipesId(recipeId);
    }

    RecipeCategory createRecipeCategory(RecipeCategory recipeCategory) {
        return this.recipeCategoryRepository.save(recipeCategory);
    }

    public RecipeCategory updateRecipeCategory(Long id, RecipeCategory recipeCategory) {
        return this.recipeCategoryRepository.save(recipeCategory);
    }

    Boolean deleteRecipeCategory(Long id) {
        try {
            this.recipeCategoryRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }

    }

    Boolean deleteAllRecipeCategories() {
        this.recipeCategoryRepository.deleteAll();
        return true;
    }

}
