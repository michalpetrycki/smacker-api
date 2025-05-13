package io.coolinary.smacker.productCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.coolinary.smacker.product.ProductEntity;
import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import jakarta.transaction.Transactional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public Optional<ProductCategoryEntity> getByPublicId(UUID publicId) {
        return productCategoryRepository.findByPublicId(publicId);
    }

    public List<ProductCategoryEntity> getAll() {
        return productCategoryRepository.findAll().stream()
                .sorted(Comparator.comparingLong(ProductCategoryEntity::getId))
                .collect(Collectors.toList());
    }

    public Page<ProductCategoryEntity> getPaginated(Integer pageNo, Integer pageSize, String sortBy,
            String sortDirection,
            String filter) {
        int pIdx = pageNo != null ? pageNo : 0;
        int pSize = pageSize != null ? pageSize : 10;
        Sort sort = (sortBy != null)
                ? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
                : Sort.unsorted();
        PageRequest pageable = PageRequest.of(pIdx, pSize, sort);

        if (filter != null && !filter.isBlank()) {
            System.out.println(
                    "pageNo: " + pageNo + ", pageSize: " + pageSize + ", sortBy: " + sortBy + ", sortDirection: "
                            + sortDirection + ", filter: " + filter);
            return productCategoryRepository.findByCategoryNameContainingIgnoreCase(filter, pageable);
        } else {
            System.out.println(
                    "pageNo: " + pageNo + ", pageSize: " + pageSize + ", sortBy: " + sortBy + ", sortDirection: "
                            + sortDirection);
            return productCategoryRepository.findAll(pageable);
        }

    }

    public ProductCategoryEntity createCategory(ProductCategoryCreateAPI createAPI) {
        ProductCategoryAPI productCategoryAPI = new ProductCategoryAPI(UUID.randomUUID(), createAPI.name());
        ProductCategoryEntity productCategoryEntity = toProductCategoryEntity(productCategoryAPI);
        return productCategoryRepository.save(productCategoryEntity);
    }

    public ProductCategoryEntity updateCategory(ProductCategoryEntity productCategoryEntity,
            ProductCategoryAPI productCategoryAPI) {
        productCategoryEntity.setCategoryName(productCategoryAPI.name());
        return productCategoryRepository.save(productCategoryEntity);
    }

    @Transactional
    public Boolean deleteCategory(UUID publicId) {
        try {
            productCategoryRepository.deleteByPublicId(publicId);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }

    }

    public Boolean deleteAllCategories() {
        productCategoryRepository.deleteAll();
        return true;
    }

    public List<ProductEntity> getProductsByCategory(UUID publicId) {
        ProductCategoryEntity category = productCategoryRepository
                .findByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.PRODUCT_CATEGORY));

        return new ArrayList<>(category.getProducts());
    }

    public static ProductCategoryEntity toProductCategoryEntity(ProductCategoryAPI productCategoryAPI) {
        return ProductCategoryEntity.builder().categoryName(productCategoryAPI.name())
                .publicId(productCategoryAPI.publicId()).build();
    }

    public static ProductCategoryAPI toProductCategoryAPI(ProductCategoryEntity productCategoryEntity) {
        return new ProductCategoryAPI(
                productCategoryEntity.getPublicId(),
                productCategoryEntity.getCategoryName());
    }

    // public List<ProductCategoryEntity> getCategoriesByProductId(Long productId) {
    // return this.productCategoryRepository.findCategoriesByProductsId(productId);
    // }

}
