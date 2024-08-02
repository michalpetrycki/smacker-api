package io.coolinary.smacker.productCategory;

public class ProductCategoryNotFoundException extends RuntimeException {
    ProductCategoryNotFoundException(Long id) {
        super("Could not find product category: " + id);
    }
}
