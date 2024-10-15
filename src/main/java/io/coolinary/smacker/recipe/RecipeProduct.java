package io.coolinary.smacker.recipe;

import java.util.Objects;

import io.coolinary.smacker.product.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;

@Entity(name = "RecipeProduct")
@Table(name = "product_to_recipe")
@Getter
@Setter
@NoArgsConstructor
@IdClass(RecipeProductId.class)
public class RecipeProduct {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "amount")
    private int amount = 0;

    @ManyToOne
    @JoinColumn(name = "recipe_id", updatable = false, insertable = false, referencedColumnName = "id")
    private RecipeEntity recipe;

    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false, insertable = false, referencedColumnName = "id")
    private ProductEntity product;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RecipeProduct that = (RecipeProduct) o;
        return Objects.equals(recipe, that.recipe) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, product);
    }

}
