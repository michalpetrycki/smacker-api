package io.coolinary.smacker.recipeCategory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.coolinary.smacker.recipe.RecipeEntity;
import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import io.coolinary.smacker.shared.UpdatableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@Table(name = "recipe_category")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCategoryEntity extends UpdatableEntity {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name = "recipe_category_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "recipe_to_category", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = {
            @JoinColumn(name = "recipe_id") })
    @Builder.Default
    private Set<RecipeEntity> recipes = new HashSet<RecipeEntity>();

    public void addRecipe(RecipeEntity recipe) {
        this.recipes.add(recipe);
        recipe.getCategories().add(this);
    }

    public void removeRecipe(UUID recipePublicId) {
        RecipeEntity recipe = this.recipes.stream().filter(r -> r.getPublicId() == recipePublicId).findFirst()
                .orElseThrow(() -> new ElementNotFoundException(recipePublicId, EntityType.RECIPE));
        if (recipe != null) {
            this.recipes.remove(recipe);
            recipe.getCategories().remove(this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RecipeCategoryEntity other = (RecipeCategoryEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RecipeCategory [id=" + id + ", name=" + name + "]";
    }

}
