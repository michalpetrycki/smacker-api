package io.coolinary.smacker;

import org.junit.jupiter.api.Test;

import io.coolinary.smacker.productCategory.ProductCategoryController;
import io.coolinary.smacker.recipeCategory.RecipeCategoryController;
import io.coolinary.smacker.tool.ToolController;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class SmackerApplicationTests {

	@Autowired
	private ToolController toolController;

	@Autowired
	private ProductCategoryController productCategoryController;

	@Autowired
	private RecipeCategoryController recipeCategoryController;

	@Test
	void contextLoads() throws Exception {
		assertThat(toolController).isNotNull();
		assertThat(productCategoryController).isNotNull();
		assertThat(recipeCategoryController).isNotNull();
	}

}
