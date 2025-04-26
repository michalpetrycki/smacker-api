package io.coolinary.smacker.productCategory;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import io.coolinary.smacker.product.ProductEntity;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory extends UpdatableEntity {

    @Column(name = "product_category_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "product_to_category", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = {
            @JoinColumn(name = "product_id") })
    @Builder.Default
    private Set<ProductEntity> products = new HashSet<ProductEntity>();

    public void addProduct(ProductEntity product) {
        this.products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(Long productId) {
        this.products.removeIf(product -> {
            if (product.getId().equals(productId)) {
                product.getCategories().remove(this);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductCategory that = (ProductCategory) o;
        return Objects.equals(publicId, that.publicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicId);
    }

    @Override
    public String toString() {
        return "ProductCategory [id=" + id + ", name=" + name + "]";
    }

}
