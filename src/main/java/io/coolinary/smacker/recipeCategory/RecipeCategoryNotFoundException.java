package io.coolinary.smacker.recipeCategory;

public class RecipeCategoryNotFoundException extends RuntimeException {
    public RecipeCategoryNotFoundException(Long id) {
        super("Could not find recipe category: " + id);
    }
}
