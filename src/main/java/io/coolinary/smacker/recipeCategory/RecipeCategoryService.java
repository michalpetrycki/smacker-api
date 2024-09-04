package io.coolinary.smacker.recipeCategory;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class RecipeCategoryService {

    @Autowired
    RecipeCategoryRepository recipeCategoryRepository;

    List<RecipeCategoryEntity> getAll() {
        return this.recipeCategoryRepository.findAll();
    }

    public boolean existsByPublicId(UUID publicId) {
        return this.recipeCategoryRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.RECIPE_CATEGORY)) != null;
    }

    public RecipeCategoryEntity getByPublicId(UUID publicId) {
        return this.recipeCategoryRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.RECIPE_CATEGORY));
    }

    public List<RecipeCategoryEntity> getCategoriesByRecipePublicId(UUID recipePublicId) {
        return this.recipeCategoryRepository.findCategoriesByRecipesPublicId(recipePublicId);
    }

    RecipeCategoryEntity createRecipeCategory(CreateRecipeCategoryAPI createCategoryAPI) {
        RecipeCategoryEntity categoryEntity = RecipeCategoryEntity.builder().name(createCategoryAPI.name()).build();
        return this.recipeCategoryRepository.save(categoryEntity);
    }

    public RecipeCategoryEntity updateRecipeCategory(RecipeCategoryEntity recipeCategory) {
        return this.recipeCategoryRepository.save(recipeCategory);
    }

    Long deleteRecipeCategoryByPublicId(UUID publicId) {
        return this.recipeCategoryRepository.deleteByPublicId(publicId);
    }

    Boolean deleteAllRecipeCategories() {
        this.recipeCategoryRepository.deleteAll();
        return true;
    }

    static RecipeCategoryEntity toRecipeCategoryEntity(RecipeCategoryAPI categoryAPI) {
        RecipeCategoryEntity.RecipeCategoryEntityBuilder<?, ?> categoryBuilder = RecipeCategoryEntity.builder();
        categoryBuilder.name(categoryAPI.name());
        return categoryBuilder.build();
    }

    public static RecipeCategoryAPI toRecipeCategoryAPI(RecipeCategoryEntity categoryEntity) {
        RecipeCategoryAPI categoryAPI = new RecipeCategoryAPI(
                categoryEntity.getName(),
                categoryEntity.getPublicId(),
                categoryEntity.getCreationTimestamp(),
                categoryEntity.getUpdateTimestamp());
        return categoryAPI;
    }

}
