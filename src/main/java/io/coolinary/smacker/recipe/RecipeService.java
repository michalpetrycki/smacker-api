package io.coolinary.smacker.recipe;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.coolinary.smacker.product.ProductAPI;
import io.coolinary.smacker.product.ProductEntity;
import io.coolinary.smacker.product.ProductRepository;
import io.coolinary.smacker.product.ProductService;
import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import io.coolinary.smacker.tool.ToolAPI;
import io.coolinary.smacker.tool.ToolEntity;
import io.coolinary.smacker.tool.ToolRepository;
import io.coolinary.smacker.tool.ToolServiceImpl;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ToolRepository toolRepository;

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
        RecipeEntity recipeEntity = RecipeEntity.builder()
                .name(createRecipeAPI.name()).build();
        createRecipeAPI.description().ifPresent(recipeEntity::setDescription);

        for (int i = 0; i < createRecipeAPI.products().size(); i++) {
            ProductEntity productEntity = productRepository
                    .findByPublicId(createRecipeAPI.products().get(i).publicId()).orElseThrow();
            int amount = createRecipeAPI.amounts().get(i);
            recipeEntity.addProduct(productEntity, amount);
        }

        for (ToolAPI tool : createRecipeAPI.tools()) {
            ToolEntity toolEntity = toolRepository.findByPublicId(tool.publicId()).orElseThrow();
            recipeEntity.addTool(toolEntity);
        }

        for (RecipeStepAPI step : createRecipeAPI.steps()) {
            RecipeStepEntity stepEntity = new RecipeStepEntity();
            stepEntity.setStepOrder(step.order());
            stepEntity.setStepText(step.text());
            recipeEntity.addStep(stepEntity);
        }

        return this.recipeRepository.save(recipeEntity);
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

    static RecipeStepAPI toRecipeStepAPI(RecipeStepEntity step) {
        return new RecipeStepAPI(step.getStepOrder(), step.getStepText());
    }

    public static RecipeAPI toRecipeAPI(RecipeEntity recipeEntity) {

        List<ProductAPI> products = recipeEntity.getProducts().stream().map(recipeProduct -> recipeProduct.getProduct())
                .toList().stream().map(ProductService::toProductAPI).collect(Collectors.toList());

        List<ToolAPI> tools = recipeEntity.getTools().stream().map(recipeTool -> recipeTool.getTool()).toList().stream()
                .map(ToolServiceImpl::toToolAPI)
                .collect(Collectors.toList());

        List<RecipeStepAPI> steps = recipeEntity.getSteps().stream().map(RecipeService::toRecipeStepAPI)
                .collect(Collectors.toList());

        RecipeAPI recipeAPI = new RecipeAPI(
                recipeEntity.getName(),
                recipeEntity.getDescription(),
                steps,
                recipeEntity.getPublicId(),
                recipeEntity.getCreationTimestamp(),
                recipeEntity.getUpdateTimestamp(),
                products,
                tools,
                List.of(1, 2, 3));
        return recipeAPI;
    }

}
