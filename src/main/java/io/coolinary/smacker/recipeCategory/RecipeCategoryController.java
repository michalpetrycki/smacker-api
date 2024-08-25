package io.coolinary.smacker.recipeCategory;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import io.coolinary.smacker.recipe.RecipeEntity;
import io.coolinary.smacker.recipe.RecipeService;
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

@RestController
@RequestMapping("/api")
public class RecipeCategoryController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeCategoryService recipeCategoryService;
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
    public ResponseEntity<RecipeEntity> createRecipe(@PathVariable("publicId") UUID recipeCategoryPublicId,
            @RequestBody RecipeEntity recipeRequest) {

        try {
            RecipeCategoryEntity category = recipeCategoryService.getByPublicId(recipeCategoryPublicId);
            RecipeEntity recipe = recipeService.createRecipe(recipeRequest);
            category.addRecipe(recipe);
            recipeCategoryService.updateRecipeCategory(category);

            return new ResponseEntity<RecipeEntity>(recipe,
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

    @GetMapping(Routes.RECIPE_CATEGORIES + Routes.ID + Routes.RECIPES)
    public ResponseEntity<List<RecipeEntity>> getAllRecipesByCategoryId(@PathVariable("id") UUID publicId) {
        if (!this.recipeCategoryService.existsByPublicId(publicId)) {
            throw new RecipeCategoryNotFoundException(publicId);
        }
        return new ResponseEntity<List<RecipeEntity>>(this.recipeService.getRecipesByCategoriesId(publicId),
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
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<Boolean>(this.recipeCategoryService.deleteRecipeCategoryByPublicId(publicId),
                HttpStatus.NO_CONTENT);
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
