package io.coolinary.smacker.shared;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeProductId implements Serializable {

    private Long recipeId;

    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RecipeProductId that = (RecipeProductId) o;
        return Objects.equals(recipeId, that.recipeId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, productId);
    }

}
