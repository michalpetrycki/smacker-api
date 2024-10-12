package io.coolinary.smacker.recipe;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import io.coolinary.smacker.product.ProductEntity;
import io.coolinary.smacker.recipeCategory.RecipeCategoryEntity;
import io.coolinary.smacker.shared.RecipeProduct;
import io.coolinary.smacker.shared.UpdatableEntity;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "recipe")
@AllArgsConstructor
@NoArgsConstructor
public class RecipeEntity extends UpdatableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "recipe_name")
    private String name;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @Builder.Default
    private List<RecipeProduct> products = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "recipes")
    @JsonIgnore
    @Builder.Default
    private Set<RecipeCategoryEntity> categories = new HashSet<>();

    public void addProduct(ProductEntity product, int amount) {
        RecipeProduct recipeProduct = new RecipeProduct();
        recipeProduct.setProduct(product);
        recipeProduct.setRecipe(this);
        recipeProduct.setProductId(product.getId());
        recipeProduct.setRecipeId(this.getId());
        recipeProduct.setAmount(amount);
        products.add(recipeProduct);
        product.getRecipes().add(recipeProduct);
    }

    public void removeProduct(RecipeProduct product) {

        for (Iterator<RecipeProduct> iterator = products.iterator(); iterator.hasNext();) {

            RecipeProduct recipeProduct = iterator.next();

            if (recipeProduct.getRecipe().equals(this) && recipeProduct.getProduct().equals(product)) {

                iterator.remove();
                recipeProduct.getProduct().getRecipes().remove(recipeProduct);
                recipeProduct.setRecipe(null);
                recipeProduct.setProduct(null);

            }

        }

    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RecipeEntity))
            return false;
        return id != null && id.equals(((RecipeEntity) o).getId());
    }

    @Override
    public String toString() {
        return "Recipe [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

}
