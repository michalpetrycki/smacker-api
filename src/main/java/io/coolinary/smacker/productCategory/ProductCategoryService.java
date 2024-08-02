package io.coolinary.smacker.productCategory;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    List<ProductCategory> getAll() {
        return this.productCategoryRepository.findAll();
    }

    public boolean existsById(Long id) {
        return this.productCategoryRepository.findById(id)
                .orElseThrow(() -> new ProductCategoryNotFoundException(id)) != null;
    }

    ProductCategory getById(Long id) {
        return this.productCategoryRepository.findById(id).orElseThrow(() -> new ProductCategoryNotFoundException(id));
    }

    public List<ProductCategory> getCategoriesByProductId(Long productId) {
        return this.productCategoryRepository.findCategoriesByProductsId(productId);
    }

    ProductCategory createProductCategory(ProductCategory productCategory) {
        return this.productCategoryRepository.save(productCategory);
    }

    ProductCategory updateProductCategory(Long id, ProductCategory productCategory) {
        return this.productCategoryRepository.save(productCategory);
    }

    Boolean deleteProductCategory(Long id) {
        try {
            this.productCategoryRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }

    }

    Boolean deleteAllProductCategories() {
        this.productCategoryRepository.deleteAll();
        return true;
    }

}
