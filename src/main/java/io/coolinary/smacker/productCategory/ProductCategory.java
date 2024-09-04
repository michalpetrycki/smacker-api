package io.coolinary.smacker.productCategory;

import java.util.HashSet;
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

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name = "product_category_name")
    private String name;

    @Column(name = "public_identifier")
    private UUID publicId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "product_to_category", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = {
            @JoinColumn(name = "product_id") })
    private Set<ProductEntity> products = new HashSet<ProductEntity>();

    public void addProduct(ProductEntity product) {
        this.products.add(product);
        // product.getCategories().add(this);
    }

    public void removeProduct(Long productId) {
        // Product product = this.products.stream().filter(r -> r.getId() ==
        // productId).findFirst().orElse(null);
        // if (product != null) {
        // this.products.remove(product);
        // product.getCategories().remove(this);
        // }
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
        ProductCategory other = (ProductCategory) obj;
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
        return "ProductCategory [id=" + id + ", name=" + name + "]";
    }

}
