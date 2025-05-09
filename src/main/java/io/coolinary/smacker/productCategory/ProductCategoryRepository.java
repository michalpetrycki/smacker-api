package io.coolinary.smacker.productCategory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    Optional<ProductCategoryEntity> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID publicId);

    Page<ProductCategoryEntity> findByCategoryNameContainingIgnoreCase(String name, Pageable pageable);

    List<ProductCategoryEntity> findCategoriesByProductsId(Long productId);

    List<ProductCategoryEntity> findAllByPublicIdIn(Collection<UUID> publicIds);

}
