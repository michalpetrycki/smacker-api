package io.coolinary.smacker.recipe;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import io.coolinary.smacker.recipeCategory.RecipeCategory;
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
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe newRecipe) {

        try {
            return new ResponseEntity<Recipe>(this.recipeService.createRecipe(newRecipe),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(Routes.RECIPES)
    ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            return new ResponseEntity<List<Recipe>>(this.recipeService.getAll().stream().collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(Routes.RECIPES + Routes.ID)
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") Long id) {
        return new ResponseEntity<Recipe>(this.recipeService.getById(id), HttpStatus.OK);
    }

    @GetMapping(Routes.RECIPES + Routes.ID + Routes.RECIPE_CATEGORIES)
    public ResponseEntity<List<RecipeCategory>> getAllCategoriesByRecipeId(@PathVariable("id") Long recipeId) {
        if (!this.recipeService.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
        return new ResponseEntity<List<RecipeCategory>>(this.recipeCategoryService.getCategoriesByRecipeId(recipeId),
                HttpStatus.CREATED);
    }

    @PutMapping(Routes.RECIPES + Routes.ID)
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") Long id, @RequestBody Recipe recipeRequest) {

        Recipe _recipe = this.recipeService.getById(id);
        if (_recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        _recipe.setName(recipeRequest.getName());
        return new ResponseEntity<Recipe>(this.recipeService.updateRecipe(id, _recipe), HttpStatus.OK);
    }

    @DeleteMapping(Routes.RECIPES + Routes.ID)
    public ResponseEntity<Boolean> deleteRecipe(@PathVariable("id") Long id) {
        return new ResponseEntity<Boolean>(this.recipeService.deleteRecipe(id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Routes.RECIPES + Routes.ALL)
    public ResponseEntity<Boolean> deleteAllRecipes() {
        return new ResponseEntity<Boolean>(this.recipeService.deleteAllRecipes(),
                HttpStatus.NO_CONTENT);
    }

}
