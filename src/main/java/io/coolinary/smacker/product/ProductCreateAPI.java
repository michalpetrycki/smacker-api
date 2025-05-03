package io.coolinary.smacker.product;

import java.util.List;
import java.util.Optional;

import io.coolinary.smacker.productCategory.ProductCategoryAPI;

public record ProductCreateAPI(
                String name,
                Optional<String> description,
                Optional<Integer> carbs,
                Optional<Integer> fiber,
                Optional<Integer> fats,
                Optional<Integer> proteins,
                List<ProductCategoryAPI> categories) {

}
