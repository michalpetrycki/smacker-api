package io.coolinary.smacker.recipeCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

@Service
public interface RecipeCategoryService {

        List<RecipeCategoryEntity> getAll();

        Page<RecipeCategoryEntity> getPaginated(Integer pageNo, Integer pageSize, String sortBy, String sortOrder,
                        String filter);

        Optional<RecipeCategoryEntity> getByPublicId(UUID publicId);

        RecipeCategoryEntity createCategory(RecipeCategoryCreateAPI createAPI);

        RecipeCategoryEntity updateCategory(RecipeCategoryEntity recipeCategoryEntity,
                        RecipeCategoryAPI recipeCategoryAPI) throws DataIntegrityViolationException;

        Boolean deleteCategory(UUID publicId);

        Boolean deleteAllCategories();

}
