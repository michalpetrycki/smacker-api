package io.coolinary.smacker.recipe;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import io.coolinary.smacker.product.ProductAPI;
import io.coolinary.smacker.tool.ToolAPI;

public record RecipeAPI(
                String name,
                String description,
                List<String> steps,
                UUID publicId,
                Instant creationTimestamp,
                Instant updateTimestamp,
                List<ProductAPI> products,
                List<ToolAPI> tools,
                List<Integer> amounts) {

}
