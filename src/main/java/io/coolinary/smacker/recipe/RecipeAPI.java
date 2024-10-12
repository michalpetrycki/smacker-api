package io.coolinary.smacker.recipe;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import io.coolinary.smacker.product.ProductAPI;

public record RecipeAPI(
        String name,
        String description,
        List<String> steps,
        UUID publicId,
        Instant creationTimestamp,
        Instant updateTimestamp,
        List<ProductAPI> products,
        List<Integer> amounts) {

}
