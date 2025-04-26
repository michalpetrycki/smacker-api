package io.coolinary.smacker.product;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.coolinary.smacker.productCategory.ProductCategory;
import io.coolinary.smacker.recipe.RecipeProduct;
import io.coolinary.smacker.shared.UpdatableEntity;

import java.util.HashSet;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "product")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends UpdatableEntity {

    @Column(name = "product_name", unique = true, nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column
    private int carbs;
    @Column
    private int fats;
    @Column
    private int fiber;
    @Column
    private int proteins;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Builder.Default
    private Set<RecipeProduct> recipes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    @JsonIgnore
    @Builder.Default
    private Set<ProductCategory> categories = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(this.publicId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(this.publicId, that.publicId);
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", carbs=" + carbs + ", fats="
                + fats + ", fiber=" + fiber + ", proteins=" + proteins + "]";
    }

}
