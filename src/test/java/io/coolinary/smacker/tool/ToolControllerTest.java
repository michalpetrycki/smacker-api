package io.coolinary.smacker.tool;

// import java.util.List;
// import java.util.ArrayList;
// import org.junit.jupiter.api.Test;
// import static org.mockito.Mockito.when;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import io.coolinary.smacker.shared.Routes;
// import org.springframework.http.MediaType;
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
public class ToolControllerTest {

    @Autowired
    // private MockMvc mockMvc;

    @MockBean
    private ToolService toolService;

    // private ObjectMapper objectMapper = new ObjectMapper();

    // @Test
    // void shouldReturnListOfTools() throws Exception {

    //     List<ToolEntity> toolsList = new ArrayList<ToolEntity>();
    //     ToolEntity toolMixer = new ToolEntity("Hand mixer");
    //     ToolEntity toolSpatula = new ToolEntity("Wooden spatula");
    //     toolsList.add(toolMixer);
    //     toolsList.add(toolSpatula);

    //     when(toolService.getAll()).thenReturn(toolsList);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Routes.TOOLS).accept(MediaType.APPLICATION_JSON);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualResult = mvcResult.getResponse().getStatus();
    //     int expectedResult = HttpStatus.OK.value();

    //     String actualList = mvcResult.getResponse().getContentAsString();
    //     String expectedList = objectMapper.writeValueAsString(toolsList);

    //     assertEquals(expectedResult, actualResult, "Result not as expected!");
    //     assertEquals(expectedList, actualList, "Result not as expected!");
    //     verify(toolService, times(1)).getAll();

    // }

    // @Test
    // void shouldReturnCorrectToolById() throws Exception {

    //     ToolEntity toolMixer = new ToolEntity("Hand mixer");
    //     toolMixer.setId((13L));

    //     when(toolService.getById(toolMixer.getId())).thenReturn(toolMixer);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Routes.TOOLS + "/" + toolMixer.getId())
    //             .accept(MediaType.APPLICATION_JSON);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualResult = mvcResult.getResponse().getStatus();
    //     int expectedResult = HttpStatus.OK.value();

    //     String actualTool = mvcResult.getResponse().getContentAsString();
    //     String expectedTool = objectMapper.writeValueAsString(toolMixer);

    //     assertEquals(expectedResult, actualResult, "Result not as expected!");
    //     assertEquals(expectedTool, actualTool, "Result not as expected!");
    //     verify(toolService, times(1)).getById(toolMixer.getId());

    // }

    // @Test
    // void shouldReturnNewToolOnPostRequest() throws Exception {

    //     ToolEntity tool = new ToolEntity("Hand mixer");
    //     String toolAsJson = objectMapper.writeValueAsString(tool);

    //     when(toolService.createTool(any(ToolEntity.class))).thenReturn(tool);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Routes.TOOLS).accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON).content(toolAsJson);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualResult = mvcResult.getResponse().getStatus();
    //     int expectedResult = HttpStatus.CREATED.value();

    //     assertEquals(expectedResult, actualResult, "Result not as expected!");
    //     verify(toolService, times(1)).createTool(refEq(tool));

    // }

    // @Test
    // void shouldReturnUpdatedToolOnPutRequest() throws Exception {

    //     ToolEntity toolMixer = new ToolEntity("Hand mixer");
    //     toolMixer.setId((13L));
    //     ToolEntity updatedToolMixer = new ToolEntity("Updated hand mixer");
    //     updatedToolMixer.setId(toolMixer.getId());
    //     String updatedToolMixerAsJson = objectMapper.writeValueAsString(updatedToolMixer);

    //     when(toolService.getById(toolMixer.getId())).thenReturn(toolMixer);
    //     when(toolService.updateTool(toolMixer.getId(), updatedToolMixer)).thenReturn(updatedToolMixer);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.put(Routes.TOOLS + "/" + toolMixer.getId())
    //             .accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON).content(updatedToolMixerAsJson);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualResult = mvcResult.getResponse().getStatus();
    //     int expectedResult = HttpStatus.OK.value();

    //     String actualTool = mvcResult.getResponse().getContentAsString();
    //     String expectedTool = updatedToolMixerAsJson;

    //     assertEquals(expectedResult, actualResult, "Result not as expected!");
    //     assertEquals(expectedTool, actualTool, "Result not as expected!");
    //     verify(toolService, times(1)).updateTool(toolMixer.getId(), updatedToolMixer);

    // }

    // @Test
    // void shouldReturnTrueOnDeleteByIdRequest() throws Exception {

    //     ToolEntity toolMixer = new ToolEntity("Hand mixer");
    //     toolMixer.setId(13L);

    //     when(toolService.deleteTool(toolMixer.getId())).thenReturn(true);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(Routes.TOOLS + "/" + toolMixer.getId())
    //             .accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualStatus = mvcResult.getResponse().getStatus();
    //     int expectedStatus = HttpStatus.OK.value();

    //     String actualResult = mvcResult.getResponse().getContentAsString();
    //     String expectedResult = objectMapper.writeValueAsString(true);

    //     assertEquals(expectedStatus, actualStatus, "Result not as expected!");
    //     assertEquals(expectedResult, actualResult, "Result not as expected!");
    //     verify(toolService, times(1)).deleteTool(toolMixer.getId());

    // }

    // @Test
    // void shouldReturnTrueOnDeleteAllIdRequest() throws Exception {

    //     ToolEntity toolMixer = new ToolEntity("Hand mixer");
    //     toolMixer.setId(13L);

    //     when(toolService.deleteAllTools()).thenReturn(true);

    //     RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(Routes.TOOLS)
    //             .accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON);

    //     MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

    //     int actualStatus = mvcResult.getResponse().getStatus();
    //     int expectedStatus = HttpStatus.OK.value();

    //     String actualResult = mvcResult.getResponse().getContentAsString();
    //     String expectedResult = objectMapper.writeValueAsString(true);

    //     assertEquals(expectedStatus, actualStatus, "Result not as expected!");
    //     assertEquals(expectedResult, actualResult, "Result not as expected!");
    //     verify(toolService, times(1)).deleteAllTools();

    // }

}
