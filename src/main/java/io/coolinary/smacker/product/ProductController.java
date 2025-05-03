package io.coolinary.smacker.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.stream.Collectors;

import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import io.coolinary.smacker.shared.PaginatedResponse;
import io.coolinary.smacker.shared.Routes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static io.coolinary.smacker.product.ProductServiceImpl.toProductAPI;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductServiceImpl productService;

    ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping(Routes.PRODUCTS_ALL)
    ResponseEntity<List<ProductAPI>> getAll() {

        return new ResponseEntity<List<ProductAPI>>(
                productService.getAll().stream().map(product -> toProductAPI(product))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(Routes.PRODUCTS)
    ResponseEntity<PaginatedResponse<ProductAPI>> getPaginated(@RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String filter) {

        Page<ProductEntity> page = productService.getPaginated(pageNo, pageSize, sortBy, sortDirection, filter);
        List<ProductAPI> products = page.getContent().stream()
                .map(product -> toProductAPI(product))
                .collect(Collectors.toList());

        PaginatedResponse<ProductAPI> resp = new PaginatedResponse<ProductAPI>(products, page.getTotalElements());
        return new ResponseEntity<PaginatedResponse<ProductAPI>>(resp, HttpStatus.OK);
    }

    @GetMapping(Routes.PRODUCTS + Routes.PID)
    public ResponseEntity<ProductAPI> getProduct(@PathVariable("publicId") UUID publicId) {
        ProductEntity productEntity = productService.getByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.PRODUCT));
        return new ResponseEntity<ProductAPI>(toProductAPI(productEntity), HttpStatus.OK);
    }

    @PostMapping(Routes.PRODUCTS)
    public ResponseEntity<ProductAPI> createProduct(@RequestBody ProductCreateAPI newProduct) {
        return new ResponseEntity<ProductAPI>(toProductAPI(productService.createProduct(newProduct)),
                HttpStatus.CREATED);
    }

    @PutMapping(Routes.PRODUCTS + Routes.PID)
    public ResponseEntity<ProductAPI> replaceProduct(@RequestBody ProductAPI productAPI,
            @PathVariable("publicId") UUID publicId) {

        Optional<ProductEntity> productToUpdate = productService.getByPublicId(publicId);
        if (!productToUpdate.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ProductAPI>(toProductAPI(productService.updateProduct(
                productToUpdate.get(), productAPI)), HttpStatus.OK);
    }

    @DeleteMapping(Routes.PRODUCTS + Routes.PID)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<Boolean>(productService.deleteProduct(publicId), HttpStatus.OK);
    }

    @DeleteMapping(Routes.PRODUCTS)
    public ResponseEntity<Boolean> deleteAllProducts() {
        return new ResponseEntity<Boolean>(productService.deleteAllProducts(), HttpStatus.OK);
    }

}
