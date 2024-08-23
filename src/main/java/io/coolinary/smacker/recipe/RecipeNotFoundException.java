package io.coolinary.smacker.recipe;

import java.util.UUID;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(UUID publicId) {
        super("Could not find recipe: " + publicId);
    }
}
