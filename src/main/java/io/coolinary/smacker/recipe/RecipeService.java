package io.coolinary.smacker.recipe;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    List<RecipeEntity> getAll() {
        return this.recipeRepository.findAll();
    }

    public boolean existsByPublicId(UUID publicId) {
        return this.recipeRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.RECIPE)) != null;
    }

    public RecipeEntity getByPublicId(UUID publicId) {
        return this.recipeRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.RECIPE));
    }

    public List<RecipeEntity> getRecipesByCategoriesId(UUID categoryId) {
        return this.recipeRepository.findRecipesByCategoriesPublicId(categoryId);
    }

    public RecipeEntity createRecipe(RecipeEntity recipe) {
        return this.recipeRepository.save(recipe);
    }

    RecipeEntity updateRecipe(RecipeEntity recipe) {
        return this.recipeRepository.save(recipe);
    }

    Boolean deleteRecipe(UUID publicId) {
        try {
            this.recipeRepository.deleteByPublicId(publicId);
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
