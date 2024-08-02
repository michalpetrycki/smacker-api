package io.coolinary.smacker.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findProductsByCategoriesId(Long categoryId);

}
