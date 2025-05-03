package io.coolinary.smacker.productCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

@Service
public interface ProductCategoryService {

        List<ProductCategoryEntity> getAll();

        Page<ProductCategoryEntity> getPaginated(Integer pageNo, Integer pageSize, String sortBy, String sortOrder,
                        String filter);

        Optional<ProductCategoryEntity> getByPublicId(UUID publicId);

        ProductCategoryEntity createCategory(ProductCategoryCreateAPI createAPI);

        ProductCategoryEntity updateCategory(ProductCategoryEntity productCategoryEntity,
                        ProductCategoryAPI productCategoryAPI) throws DataIntegrityViolationException;

        Boolean deleteCategory(UUID publicId);

        Boolean deleteAllCategories();

}
