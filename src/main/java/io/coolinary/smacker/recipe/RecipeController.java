package io.coolinary.smacker.recipe;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import io.coolinary.smacker.recipeCategory.RecipeCategoryEntity;
import io.coolinary.smacker.recipeCategory.RecipeCategoryService;
import io.coolinary.smacker.shared.Routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeCategoryService recipeCategoryService;

    private Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @PostMapping(Routes.RECIPES)
    public ResponseEntity<RecipeEntity> createRecipe(@RequestBody RecipeEntity newRecipe) {

        try {
            return new ResponseEntity<RecipeEntity>(this.recipeService.createRecipe(newRecipe),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(Routes.RECIPES)
    ResponseEntity<List<RecipeEntity>> getAllRecipes() {
        try {
            return new ResponseEntity<List<RecipeEntity>>(
                    this.recipeService.getAll().stream().collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(Routes.RECIPES + Routes.PID)
    public ResponseEntity<RecipeEntity> getRecipe(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<RecipeEntity>(this.recipeService.getByPublicId(publicId), HttpStatus.OK);
    }

    @GetMapping(Routes.RECIPES + Routes.PID + Routes.RECIPE_CATEGORIES)
    public ResponseEntity<List<RecipeCategoryEntity>> getAllCategoriesByRecipeId(
            @PathVariable("publicId") UUID recipePublicId) {
        if (!this.recipeService.existsByPublicId(recipePublicId)) {
            throw new RecipeNotFoundException(recipePublicId);
        }
        return new ResponseEntity<List<RecipeCategoryEntity>>(
                this.recipeCategoryService.getCategoriesByRecipePublicId(recipePublicId),
                HttpStatus.CREATED);
    }

    @PutMapping(Routes.RECIPES + Routes.PID)
    public ResponseEntity<RecipeEntity> updateRecipe(@PathVariable("publicId") UUID publicId,
            @RequestBody RecipeEntity recipeRequest) {

        RecipeEntity _recipe = this.recipeService.getByPublicId(publicId);
        if (_recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        _recipe.setName(recipeRequest.getName());
        return new ResponseEntity<RecipeEntity>(this.recipeService.updateRecipe(_recipe), HttpStatus.OK);
    }

    @DeleteMapping(Routes.RECIPES + Routes.PID)
    public ResponseEntity<Boolean> deleteRecipe(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<Boolean>(this.recipeService.deleteRecipe(publicId), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Routes.RECIPES + Routes.ALL)
    public ResponseEntity<Boolean> deleteAllRecipes() {
        return new ResponseEntity<Boolean>(this.recipeService.deleteAllRecipes(),
                HttpStatus.NO_CONTENT);
    }

}
