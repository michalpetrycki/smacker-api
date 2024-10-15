package io.coolinary.smacker.tool;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ToolService {

    @Autowired
    ToolRepository toolRepository;

    List<ToolEntity> getAll() {
        return this.toolRepository.findAll();
    }

    // ToolEntity getById(Long id) {
    // return this.toolRepository.findById(id).orElseThrow(() -> new
    // ToolNotFoundException(id));
    // }

    ToolEntity createTool(ToolCreateAPI toolCreateAPI) {
        ToolAPI toolAPI = new ToolAPI(UUID.randomUUID(), toolCreateAPI.name());
        ToolEntity toolEntity = toToolEntity(toolAPI);
        toolEntity.setPublicId(UUID.randomUUID());
        return this.toolRepository.save(toolEntity);
    }

    ToolEntity updateTool(Long id, ToolEntity toolEntity) {
        return this.toolRepository.save(toolEntity);
    }

    Boolean deleteTool(Long id) {
        try {
            this.toolRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }

    }

    Boolean deleteAllTools() {
        this.toolRepository.deleteAll();
        return true;
    }

    public static ToolEntity toToolEntity(ToolAPI toolAPI) {
        return ToolEntity.builder().toolName(toolAPI.name()).publicId(toolAPI.publicId()).build();
    }

    public static ToolAPI toToolAPI(ToolEntity toolEntity) {
        return new ToolAPI(
                toolEntity.getPublicId(),
                toolEntity.getToolName());
    }

}
