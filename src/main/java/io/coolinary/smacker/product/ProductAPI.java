package io.coolinary.smacker.product;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public record ProductAPI(
                UUID publicId,
                Instant lastUpdate,
                String name,
                Optional<String> description,
                Optional<Integer> carbs,
                Optional<Integer> fiber,
                Optional<Integer> fats,
                Optional<Integer> proteins) {
}
