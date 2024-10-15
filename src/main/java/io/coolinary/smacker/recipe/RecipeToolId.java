package io.coolinary.smacker.recipe;

import java.util.Objects;

import io.coolinary.smacker.shared.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeToolId implements Serializable {

    private Long recipeId;

    private Long toolId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RecipeToolId that = (RecipeToolId) o;
        return Objects.equals(recipeId, that.recipeId) &&
                Objects.equals(toolId, that.toolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, toolId);
    }

}
