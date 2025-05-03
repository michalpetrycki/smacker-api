package io.coolinary.smacker.shared;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementNotFoundException extends RuntimeException {

    private EntityType entityType;

    public ElementNotFoundException(UUID publicId, EntityType entityType) {
        super("Could not find " + entityType.label + " with public id: " + publicId);
        this.entityType = entityType;
    }

    public enum EntityType {
        IMAGE("image"),
        PRODUCT("product"),
        PRODUCT_CATEGORY("product category"),
        RECIPE("recipe"),
        RECIPE_CATEGORY("recipe category"),
        TOOL("tool");

        public final String label;

        private EntityType(String label) {
            this.label = label;
        }

    }
}
