package io.coolinary.smacker.productCategory;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findCategoriesByProductsId(Long productId);

}
