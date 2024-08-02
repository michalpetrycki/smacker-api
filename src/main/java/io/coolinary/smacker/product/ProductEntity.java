package io.coolinary.smacker.product;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.coolinary.smacker.productCategory.ProductCategory;
import io.coolinary.smacker.shared.UpdatableEntity;

import java.util.HashSet;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity extends UpdatableEntity {

    @Builder.Default
    @Column(name = "public_identifier")
    private UUID publicId = UUID.randomUUID();

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "products")
    @JsonIgnore
    private Set<ProductCategory> categories = new HashSet<>();

    public Set<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<ProductCategory> categories) {
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + carbs;
        result = prime * result + fats;
        result = prime * result + fiber;
        result = prime * result + proteins;
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
        ProductEntity other = (ProductEntity) obj;
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
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (carbs != other.carbs)
            return false;
        if (fats != other.fats)
            return false;
        if (fiber != other.fiber)
            return false;
        if (proteins != other.proteins)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", carbs=" + carbs + ", fats="
                + fats + ", fiber=" + fiber + ", proteins=" + proteins + "]";
    }

}
