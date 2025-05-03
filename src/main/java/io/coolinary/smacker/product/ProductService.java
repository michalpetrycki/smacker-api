package io.coolinary.smacker.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

public interface ProductService {

        List<ProductEntity> getAll();

        List<ProductEntity> getProductsByCategoriesId(Long categoryId);

        Page<ProductEntity> getPaginated(Integer pageNo, Integer pageSize, String sortBy, String sortOrder,
                        String filter);

        Optional<ProductEntity> getByPublicId(UUID publicId);

        ProductEntity createProduct(ProductCreateAPI createAPI);

        ProductEntity updateProduct(ProductEntity productEntity, ProductAPI productAPI)
                        throws DataIntegrityViolationException;

        Boolean deleteProduct(UUID publicId);

        Boolean deleteAllProducts();

}
