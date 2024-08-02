package io.coolinary.smacker.productCategory;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

import io.coolinary.smacker.product.ProductAPI;
// import io.coolinary.smacker.product.ProductService;
// import io.coolinary.smacker.recipe.Recipe;
// import io.coolinary.smacker.recipeCategory.RecipeCategory;
// import io.coolinary.smacker.recipeCategory.RecipeCategoryNotFoundException;
import io.coolinary.smacker.shared.Routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductCategoryController {

    // @Autowired
    // private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    private final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

    @PostMapping(Routes.PRODUCT_CATEGORIES)
    public ResponseEntity<ProductCategory> newCategory(@RequestBody ProductCategory newCategory) {

        try {
            return new ResponseEntity<ProductCategory>(this.productCategoryService.createProductCategory(newCategory),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(Routes.PRODUCT_CATEGORIES + Routes.ID + Routes.PRODUCTS)
    public ResponseEntity<ProductAPI> createProduct(@PathVariable("id") Long categoryId,
            @RequestBody ProductAPI productRequest) {

        // try {
        // ProductCategory category = productCategoryService.getById(categoryId);
        // Long recipeId = productRequest.getId();

        // // if (recipeId != null && recipeId.longValue() != 0L) {
        // if (recipeId.longValue() != 0L) {

        // Product _product = this.productService.getById(recipeId);
        // category.addProduct(_product);
        // productCategoryService.updateProductCategory(categoryId, category);
        // return new ResponseEntity<ProductAPI>(_product, HttpStatus.OK);

        // }

        // category.addProduct(productRequest);
        // return new
        // ResponseEntity<ProductAPI>(productService.createProduct(productRequest),
        // HttpStatus.OK);
        // } catch (Exception ex) {
        // this.logger.error(ex.getMessage(), ex);
        // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // }
        return new ResponseEntity<>(null);

    }

    @GetMapping(Routes.PRODUCT_CATEGORIES)
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {

        try {
            return new ResponseEntity<List<ProductCategory>>(
                    this.productCategoryService.getAll().stream().collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(Routes.PRODUCT_CATEGORIES + Routes.ID)
    public ResponseEntity<ProductCategory> getCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<ProductCategory>(this.productCategoryService.getById(id), HttpStatus.OK);
    }

    @GetMapping(Routes.PRODUCT_CATEGORIES + Routes.ID + Routes.PRODUCTS)
    public ResponseEntity<List<ProductAPI>> getAllProductsByCategoryId(@PathVariable("id") Long categoryId) {
        // if (!this.productCategoryService.existsById(categoryId)) {
        // throw new ProductCategoryNotFoundException(categoryId);
        // }
        // return new
        // ResponseEntity<List<Product>>(this.productService.getProductsByCategoriesId(categoryId),
        // HttpStatus.CREATED);
        return new ResponseEntity<>(null);
    }

    @PutMapping(Routes.PRODUCT_CATEGORIES + Routes.ID)
    public ResponseEntity<ProductCategory> replaceCategory(@PathVariable("id") Long id,
            @RequestBody ProductCategory newCategory) {

        ProductCategory _category = this.productCategoryService.getById(id);
        _category.setName(newCategory.getName());
        return new ResponseEntity<ProductCategory>(
                this.productCategoryService.updateProductCategory(id, _category),
                HttpStatus.OK);

    }

    @DeleteMapping(Routes.PRODUCT_CATEGORIES + Routes.ID)
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<Boolean>(this.productCategoryService.deleteProductCategory(id),
                HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Routes.PRODUCT_CATEGORIES + Routes.ALL)
    public ResponseEntity<Boolean> deleteAllCategories() {
        return new ResponseEntity<Boolean>(this.productCategoryService.deleteAllProductCategories(),
                HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Routes.PRODUCT_CATEGORIES + Routes.ID + Routes.PRODUCTS + Routes.PRODUCT_ID)
    public ResponseEntity<HttpStatus> deleteProductFromCategory(@PathVariable("id") Long id,
            @PathVariable("productId") Long productId) {
        ProductCategory category = productCategoryService.getById(id);
        category.removeProduct(productId);
        productCategoryService.updateProductCategory(id, category);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
