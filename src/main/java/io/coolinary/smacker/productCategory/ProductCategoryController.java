package io.coolinary.smacker.productCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.stream.Collectors;

import io.coolinary.smacker.product.ProductAPI;
import io.coolinary.smacker.product.ProductCreateAPI;
import io.coolinary.smacker.product.ProductEntity;
import io.coolinary.smacker.product.ProductServiceImpl;
import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import io.coolinary.smacker.shared.PaginatedResponse;
import io.coolinary.smacker.shared.Routes;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import static io.coolinary.smacker.productCategory.ProductCategoryServiceImpl.toProductCategoryAPI;
import static io.coolinary.smacker.product.ProductServiceImpl.toProductAPI;

@RestController
@RequestMapping("/api")
public class ProductCategoryController {

    private final ProductCategoryServiceImpl productCategoryService;
    private final ProductServiceImpl productService;

    ProductCategoryController(ProductCategoryServiceImpl productCategoryService, ProductServiceImpl productService) {
        this.productCategoryService = productCategoryService;
        this.productService = productService;
    }

    @GetMapping(Routes.PRODUCT_CATEGORIES_ALL)
    ResponseEntity<List<ProductCategoryAPI>> getAll() {

        return new ResponseEntity<List<ProductCategoryAPI>>(
                this.productCategoryService.getAll().stream().map(category -> toProductCategoryAPI(category))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(Routes.PRODUCT_CATEGORIES)
    ResponseEntity<PaginatedResponse<ProductCategoryAPI>> getPaginated(@RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String filter) {

        Page<ProductCategoryEntity> page = this.productCategoryService.getPaginated(pageNo, pageSize, sortBy,
                sortDirection, filter);
        List<ProductCategoryAPI> categories = page.getContent().stream()
                .map(category -> toProductCategoryAPI(category))
                .collect(Collectors.toList());

        PaginatedResponse<ProductCategoryAPI> resp = new PaginatedResponse<ProductCategoryAPI>(categories,
                page.getTotalElements());
        return new ResponseEntity<PaginatedResponse<ProductCategoryAPI>>(resp, HttpStatus.OK);
    }

    @GetMapping(Routes.PRODUCT_CATEGORIES + Routes.PID)
    public ResponseEntity<ProductCategoryAPI> getTool(@PathVariable("publicId") UUID publicId) {
        ProductCategoryEntity productCategoryEntity = this.productCategoryService.getByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.PRODUCT_CATEGORY));
        return new ResponseEntity<ProductCategoryAPI>(toProductCategoryAPI(productCategoryEntity), HttpStatus.OK);
    }

    @PostMapping(Routes.PRODUCT_CATEGORIES)
    public ResponseEntity<ProductCategoryAPI> createCategory(@RequestBody ProductCategoryCreateAPI newCategory) {
        return new ResponseEntity<ProductCategoryAPI>(
                toProductCategoryAPI(this.productCategoryService.createCategory(newCategory)),
                HttpStatus.CREATED);
    }

    @PutMapping(Routes.PRODUCT_CATEGORIES + Routes.PID)
    public ResponseEntity<ProductCategoryAPI> replaceCategory(@RequestBody ProductCategoryAPI productCategoryAPI,
            @PathVariable("publicId") UUID publicId) {

        Optional<ProductCategoryEntity> categoryToUpdate = this.productCategoryService.getByPublicId(publicId);
        if (!categoryToUpdate.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ProductCategoryAPI>(toProductCategoryAPI(this.productCategoryService.updateCategory(
                categoryToUpdate.get(), productCategoryAPI)), HttpStatus.OK);
    }

    @DeleteMapping(Routes.PRODUCT_CATEGORIES + Routes.PID)
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<Boolean>(this.productCategoryService.deleteCategory(publicId), HttpStatus.OK);
    }

    @DeleteMapping(Routes.PRODUCT_CATEGORIES)
    public ResponseEntity<Boolean> deleteAllCategories() {
        return new ResponseEntity<Boolean>(this.productCategoryService.deleteAllCategories(), HttpStatus.OK);
    }

    @PostMapping(Routes.PRODUCT_CATEGORIES + Routes.PID + Routes.PRODUCTS)
    public ResponseEntity<ProductCategoryAPI> createProduct(@PathVariable("publicId") UUID categoryPid,
            @RequestBody ProductCreateAPI productCreateAPI) {

        Optional<ProductCategoryEntity> categoryEntity = productCategoryService.getByPublicId(categoryPid);

        categoryEntity.ifPresentOrElse(category -> {
            ProductEntity product = productService.createProduct(productCreateAPI);
            category.addProduct(product);
            productCategoryService.updateCategory(category, toProductCategoryAPI(category));
        }, () -> {
            throw new ElementNotFoundException(categoryPid, EntityType.PRODUCT_CATEGORY);
        });

        return new ResponseEntity<ProductCategoryAPI>(toProductCategoryAPI(categoryEntity.get()),
                HttpStatus.OK);

    }

    @GetMapping(Routes.PRODUCT_CATEGORIES + Routes.PID + Routes.PRODUCTS)
    public ResponseEntity<List<ProductAPI>> getAllProductsByCategoryId(@PathVariable("publicId") UUID categoryPid) {
        return new ResponseEntity<List<ProductAPI>>(
                productCategoryService.getProductsByCategory(categoryPid).stream().map(product -> toProductAPI(product))
                        .collect(Collectors.toList()),
                HttpStatus.OK);

    }

    // @DeleteMapping(Routes.PRODUCT_CATEGORIES + Routes.PID + Routes.PRODUCTS +
    // Routes.PRODUCT_ID)
    // public ResponseEntity<HttpStatus>
    // deleteProductFromCategory(@PathVariable("publicId") UUID publicId,
    // @PathVariable("productId") Long productId) {
    // ProductCategoryEntity category =
    // productCategoryService.getByPublicId(publicId)
    // .orElseThrow(() -> new ElementNotFoundException(publicId,
    // EntityType.PRODUCT_CATEGORY));
    // category.removeProduct(productId);
    // productCategoryService.updateCategory(category,
    // toProductCategoryAPI(category));
    // return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    // }

}
