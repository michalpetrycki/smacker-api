package io.coolinary.smacker.tool;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static io.coolinary.smacker.tool.ToolServiceImpl.toToolAPI;

import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.Routes;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;

@RestController
@RequestMapping("/api")
public class ToolController {

    private final ToolServiceImpl toolService;
    private final Logger logger = LoggerFactory.getLogger(ToolController.class);

    ToolController(ToolServiceImpl toolService) {
        this.toolService = toolService;
    }

    @GetMapping(Routes.TOOLS)
    ResponseEntity<List<ToolAPI>> getAll() {
        List<ToolEntity> tools = this.toolService.getAll();
        return new ResponseEntity<List<ToolAPI>>(
                tools.stream().map(tool -> toToolAPI(tool)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Routes.TOOLS + Routes.ID)
    public ResponseEntity<ToolAPI> getTool(@PathVariable("publicId") UUID publicId) {
        ToolEntity toolEntity = this.toolService.getByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.TOOL));
        return new ResponseEntity<ToolAPI>(toToolAPI(toolEntity), HttpStatus.OK);
    }

    @PostMapping(Routes.TOOLS)
    public ResponseEntity<ToolAPI> createTool(@RequestBody ToolCreateAPI newTool) {

        try {
            return new ResponseEntity<ToolAPI>(toToolAPI(this.toolService.createTool(newTool)), HttpStatus.CREATED);
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // @PutMapping(Routes.TOOLS + Routes.ID)
    // public ResponseEntity<ToolEntity> replaceTool(@RequestBody ToolEntity
    // newTool, @PathVariable("id") Long id) {

    // ToolEntity toolToUpdate = this.toolService.getById(id);
    // if (toolToUpdate == null) {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }

    // toolToUpdate.setToolName(newTool.getToolName());
    // return new ResponseEntity<ToolEntity>(this.toolService.updateTool(id,
    // toolToUpdate), HttpStatus.OK);
    // }

    @DeleteMapping(Routes.TOOLS + Routes.ID)
    public ResponseEntity<Boolean> deleteTool(@PathVariable("publicIc") UUID publicId) {
        return new ResponseEntity<Boolean>(this.toolService.deleteTool(publicId), HttpStatus.OK);
    }

    @DeleteMapping(Routes.TOOLS)
    public ResponseEntity<Boolean> deleteAllTools() {
        return new ResponseEntity<Boolean>(this.toolService.deleteAllTools(), HttpStatus.OK);
    }

}
