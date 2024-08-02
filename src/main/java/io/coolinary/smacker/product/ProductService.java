package io.coolinary.smacker.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    public boolean existsById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id)) != null;
    }

    public ProductEntity getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<ProductEntity> getProductsByCategoriesId(Long categoryId) {
        return productRepository.findProductsByCategoriesId(categoryId);
    }

    public ProductEntity createProduct(ProductCreateAPI productCreateAPI) {
        ProductEntity.ProductEntityBuilder productBuilder = ProductEntity.builder();

        productBuilder.publicId(UUID.randomUUID());
        productBuilder.name(productCreateAPI.name());
        productCreateAPI.description().ifPresent(productBuilder::description);
        productCreateAPI.carbs().ifPresent(productBuilder::carbs);
        productCreateAPI.fiber().ifPresent(productBuilder::fiber);
        productCreateAPI.fats().ifPresent(productBuilder::fats);
        productCreateAPI.proteins().ifPresent(productBuilder::proteins);

        return productRepository.save(productBuilder.build());
    }

    ProductEntity updateProduct(Long id, ProductAPI productAPI) {

        ProductEntity productEntity = getById(id);
        if (productEntity == null) {
            throw new ProductNotFoundException(id);
        }

        productEntity.setName(productAPI.name());
        productAPI.description().ifPresent(productEntity::setDescription);
        productAPI.carbs().ifPresent(productEntity::setCarbs);
        productAPI.fats().ifPresent(productEntity::setFats);
        productAPI.fiber().ifPresent(productEntity::setFiber);
        productAPI.proteins().ifPresent(productEntity::setProteins);

        return productRepository.save(productEntity);
    }

    Boolean deleteProduct(Long id) {
        try {
            ProductEntity productEntity = getById(id);
            productRepository.deleteById(productEntity.getId());
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
}
