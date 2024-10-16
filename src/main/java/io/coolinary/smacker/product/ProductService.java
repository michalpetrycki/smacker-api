package io.coolinary.smacker.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    public boolean existsByPublicId(UUID publicId) {
        return productRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.PRODUCT)) != null;
    }

    public ProductEntity getByPublicId(UUID publicId) {
        return productRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.PRODUCT));
    }

    // public ProductEntity getById(Long id) {
    // return productRepository.findById(id).orElseThrow(() -> new
    // ElementNotFoundException(id, "product"));
    // }

    public List<ProductEntity> getProductsByCategoriesId(Long categoryId) {
        return productRepository.findProductsByCategoriesId(categoryId);
    }

    public ProductEntity createProduct(ProductCreateAPI productCreateAPI) {
        ProductEntity.ProductEntityBuilder<?, ?> productBuilder = ProductEntity.builder();

        productBuilder.publicId(UUID.randomUUID());
        productBuilder.name(productCreateAPI.name());
        productCreateAPI.description().ifPresent(productBuilder::description);
        productCreateAPI.carbs().ifPresent(productBuilder::carbs);
        productCreateAPI.fiber().ifPresent(productBuilder::fiber);
        productCreateAPI.fats().ifPresent(productBuilder::fats);
        productCreateAPI.proteins().ifPresent(productBuilder::proteins);

        return productRepository.save(productBuilder.build());
    }

    ProductEntity updateProduct(UUID publicId, ProductAPI productAPI) {

        ProductEntity productEntity = getByPublicId(publicId);
        if (productEntity == null) {
            throw new ElementNotFoundException(publicId, EntityType.PRODUCT);
        }

        productEntity.setName(productAPI.name());
        productAPI.description().ifPresent(productEntity::setDescription);
        productAPI.carbs().ifPresent(productEntity::setCarbs);
        productAPI.fats().ifPresent(productEntity::setFats);
        productAPI.fiber().ifPresent(productEntity::setFiber);
        productAPI.proteins().ifPresent(productEntity::setProteins);

        return productRepository.save(productEntity);
    }

    Boolean deleteProduct(UUID publicId) {
        try {
            productRepository.deleteByPublicId(publicId);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    Boolean deleteAllProducts() {
        productRepository.deleteAll();
        return true;
    }

    public static ProductAPI toProductAPI(ProductEntity product) {
        return new ProductAPI(
                product.getPublicId(),
                product.getUpdateTimestamp(),
                product.getName(),
                Optional.ofNullable(product.getDescription()),
                Optional.ofNullable(product.getCarbs()),
                Optional.ofNullable(product.getFiber()),
                Optional.ofNullable(product.getFats()),
                Optional.ofNullable(product.getProteins()));
    }

    public static ProductEntity toProductEntity(ProductAPI productAPI) {
        ProductEntity.ProductEntityBuilder<?, ?> productEntity = ProductEntity.builder();
        productEntity.name(productAPI.name());
        productEntity.publicId(productAPI.publicId());
        productEntity.updateTimestamp(productAPI.lastUpdate());
        productAPI.description().ifPresent(productEntity::description);
        productAPI.carbs().ifPresent(productEntity::carbs);
        productAPI.fiber().ifPresent(productEntity::fiber);
        productAPI.fats().ifPresent(productEntity::fats);
        productAPI.proteins().ifPresent(productEntity::proteins);
        return productEntity.build();
    }

}
