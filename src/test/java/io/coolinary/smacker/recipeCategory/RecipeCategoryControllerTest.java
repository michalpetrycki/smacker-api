package io.coolinary.smacker.recipeCategory;

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
public class RecipeCategoryControllerTest {

    @Autowired
    // private MockMvc mockMvc;

    @MockBean
    private RecipeCategoryService recipeCategoryService;

    // private ObjectMapper objectMapper = new ObjectMapper();

//     @Test
//     void shouldReturnListOfCategories() throws Exception {

//         List<RecipeCategory> categoriesList = new ArrayList<RecipeCategory>();
//         RecipeCategory categoryBreakfast = new RecipeCategory("Breakfast");
//         RecipeCategory categoryDinner = new RecipeCategory("Dinner");
//         categoriesList.add(categoryBreakfast);
//         categoriesList.add(categoryDinner);

//         when(recipeCategoryService.getAll()).thenReturn(categoriesList);

//         RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Routes.RECIPE_CATEGORIES)
//                 .accept(MediaType.APPLICATION_JSON);

//         MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

//         int actualResult = mvcResult.getResponse().getStatus();
//         int expectedResult = HttpStatus.OK.value();

//         String actualList = mvcResult.getResponse().getContentAsString();
//         String expectedList = objectMapper.writeValueAsString(categoriesList);

//         assertEquals(expectedResult, actualResult, "Response status not as expected!");
//         assertEquals(expectedList, actualList, "Response list of items not as expected!");
//         verify(recipeCategoryService, times(1)).getAll();

//     }

//     @Test
//     void shouldReturnCorrectCategoryById() throws Exception {

//         RecipeCategory categoryBreakfast = new RecipeCategory("Breakfast");
//         categoryBreakfast.setId((13L));

//         when(recipeCategoryService.getById(categoryBreakfast.getId())).thenReturn(categoryBreakfast);

//         RequestBuilder requestBuilder = MockMvcRequestBuilders
//                 .get(Routes.RECIPE_CATEGORIES + "/" + categoryBreakfast.getId())
//                 .accept(MediaType.APPLICATION_JSON);

//         MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

//         int actualResult = mvcResult.getResponse().getStatus();
//         int expectedResult = HttpStatus.OK.value();

//         String actualCategory = mvcResult.getResponse().getContentAsString();
//         String expectedCategory = objectMapper.writeValueAsString(categoryBreakfast);

//         assertEquals(expectedResult, actualResult, "Response status not as expected!");
//         assertEquals(expectedCategory, actualCategory, "Response item not as expected!");
//         verify(recipeCategoryService, times(1)).getById(categoryBreakfast.getId());

//     }

//     @Test
//     void shouldReturnNewRecipeCategoryOnPostRequest() throws Exception {

//         RecipeCategory categoryBreakfast = new RecipeCategory("Breakfast");
//         String categoryBreakfastAsJson = objectMapper.writeValueAsString(categoryBreakfast);

//         when(recipeCategoryService.createRecipeCategory(any(RecipeCategory.class))).thenReturn(categoryBreakfast);

//         RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Routes.RECIPE_CATEGORIES)
//                 .accept(MediaType.APPLICATION_JSON)
//                 .contentType(MediaType.APPLICATION_JSON).content(categoryBreakfastAsJson);

//         MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

//         int actualStatus = mvcResult.getResponse().getStatus();
//         int expectedStatus = HttpStatus.CREATED.value();

//         assertEquals(expectedStatus, actualStatus, "Response status not as expected!");
//         verify(recipeCategoryService, times(1)).createRecipeCategory(refEq(categoryBreakfast));

//     }

//     @Test
//     void shouldReturnUpdatedRecipeCategoryOnPutRequest() throws Exception {

//         RecipeCategory categoryBreakfast = new RecipeCategory("Breakfast");
//         categoryBreakfast.setId((13L));
//         RecipeCategory updatedCategoryBreakfast = new RecipeCategory("Updated breakfast");
//         updatedCategoryBreakfast.setId(categoryBreakfast.getId());
//         String updatedCategoryBreakfastAsJson = objectMapper.writeValueAsString(updatedCategoryBreakfast);

//         when(recipeCategoryService.getById(categoryBreakfast.getId())).thenReturn(categoryBreakfast);
//         when(recipeCategoryService.updateRecipeCategory(categoryBreakfast.getId(), updatedCategoryBreakfast))
//                 .thenReturn(updatedCategoryBreakfast);

//         RequestBuilder requestBuilder = MockMvcRequestBuilders
//                 .put(Routes.RECIPE_CATEGORIES + "/" + categoryBreakfast.getId())
//                 .accept(MediaType.APPLICATION_JSON)
//                 .contentType(MediaType.APPLICATION_JSON).content(updatedCategoryBreakfastAsJson);

//         MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

//         int actualStatus = mvcResult.getResponse().getStatus();
//         int expectedStatus = HttpStatus.OK.value();

//         String actualCategory = mvcResult.getResponse().getContentAsString();
//         String expectedCategory = updatedCategoryBreakfastAsJson;

//         assertEquals(expectedStatus, actualStatus, "Response status not as expected!");
//         assertEquals(expectedCategory, actualCategory, "Response category not as expected!");
//         verify(recipeCategoryService, times(1)).updateRecipeCategory(categoryBreakfast.getId(),
//                 updatedCategoryBreakfast);

//     }

//     @Test
//     void shouldReturnTrueOnDeleteByIdRequest() throws Exception {

//         RecipeCategory categoryBreakfast = new RecipeCategory("Breakfast");
//         categoryBreakfast.setId(13L);

//         when(recipeCategoryService.deleteRecipeCategory(categoryBreakfast.getId())).thenReturn(true);

//         RequestBuilder requestBuilder = MockMvcRequestBuilders
//                 .delete(Routes.RECIPE_CATEGORIES + "/" + categoryBreakfast.getId())
//                 .accept(MediaType.APPLICATION_JSON)
//                 .contentType(MediaType.APPLICATION_JSON);

//         MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

//         int actualStatus = mvcResult.getResponse().getStatus();
//         int expectedStatus = HttpStatus.OK.value();

//         String actualResult = mvcResult.getResponse().getContentAsString();
//         String expectedResult = objectMapper.writeValueAsString(true);

//         assertEquals(expectedStatus, actualStatus, "Response status not as expected!");
//         assertEquals(expectedResult, actualResult, "Repsonse delete success status not as expected!");
//         verify(recipeCategoryService, times(1)).deleteRecipeCategory(categoryBreakfast.getId());

//     }

//     @Test
//     void shouldReturnTrueOnDeleteAllIdRequest() throws Exception {

//         RecipeCategory categoryBreakfast = new RecipeCategory("Breakfast");
//         categoryBreakfast.setId(13L);

//         when(recipeCategoryService.deleteAllRecipeCategories()).thenReturn(true);

//         RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(Routes.RECIPE_CATEGORIES)
//                 .accept(MediaType.APPLICATION_JSON)
//                 .contentType(MediaType.APPLICATION_JSON);

//         MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

//         int actualStatus = mvcResult.getResponse().getStatus();
//         int expectedStatus = HttpStatus.OK.value();

//         String actualResult = mvcResult.getResponse().getContentAsString();
//         String expectedResult = objectMapper.writeValueAsString(true);

//         assertEquals(expectedStatus, actualStatus, "Response status not as expected!");
//         assertEquals(expectedResult, actualResult, "Response delete success status not as expected!");
//         verify(recipeCategoryService, times(1)).deleteAllRecipeCategories();

//     }

}
