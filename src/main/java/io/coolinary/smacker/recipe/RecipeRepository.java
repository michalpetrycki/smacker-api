package io.coolinary.smacker.recipe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    Optional<RecipeEntity> findByPublicId(UUID publicId);

    List<RecipeEntity> findRecipesByCategoriesPublicId(UUID categoryPublicId);

    void deleteByPublicId(UUID publicId);

}
