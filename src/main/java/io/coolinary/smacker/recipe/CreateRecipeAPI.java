package io.coolinary.smacker.recipe;

import java.util.ArrayList;
import java.util.Optional;
import io.coolinary.smacker.product.ProductAPI;
import io.coolinary.smacker.tool.ToolAPI;

public record CreateRecipeAPI(
        String name,
        Optional<String> description,
        ArrayList<ProductAPI> products,
        ArrayList<ToolAPI> tools,
        ArrayList<String> steps) {

}
