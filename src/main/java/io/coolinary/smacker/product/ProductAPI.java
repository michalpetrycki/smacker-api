package io.coolinary.smacker.product;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.coolinary.smacker.productCategory.ProductCategoryAPI;

public record ProductAPI(
        UUID publicId,
        // Instant lastUpdate,
        String name,
        Optional<String> description,
        Optional<Integer> carbs,
        Optional<Integer> fiber,
        Optional<Integer> fats,
        Optional<Integer> proteins,
        List<ProductCategoryAPI> categories) {
}
