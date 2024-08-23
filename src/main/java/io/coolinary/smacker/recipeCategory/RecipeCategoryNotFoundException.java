package io.coolinary.smacker.recipeCategory;

import java.util.UUID;

public class RecipeCategoryNotFoundException extends RuntimeException {
    public RecipeCategoryNotFoundException(UUID id) {
        super("Could not find recipe category: " + id);
    }
}
