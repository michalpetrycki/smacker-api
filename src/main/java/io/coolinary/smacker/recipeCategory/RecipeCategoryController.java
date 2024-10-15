package io.coolinary.smacker.recipeCategory;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import io.coolinary.smacker.product.ProductEntity;
import io.coolinary.smacker.product.ProductRepository;
import io.coolinary.smacker.recipe.CreateRecipeAPI;
import io.coolinary.smacker.recipe.RecipeAPI;
import io.coolinary.smacker.recipe.RecipeEntity;
import io.coolinary.smacker.recipe.RecipeService;
import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import io.coolinary.smacker.tool.ToolAPI;
import io.coolinary.smacker.tool.ToolEntity;
import io.coolinary.smacker.tool.ToolRepository;
import io.coolinary.smacker.shared.Routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.coolinary.smacker.recipeCategory.RecipeCategoryService.toRecipeCategoryAPI;
import static io.coolinary.smacker.recipe.RecipeService.toRecipeAPI;

@RestController
@RequestMapping("/api")
public class RecipeCategoryController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeCategoryService recipeCategoryService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ToolRepository toolRepository;
    private final Logger logger = LoggerFactory.getLogger(RecipeCategoryController.class);

    @PostMapping(Routes.RECIPE_CATEGORIES)
    public ResponseEntity<RecipeCategoryEntity> createRecipeCategory(@RequestBody CreateRecipeCategoryAPI newCategory) {

        try {
            return new ResponseEntity<RecipeCategoryEntity>(
                    this.recipeCategoryService.createRecipeCategory(newCategory),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(Routes.RECIPE_CATEGORIES + Routes.PID + Routes.RECIPES)
    public ResponseEntity<RecipeAPI> createRecipe(@PathVariable("publicId") UUID recipeCategoryPublicId,
            @RequestBody CreateRecipeAPI createRecipeAPI) {

        try {
            RecipeCategoryEntity category = recipeCategoryService.getByPublicId(recipeCategoryPublicId);
            RecipeEntity recipeEntity = recipeService.createRecipe(createRecipeAPI);

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

            category.addRecipe(recipeEntity);
            recipeCategoryService.updateRecipeCategory(category);

            return new ResponseEntity<RecipeAPI>(toRecipeAPI(recipeEntity),
                    HttpStatus.OK);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(Routes.RECIPE_CATEGORIES)
    public ResponseEntity<List<RecipeCategoryAPI>> getAllRecipeCategories() {

        try {
            return new ResponseEntity<List<RecipeCategoryAPI>>(
                    this.recipeCategoryService.getAll().stream().map(category -> toRecipeCategoryAPI(category))
                            .collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(Routes.RECIPE_CATEGORIES + Routes.PID)
    public ResponseEntity<RecipeCategoryEntity> getCategory(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<RecipeCategoryEntity>(this.recipeCategoryService.getByPublicId(publicId),
                HttpStatus.OK);
    }

    @GetMapping(Routes.RECIPES + Routes.PID + Routes.RECIPE_CATEGORIES)
    public ResponseEntity<List<RecipeCategoryEntity>> getAllCategoriesByRecipeId(
            @PathVariable("publicId") UUID recipePublicId) {
        if (!this.recipeService.existsByPublicId(recipePublicId)) {
            throw new ElementNotFoundException(recipePublicId, EntityType.RECIPE);
        }
        return new ResponseEntity<List<RecipeCategoryEntity>>(
                this.recipeCategoryService.getCategoriesByRecipePublicId(recipePublicId),
                HttpStatus.CREATED);
    }

    @PutMapping(Routes.RECIPE_CATEGORIES + Routes.PID)
    public ResponseEntity<RecipeCategoryEntity> replaceCategory(@PathVariable("publicId") UUID publicId,
            @RequestBody RecipeCategoryEntity newCategory) {

        RecipeCategoryEntity _category = this.recipeCategoryService.getByPublicId(publicId);
        _category.setName(newCategory.getName());
        return new ResponseEntity<RecipeCategoryEntity>(
                this.recipeCategoryService.updateRecipeCategory(_category),
                HttpStatus.OK);

    }

    @DeleteMapping(Routes.RECIPE_CATEGORIES + Routes.PID)
    public ResponseEntity<Long> deleteCategory(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<Long>(this.recipeCategoryService.deleteRecipeCategoryByPublicId(publicId),
                HttpStatus.OK);
    }

    @DeleteMapping(Routes.RECIPE_CATEGORIES + Routes.ALL)
    public ResponseEntity<Boolean> deleteAllCategories() {
        return new ResponseEntity<Boolean>(this.recipeCategoryService.deleteAllRecipeCategories(),
                HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Routes.RECIPE_CATEGORIES + Routes.RECIPE_CATEGORY_PUBLIC_ID + Routes.RECIPES +
            Routes.RECIPE_PUBLIC_ID)
    public ResponseEntity<HttpStatus> deleteRecipeFromCategory(
            @PathVariable("recipeCategoryPublicId") UUID recipeCategoryPublicId,
            @PathVariable("recipePublicId") UUID recipePublicId) {
        RecipeCategoryEntity category = recipeCategoryService.getByPublicId(recipeCategoryPublicId);
        category.removeRecipe(recipePublicId);
        recipeCategoryService.updateRecipeCategory(category);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
