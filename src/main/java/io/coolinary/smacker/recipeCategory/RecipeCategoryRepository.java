package io.coolinary.smacker.recipeCategory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategoryEntity, Long> {

    Optional<RecipeCategoryEntity> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID publicId);

    Page<RecipeCategoryEntity> findByCategoryNameContainingIgnoreCase(String name, Pageable pageable);

    List<RecipeCategoryEntity> findAllByPublicIdIn(Collection<UUID> publicIds);

}
