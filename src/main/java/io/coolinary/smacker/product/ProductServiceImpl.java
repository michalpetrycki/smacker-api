package io.coolinary.smacker.product;

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

import io.coolinary.smacker.productCategory.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import static io.coolinary.smacker.productCategory.ProductCategoryServiceImpl.toProductCategoryEntity;
import static io.coolinary.smacker.productCategory.ProductCategoryServiceImpl.toProductCategoryAPI;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public Optional<ProductEntity> getByPublicId(UUID publicId) {
        return this.productRepository.findByPublicId(publicId);
    }

    public List<ProductEntity> getAll() {
        return this.productRepository.findAll().stream().sorted(Comparator.comparingLong(ProductEntity::getId))
                .collect(Collectors.toList());
    }

    public Page<ProductEntity> getPaginated(Integer pageNo, Integer pageSize, String sortBy, String sortDirection,
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
            return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(filter,
                    filter,
                    pageable);
        } else {
            System.out.println(
                    "pageNo: " + pageNo + ", pageSize: " + pageSize + ", sortBy: " + sortBy + ", sortDirection: "
                            + sortDirection);
            return productRepository.findAll(pageable);
        }

    }

    public ProductEntity createProduct(ProductCreateAPI productCreateAPI) {
        ProductAPI productAPI = new ProductAPI(
                UUID.randomUUID(),
                productCreateAPI.name(),
                productCreateAPI.description(),
                productCreateAPI.carbs(),
                productCreateAPI.fiber(),
                productCreateAPI.fats(),
                productCreateAPI.proteins(),
                productCreateAPI.categories());
        ProductEntity productEntity = toProductEntity(productAPI);
        return this.productRepository.save(productEntity);
    }

    public ProductEntity updateProduct(ProductEntity productEntity, ProductAPI productAPI) {
        productEntity.setProductName(productAPI.name());
        productAPI.description().ifPresent(productEntity::setDescription);
        productAPI.carbs().ifPresent(productEntity::setCarbs);
        productAPI.fiber().ifPresent(productEntity::setFiber);
        productAPI.fats().ifPresent(productEntity::setFats);
        productAPI.proteins().ifPresent(productEntity::setProteins);
        productEntity.setCategories(productAPI.categories().stream().map(category -> toProductCategoryEntity(category))
                .collect(Collectors.toSet()));

        return productRepository.save(productEntity);
    }

    @Transactional
    public Boolean deleteProduct(UUID publicId) {
        try {
            productRepository.deleteByPublicId(publicId);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }

    }

    public Boolean deleteAllProducts() {
        productRepository.deleteAll();
        return true;
    }

    public static ProductEntity toProductEntity(ProductAPI productAPI) {
        return ProductEntity.builder()
                .publicId(productAPI.publicId())
                .productName(productAPI.name())
                .description(productAPI.description().get())
                .carbs(productAPI.carbs().get())
                .fiber(productAPI.fiber().get())
                .fats(productAPI.fats().get())
                .proteins(productAPI.proteins().get())
                .categories(productAPI.categories().stream().map(category -> toProductCategoryEntity(category))
                        .collect(Collectors.toSet()))
                .build();
    }

    public static ProductAPI toProductAPI(ProductEntity productEntity) {
        return new ProductAPI(
                productEntity.getPublicId(),
                productEntity.getProductName(),
                Optional.of(productEntity.getDescription()),
                Optional.of(productEntity.getCarbs()),
                Optional.of(productEntity.getFiber()),
                Optional.of(productEntity.getFats()),
                Optional.of(productEntity.getProteins()),
                productEntity.getCategories().stream().map(category -> toProductCategoryAPI(category))
                        .collect(Collectors.toList()));
    }

    /** To w ogole potrzebne??? */
    public List<ProductEntity> getProductsByCategoriesId(Long categoryId) {
        return productRepository.findProductsByCategoriesId(categoryId);
    }

}
