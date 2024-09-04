package io.coolinary.smacker.recipeCategory;

import java.time.Instant;
import java.util.UUID;

public record RecipeCategoryAPI(
        String name,
        UUID publicId,
        Instant creationTimestamp,
        Instant updateTimestamp) {

}
