package io.coolinary.smacker.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByPublicId(UUID publicId);

    List<ProductEntity> findProductsByCategoriesId(Long categoryId);

    Page<ProductEntity> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name,
            String description,
            Pageable pageable);

    void deleteByPublicId(UUID publicId);

}
