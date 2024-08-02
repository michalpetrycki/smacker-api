package io.coolinary.smacker.productCategory;

// import java.util.List;
// import java.util.ArrayList;
// import org.junit.jupiter.api.Test;
// import static org.mockito.Mockito.when;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import org.springframework.http.MediaType;
// import io.coolinary.smacker.shared.Routes;
// import org.springframework.http.HttpStatus;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.refEq;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.boot.test.mock.mockito.MockBean;
// import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductCategoryControllerTest {

    @Autowired
    // private MockMvc mockMvc;

    @MockBean
    private ProductCategoryService productCategoryService;

    // private ObjectMapper objectMapper = new ObjectMapper();

    // @Test
    // void shouldReturnListOfCategories() throws Exception {

    //     List<ProductCategory> categoriesList = new ArrayList<ProductCategory>();
    //     ProductCategory meat = new ProductCategory("Meat");
    //     ProductCategory vegetables = new ProductCategory("Vegetables");
    //     categoriesList.add(meat);
    //     categoriesList.add(vegetables);

    //     when(productCategoryService.getAll()).thenReturn(categoriesList);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Routes.PRODUCT_CATEGORIES)
    //             .accept(MediaType.APPLICATION_JSON);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualResult = mvcResult.getResponse().getStatus();
    //     int expectedResult = HttpStatus.OK.value();

    //     String actualList = mvcResult.getResponse().getContentAsString();
    //     String expectedList = objectMapper.writeValueAsString(categoriesList);

    //     assertEquals(expectedResult, actualResult, "Response status not as expected!");
    //     assertEquals(expectedList, actualList, "Response list of items not as expected!");
    //     verify(productCategoryService, times(1)).getAll();

    // }

    // @Test
    // void shouldReturnCorrectCategoryById() throws Exception {

    //     ProductCategory categoryMeat = new ProductCategory("Meat");
    //     categoryMeat.setId((13L));

    //     when(productCategoryService.getById(categoryMeat.getId())).thenReturn(categoryMeat);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders
    //             .get(Routes.PRODUCT_CATEGORIES + "/" + categoryMeat.getId())
    //             .accept(MediaType.APPLICATION_JSON);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualResult = mvcResult.getResponse().getStatus();
    //     int expectedResult = HttpStatus.OK.value();

    //     String actualCategory = mvcResult.getResponse().getContentAsString();
    //     String expectedCategory = objectMapper.writeValueAsString(categoryMeat);

    //     assertEquals(expectedResult, actualResult, "Response status not as expected!");
    //     assertEquals(expectedCategory, actualCategory, "Response item not as expected!");
    //     verify(productCategoryService, times(1)).getById(categoryMeat.getId());

    // }

    // @Test
    // void shouldReturnNewProductCategoryOnPostRequest() throws Exception {

    //     ProductCategory categoryMeat = new ProductCategory("Meat");
    //     String categoryMeatAsJson = objectMapper.writeValueAsString(categoryMeat);

    //     when(productCategoryService.createProductCategory(any(ProductCategory.class))).thenReturn(categoryMeat);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Routes.PRODUCT_CATEGORIES)
    //             .accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON).content(categoryMeatAsJson);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualStatus = mvcResult.getResponse().getStatus();
    //     int expectedStatus = HttpStatus.CREATED.value();

    //     assertEquals(expectedStatus, actualStatus, "Response status not as expected!");
    //     verify(productCategoryService, times(1)).createProductCategory(refEq(categoryMeat));

    // }

    // @Test
    // void shouldReturnUpdatedProductCategoryOnPutRequest() throws Exception {

    //     ProductCategory categoryMeat = new ProductCategory("Meat");
    //     categoryMeat.setId((13L));
    //     ProductCategory updatedCategoryMeat = new ProductCategory("Updated meat");
    //     updatedCategoryMeat.setId(categoryMeat.getId());
    //     String updatedCategoryMeatAsJson = objectMapper.writeValueAsString(updatedCategoryMeat);

    //     when(productCategoryService.getById(categoryMeat.getId())).thenReturn(categoryMeat);
    //     when(productCategoryService.updateProductCategory(categoryMeat.getId(), updatedCategoryMeat))
    //             .thenReturn(updatedCategoryMeat);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders
    //             .put(Routes.PRODUCT_CATEGORIES + "/" + categoryMeat.getId())
    //             .accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON).content(updatedCategoryMeatAsJson);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualStatus = mvcResult.getResponse().getStatus();
    //     int expectedStatus = HttpStatus.OK.value();

    //     String actualCategory = mvcResult.getResponse().getContentAsString();
    //     String expectedCategory = updatedCategoryMeatAsJson;

    //     assertEquals(expectedStatus, actualStatus, "Response status not as expected!");
    //     assertEquals(expectedCategory, actualCategory, "Response category not as expected!");
    //     verify(productCategoryService, times(1)).updateProductCategory(categoryMeat.getId(), updatedCategoryMeat);

    // }

    // @Test
    // void shouldReturnTrueOnDeleteByIdRequest() throws Exception {

    //     ProductCategory categoryMeat = new ProductCategory("Meat");
    //     categoryMeat.setId(13L);

    //     when(productCategoryService.deleteProductCategory(categoryMeat.getId())).thenReturn(true);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders
    //             .delete(Routes.PRODUCT_CATEGORIES + "/" + categoryMeat.getId())
    //             .accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualStatus = mvcResult.getResponse().getStatus();
    //     int expectedStatus = HttpStatus.OK.value();

    //     String actualResult = mvcResult.getResponse().getContentAsString();
    //     String expectedResult = objectMapper.writeValueAsString(true);

    //     assertEquals(expectedStatus, actualStatus, "Response status not as expected!");
    //     assertEquals(expectedResult, actualResult, "Repsonse delete success status not as expected!");
    //     verify(productCategoryService, times(1)).deleteProductCategory(categoryMeat.getId());

    // }

    // @Test
    // void shouldReturnTrueOnDeleteAllIdRequest() throws Exception {

    //     ProductCategory categoryMeat = new ProductCategory("Meat");
    //     categoryMeat.setId(13L);

    //     when(productCategoryService.deleteAllProductCategories()).thenReturn(true);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(Routes.PRODUCT_CATEGORIES)
    //             .accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualStatus = mvcResult.getResponse().getStatus();
    //     int expectedStatus = HttpStatus.OK.value();

    //     String actualResult = mvcResult.getResponse().getContentAsString();
    //     String expectedResult = objectMapper.writeValueAsString(true);

    //     assertEquals(expectedStatus, actualStatus, "Response status not as expected!");
    //     assertEquals(expectedResult, actualResult, "Response delete success status not as expected!");
    //     verify(productCategoryService, times(1)).deleteAllProductCategories();

    // }

}
