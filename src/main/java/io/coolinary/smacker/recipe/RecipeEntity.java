package io.coolinary.smacker.recipe;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
import io.coolinary.smacker.shared.UpdatableEntity;
import io.coolinary.smacker.tool.ToolEntity;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "recipe")
@AllArgsConstructor
@NoArgsConstructor
public class RecipeEntity extends UpdatableEntity {

    @Column(name = "recipe_name")
    private String name;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<RecipeProduct> products = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<RecipeTool> tools = new HashSet<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "tool_to_recipe", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private Set<ToolEntity> recipeTools = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "recipes")
    @JsonIgnore
    @Builder.Default
    private Set<RecipeCategoryEntity> categories = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "recipeEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeStepEntity> steps = new HashSet<RecipeStepEntity>();

    public void addProduct(ProductEntity product, int amount) {
        RecipeProduct recipeProduct = new RecipeProduct();
        recipeProduct.setProduct(product);
        recipeProduct.setRecipe(this);
        recipeProduct.setAmount(amount);
        products.add(recipeProduct);
        product.getRecipes().add(recipeProduct);
    }

    public void addTool(ToolEntity tool) {
        RecipeTool recipeTool = new RecipeTool(this, tool);
        tools.add(recipeTool);
        tool.getRecipeTools().add(recipeTool);
    }

    public void addStep(RecipeStepEntity step) {
        steps.add(step);
        step.setRecipeEntity(this);
    }

    public void removeStep(RecipeStepEntity step) {
        steps.remove(step);
        step.setRecipeEntity(null);
    }

    public void removeProduct(RecipeProduct recipeProduct) {

        products.removeIf(product -> recipeProduct.getRecipe().equals(this) &&
                recipeProduct.isProductEqual(recipeProduct.getProduct()));

        products.forEach(product -> {
            if (recipeProduct.isProductEqual(product.getProduct())) {
                recipeProduct.getProduct().getRecipes().remove(recipeProduct);
                recipeProduct.setRecipe(null);
                recipeProduct.setProduct(null);
            }
        });

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeEntity recipe = (RecipeEntity) o;
        return Objects.equals(id, recipe.id);
    }

    @Override
    public String toString() {
        return "Recipe [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

}
