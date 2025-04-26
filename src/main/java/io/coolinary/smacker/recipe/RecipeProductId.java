package io.coolinary.smacker.recipe;

import java.util.Objects;

import io.coolinary.smacker.shared.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
