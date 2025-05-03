package io.coolinary.smacker.recipe;

import io.coolinary.smacker.product.ProductEntity;
import io.coolinary.smacker.product.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RecipeEntityTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductRepository productRepository;

    private RecipeEntity recipe;
    private ProductEntity product;
    private RecipeProduct recipeProduct;

    @BeforeEach
    void setUp() {
        recipe = RecipeEntity.builder()
                .name("Pasta Carbonara")
                .description("Traditional Italian pasta recipe.")
                .build();

        product = ProductEntity.builder()
                .productName("Pasta")
                .description("Spaghetti for Carbonara")
                .build();

        productRepository.save(product);
        recipeRepository.save(recipe);
    }

    @Test
    void testAddProductToRecipe() {
        recipe.addProduct(product, 200);
        recipeRepository.save(recipe);

        assertThat(recipe.getProducts()).hasSize(1);
        assertThat(recipe.getProducts().iterator().next().getProduct().getProductName()).isEqualTo("Pasta");
        assertThat(recipe.getProducts().iterator().next().getAmount()).isEqualTo(200);
    }

    @Test
    void testRemoveProductFromRecipe() {

        recipe.addProduct(product, 200);

        recipeProduct = new RecipeProduct();

        recipeProduct.setRecipe(recipe);
        recipeProduct.setProduct(product);
        recipe.removeProduct(recipeProduct);
        recipeRepository.save(recipe);

        assertThat(recipe.getProducts()).isEmpty();
    }

}