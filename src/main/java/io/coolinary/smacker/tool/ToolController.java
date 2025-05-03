package io.coolinary.smacker.tool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
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
import io.coolinary.smacker.shared.PaginatedResponse;
import io.coolinary.smacker.shared.Routes;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ToolController {

    private final ToolServiceImpl toolService;

    ToolController(ToolServiceImpl toolService) {
        this.toolService = toolService;
    }

    @GetMapping(Routes.TOOLS_ALL)
    ResponseEntity<List<ToolAPI>> getAll() {

        return new ResponseEntity<List<ToolAPI>>(
                this.toolService.getAll().stream().map(tool -> toToolAPI(tool)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(Routes.TOOLS)
    ResponseEntity<PaginatedResponse<ToolAPI>> getPaginated(@RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String filter) {

        Page<ToolEntity> page = this.toolService.getPaginated(pageNo, pageSize, sortBy, sortDirection, filter);
        List<ToolAPI> tools = page.getContent().stream()
                .map(tool -> toToolAPI(tool))
                .collect(Collectors.toList());

        PaginatedResponse<ToolAPI> resp = new PaginatedResponse<ToolAPI>(tools, page.getTotalElements());
        return new ResponseEntity<PaginatedResponse<ToolAPI>>(resp, HttpStatus.OK);
    }

    @GetMapping(Routes.TOOLS + Routes.PID)
    public ResponseEntity<ToolAPI> getTool(@PathVariable("publicId") UUID publicId) {
        ToolEntity toolEntity = this.toolService.getByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.TOOL));
        return new ResponseEntity<ToolAPI>(toToolAPI(toolEntity), HttpStatus.OK);
    }

    @PostMapping(Routes.TOOLS)
    public ResponseEntity<ToolAPI> createTool(@RequestBody ToolCreateAPI newTool) {
        return new ResponseEntity<ToolAPI>(toToolAPI(this.toolService.createTool(newTool)), HttpStatus.CREATED);
    }

    @PutMapping(Routes.TOOLS + Routes.PID)
    public ResponseEntity<ToolAPI> replaceTool(@RequestBody ToolAPI toolAPI,
            @PathVariable("publicId") UUID publicId) {

        Optional<ToolEntity> toolToUpdate = this.toolService.getByPublicId(publicId);
        if (!toolToUpdate.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ToolAPI>(toToolAPI(this.toolService.updateTool(
                toolToUpdate.get(), toolAPI)), HttpStatus.OK);
    }

    @DeleteMapping(Routes.TOOLS + Routes.PID)
    public ResponseEntity<Boolean> deleteTool(@PathVariable("publicId") UUID publicId) {
        return new ResponseEntity<Boolean>(this.toolService.deleteTool(publicId), HttpStatus.OK);
    }

    @DeleteMapping(Routes.TOOLS)
    public ResponseEntity<Boolean> deleteAllTools() {
        return new ResponseEntity<Boolean>(this.toolService.deleteAllTools(), HttpStatus.OK);
    }

}
