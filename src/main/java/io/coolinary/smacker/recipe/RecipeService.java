package io.coolinary.smacker.recipe;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.coolinary.smacker.product.ProductAPI;
import io.coolinary.smacker.product.ProductService;
import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import io.coolinary.smacker.tool.ToolAPI;
import io.coolinary.smacker.tool.ToolService;

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

    public RecipeEntity createRecipe(CreateRecipeAPI createRecipeAPI) {
        RecipeEntity.RecipeEntityBuilder<?, ?> recipeEntity = RecipeEntity.builder()
                .name(createRecipeAPI.name());
        createRecipeAPI.description().ifPresent(recipeEntity::description);
        return this.recipeRepository.save(recipeEntity.build());
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

    static RecipeEntity toRecipeEntity(RecipeAPI recipeAPI) {
        RecipeEntity.RecipeEntityBuilder<?, ?> recipeBuilder = RecipeEntity.builder();
        recipeBuilder.name(recipeAPI.name());
        recipeBuilder.description(recipeAPI.description());
        return recipeBuilder.build();
    }

    public static RecipeAPI toRecipeAPI(RecipeEntity recipeEntity) {

        List<ProductAPI> products = recipeEntity.getProducts().stream().map(recipeProduct -> recipeProduct.getProduct())
                .toList().stream().map(ProductService::toProductAPI).collect(Collectors.toList());

        List<ToolAPI> tools = recipeEntity.getTools().stream().map(recipeTool -> recipeTool.getTool()).toList().stream()
                .map(ToolService::toToolAPI)
                .collect(Collectors.toList());

        RecipeAPI recipeAPI = new RecipeAPI(
                recipeEntity.getName(),
                recipeEntity.getDescription(),
                List.of("Step1", "Step2", "Step3"),
                recipeEntity.getPublicId(),
                recipeEntity.getCreationTimestamp(),
                recipeEntity.getUpdateTimestamp(),
                products,
                tools,
                List.of(1, 2, 3));
        return recipeAPI;
    }

}
