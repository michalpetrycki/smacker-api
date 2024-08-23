package io.coolinary.smacker.product;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import io.coolinary.smacker.shared.Routes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.coolinary.smacker.productCategory.ProductCategory;
import io.coolinary.smacker.productCategory.ProductCategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static io.coolinary.smacker.product.ProductService.toProductAPI;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(Routes.PRODUCTS)
    ResponseEntity<List<ProductAPI>> getAll() {

        List<ProductEntity> products = this.productService.getAll();
        return new ResponseEntity<List<ProductAPI>>(
                products.stream().map(product -> toProductAPI(product)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Routes.PRODUCTS + Routes.ID)
    public ResponseEntity<ProductAPI> getProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<ProductAPI>(toProductAPI(productService.getById(id)), HttpStatus.OK);
    }

    @PostMapping(Routes.PRODUCTS)
    public ResponseEntity<ProductAPI> createProduct(@RequestBody ProductCreateAPI newProduct) {

        try {
            return new ResponseEntity<ProductAPI>(toProductAPI(productService.createProduct(newProduct)),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(Routes.PRODUCTS + Routes.ID + Routes.PRODUCT_CATEGORIES)
    public ResponseEntity<List<ProductCategory>> getAllCategoriesByProductId(@PathVariable("id") Long productId) {
        if (!productService.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
        return new ResponseEntity<List<ProductCategory>>(
                productCategoryService.getCategoriesByProductId(productId),
                HttpStatus.CREATED);
    }

    @PutMapping(Routes.PRODUCTS + Routes.ID)
    public ResponseEntity<ProductAPI> updateProduct(@PathVariable("id") Long id, @RequestBody ProductAPI productAPI) {
        return new ResponseEntity<ProductAPI>(toProductAPI(productService.updateProduct(id, productAPI)),
                HttpStatus.OK);
    }

    @DeleteMapping(Routes.PRODUCTS + Routes.ID)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<Boolean>(productService.deleteProduct(id), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Routes.PRODUCTS + Routes.ALL)
    public ResponseEntity<Boolean> deleteAllProducts() {
        return new ResponseEntity<Boolean>(productService.deleteAllProducts(),
                HttpStatus.NO_CONTENT);
    }

}
