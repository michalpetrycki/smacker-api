package io.coolinary.smacker.recipeCategory;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RecipeCategoryService {

    @Autowired
    RecipeCategoryRepository recipeCategoryRepository;

    List<RecipeCategoryEntity> getAll() {
        return this.recipeCategoryRepository.findAll();
    }

    public boolean existsByPublicId(UUID publicId) {
        return this.recipeCategoryRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RecipeCategoryNotFoundException(publicId)) != null;
    }

    public RecipeCategoryEntity getByPublicId(UUID publicId) {
        return this.recipeCategoryRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RecipeCategoryNotFoundException(publicId));
    }

    public List<RecipeCategoryEntity> getCategoriesByRecipePublicId(UUID recipePublicId) {
        return this.recipeCategoryRepository.findCategoriesByRecipesPublicId(recipePublicId);
    }

    RecipeCategoryEntity createRecipeCategory(RecipeCategoryAPI recipeCategoryAPI) {
        RecipeCategoryEntity category = toRecipeCategoryEntity(recipeCategoryAPI);
        return this.recipeCategoryRepository.save(category);
    }

    public RecipeCategoryEntity updateRecipeCategory(RecipeCategoryEntity recipeCategory) {
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

    RecipeCategoryEntity toRecipeCategoryEntity(RecipeCategoryAPI categoryAPI) {
        RecipeCategoryEntity.RecipeCategoryEntityBuilder categoryBuilder = RecipeCategoryEntity.builder();
        categoryBuilder.name(categoryAPI.name());
        return categoryBuilder.build();

    }

}
