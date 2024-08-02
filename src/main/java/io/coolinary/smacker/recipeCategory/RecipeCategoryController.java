package io.coolinary.smacker.recipeCategory;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import io.coolinary.smacker.recipe.Recipe;
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

@RestController
@RequestMapping("/api")
public class RecipeCategoryController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeCategoryService recipeCategoryService;
    private final Logger logger = LoggerFactory.getLogger(RecipeCategoryController.class);

    @PostMapping(Routes.RECIPE_CATEGORIES)
    public ResponseEntity<RecipeCategory> createRecipeCategory(@RequestBody RecipeCategory newCategory) {

        try {
            System.out.println("create done");
            return new ResponseEntity<RecipeCategory>(this.recipeCategoryService.createRecipeCategory(newCategory),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(Routes.RECIPE_CATEGORIES + Routes.ID + Routes.RECIPES)
    public ResponseEntity<Recipe> createRecipe(@PathVariable("id") Long categoryId, @RequestBody Recipe recipeRequest) {

        try {
            RecipeCategory category = recipeCategoryService.getById(categoryId);
            Long recipeId = recipeRequest.getId();

            // if (recipeId != null && recipeId.longValue() != 0L) {
            if (recipeId.longValue() != 0L) {

                Recipe _recipe = this.recipeService.getById(recipeId);
                category.addRecipe(_recipe);
                recipeCategoryService.updateRecipeCategory(categoryId, category);
                return new ResponseEntity<Recipe>(_recipe, HttpStatus.OK);

            }

            category.addRecipe(recipeRequest);
            return new ResponseEntity<Recipe>(recipeService.createRecipe(recipeRequest), HttpStatus.OK);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(Routes.RECIPE_CATEGORIES)
    public ResponseEntity<List<RecipeCategory>> getAllRecipeCategories() {

        try {
            return new ResponseEntity<List<RecipeCategory>>(
                    this.recipeCategoryService.getAll().stream().collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(Routes.RECIPE_CATEGORIES + Routes.ID)
    public ResponseEntity<RecipeCategory> getCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<RecipeCategory>(this.recipeCategoryService.getById(id), HttpStatus.OK);
    }

    @GetMapping(Routes.RECIPE_CATEGORIES + Routes.ID + Routes.RECIPES)
    public ResponseEntity<List<Recipe>> getAllRecipesByCategoryId(@PathVariable("id") Long categoryId) {
        if (!this.recipeCategoryService.existsById(categoryId)) {
            throw new RecipeCategoryNotFoundException(categoryId);
        }
        return new ResponseEntity<List<Recipe>>(this.recipeService.getRecipesByCategoriesId(categoryId),
                HttpStatus.CREATED);
    }

    @PutMapping(Routes.RECIPE_CATEGORIES + Routes.ID)
    public ResponseEntity<RecipeCategory> replaceCategory(@PathVariable("id") Long id,
            @RequestBody RecipeCategory newCategory) {

        RecipeCategory _category = this.recipeCategoryService.getById(id);
        _category.setName(newCategory.getName());
        return new ResponseEntity<RecipeCategory>(
                this.recipeCategoryService.updateRecipeCategory(id, _category),
                HttpStatus.OK);

    }

    @DeleteMapping(Routes.RECIPE_CATEGORIES + Routes.ID)
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<Boolean>(this.recipeCategoryService.deleteRecipeCategory(id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Routes.RECIPE_CATEGORIES + Routes.ALL)
    public ResponseEntity<Boolean> deleteAllCategories() {
        return new ResponseEntity<Boolean>(this.recipeCategoryService.deleteAllRecipeCategories(),
                HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Routes.RECIPE_CATEGORIES + Routes.ID + Routes.RECIPES + Routes.RECIPE_ID)
    public ResponseEntity<HttpStatus> deleteRecipeFromCategory(@PathVariable("id") Long id,
            @PathVariable("recipeId") Long recipeId) {
        RecipeCategory category = recipeCategoryService.getById(id);
        category.removeRecipe(recipeId);
        recipeCategoryService.updateRecipeCategory(id, category);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
