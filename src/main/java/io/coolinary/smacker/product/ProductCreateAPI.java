package io.coolinary.smacker.product;

import java.util.Optional;

public record ProductCreateAPI(
        String name,
        Optional<String> description,
        Optional<Integer> carbs,
        Optional<Integer> fiber,
        Optional<Integer> fats,
        Optional<Integer> proteins) {

}
