package io.coolinary.smacker.recipeCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import io.coolinary.smacker.recipe.CreateRecipeAPI;
import io.coolinary.smacker.recipe.RecipeAPI;
import io.coolinary.smacker.recipe.RecipeEntity;
import io.coolinary.smacker.recipe.RecipeService;
import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import io.coolinary.smacker.shared.PaginatedResponse;
import io.coolinary.smacker.shared.Routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.coolinary.smacker.recipeCategory.RecipeCategoryServiceImpl.toRecipeCategoryAPI;
import static io.coolinary.smacker.recipe.RecipeService.toRecipeAPI;

@RestController
@RequestMapping("/api")
public class RecipeCategoryController {

    private final RecipeCategoryServiceImpl recipeCategoryService;

    RecipeCategoryController(RecipeCategoryServiceImpl recipeCategoryService) {
        this.recipeCategoryService = recipeCategoryService;
    }

    @GetMapping(Routes.RECIPE_CATEGORIES_ALL)
    ResponseEntity<List<RecipeCategoryAPI>> getAll() {

        return new ResponseEntity<List<RecipeCategoryAPI>>(
                this.recipeCategoryService.getAll().stream().map(category -> toRecipeCategoryAPI(category))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(Routes.RECIPE_CATEGORIES)
    ResponseEntity<PaginatedResponse<RecipeCategoryAPI>> getPaginated(@RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String filter) {

        Page<RecipeCategoryEntity> page = this.recipeCategoryService.getPaginated(pageNo, pageSize, sortBy,
                sortDirection, filter);
        List<RecipeCategoryAPI> categories = page.getContent().stream()
                .map(category -> toRecipeCategoryAPI(category))
                .collect(Collectors.toList());

        PaginatedResponse<RecipeCategoryAPI> resp = new PaginatedResponse<RecipeCategoryAPI>(categories,
                page.getTotalElements());
        return new ResponseEntity<PaginatedResponse<RecipeCategoryAPI>>(resp, HttpStatus.OK);
    }

    @GetMapping(Routes.RECIPE_CATEGORIES + Routes.PID)
    public ResponseEntity<RecipeCategoryAPI> getRecipeCategory(@PathVariable("publicId") UUID publicId) {
        RecipeCategoryEntity recipeCategoryEntity = this.recipeCategoryService.getByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.RECIPE_CATEGORY));
        return new ResponseEntity<RecipeCategoryAPI>(toRecipeCategoryAPI(recipeCategoryEntity), HttpStatus.OK);
    }

    @PostMapping(Routes.RECIPE_CATEGORIES)
    public ResponseEntity<RecipeCategoryAPI> createCategory(@RequestBody RecipeCategoryCreateAPI newCategory) {
        return new ResponseEntity<RecipeCategoryAPI>(
                toRecipeCategoryAPI(this.recipeCategoryService.createCategory(newCategory)),
                HttpStatus.CREATED);
    }

    @PutMapping(Routes.RECIPE_CATEGORIES + Routes.PID)
    public ResponseEntity<RecipeCategoryAPI> replaceCategory(@RequestBody RecipeCategoryAPI recipeCategoryAPI,
            @PathVariable("publicId") UUID publicId) {

        Optional<RecipeCategoryEntity> categoryToUpdate = this.recipeCategoryService.getByPublicId(publicId);
        if (!categoryToUpdate.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<RecipeCategoryAPI>(toRecipeCategoryAPI(this.recipeCategoryService.updateCategory(
                categoryToUpdate.get(), recipeCategoryAPI)), HttpStatus.OK);
    }

    @DeleteMapping(Routes.RECIPE_CATEGORIES + Routes.PID)
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<Boolean>(this.recipeCategoryService.deleteCategory(publicId), HttpStatus.OK);
    }

    @DeleteMapping(Routes.RECIPE_CATEGORIES)
    public ResponseEntity<Boolean> deleteAllCategories() {
        return new ResponseEntity<Boolean>(this.recipeCategoryService.deleteAllCategories(), HttpStatus.OK);
    }

    // @PostMapping(Routes.RECIPE_CATEGORIES + Routes.PID + Routes.RECIPES)
    // public ResponseEntity<RecipeAPI> createRecipe(@PathVariable("publicId") UUID
    // recipeCategoryPublicId,
    // @RequestBody CreateRecipeAPI createRecipeAPI) {

    // try {
    // RecipeCategoryEntity category =
    // recipeCategoryService.getByPublicId(recipeCategoryPublicId);
    // RecipeEntity recipeEntity = recipeService.createRecipe(createRecipeAPI);
    // category.addRecipe(recipeEntity);
    // recipeCategoryService.updateRecipeCategory(category);
    // return new ResponseEntity<RecipeAPI>(toRecipeAPI(recipeEntity),
    // HttpStatus.OK);
    // } catch (Exception ex) {
    // this.logger.error(ex.getMessage(), ex);
    // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }

    // }

    // @GetMapping(Routes.RECIPES + Routes.PID + Routes.RECIPE_CATEGORIES)
    // public ResponseEntity<List<RecipeCategoryEntity>> getAllCategoriesByRecipeId(
    // @PathVariable("publicId") UUID recipePublicId) {
    // if (!this.recipeService.existsByPublicId(recipePublicId)) {
    // throw new ElementNotFoundException(recipePublicId, EntityType.RECIPE);
    // }
    // return new ResponseEntity<List<RecipeCategoryEntity>>(
    // this.recipeCategoryService.getCategoriesByRecipePublicId(recipePublicId),
    // HttpStatus.CREATED);
    // }

    // @DeleteMapping(Routes.RECIPE_CATEGORIES + Routes.RECIPE_CATEGORY_PUBLIC_ID +
    // Routes.RECIPES +
    // Routes.RECIPE_PUBLIC_ID)
    // public ResponseEntity<HttpStatus> deleteRecipeFromCategory(
    // @PathVariable("recipeCategoryPublicId") UUID recipeCategoryPublicId,
    // @PathVariable("recipePublicId") UUID recipePublicId) {
    // RecipeCategoryEntity category =
    // recipeCategoryService.getByPublicId(recipeCategoryPublicId);
    // category.removeRecipe(recipePublicId);
    // recipeCategoryService.updateRecipeCategory(category);
    // return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    // }

}
