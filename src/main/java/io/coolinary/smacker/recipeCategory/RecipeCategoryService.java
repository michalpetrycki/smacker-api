package io.coolinary.smacker.recipeCategory;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RecipeCategoryService {

    @Autowired
    RecipeCategoryRepository recipeCategoryRepository;

    List<RecipeCategory> getAll() {
        return this.recipeCategoryRepository.findAll();
    }

    public boolean existsByPublicId(UUID publicId) {
        return this.recipeCategoryRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RecipeCategoryNotFoundException(publicId)) != null;
    }

    public RecipeCategory getByPublicId(UUID publicId) {
        return this.recipeCategoryRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RecipeCategoryNotFoundException(publicId));
    }

    public List<RecipeCategory> getCategoriesByRecipePublicId(UUID recipePublicId) {
        return this.recipeCategoryRepository.findCategoriesByRecipesPublicId(recipePublicId);
    }

    RecipeCategory createRecipeCategory(RecipeCategory recipeCategory) {
        return this.recipeCategoryRepository.save(recipeCategory);
    }

    public RecipeCategory updateRecipeCategory(RecipeCategory recipeCategory) {
        return this.recipeCategoryRepository.save(recipeCategory);
    }

    Boolean deleteRecipeCategoryByPublicId(UUID publicId) {
        try {
            this.recipeCategoryRepository.deleteByPublicId(publicId);
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
