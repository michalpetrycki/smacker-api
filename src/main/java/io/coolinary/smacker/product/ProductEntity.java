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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "products")
    @JsonIgnore
    @Builder.Default
    private Set<ProductCategory> categories = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductEntity product = (ProductEntity) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", carbs=" + carbs + ", fats="
                + fats + ", fiber=" + fiber + ", proteins=" + proteins + "]";
    }

}
