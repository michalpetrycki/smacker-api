package io.coolinary.smacker.tool;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ElementNotFoundException.EntityType;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    ToolRepository toolRepository;

    public List<ToolEntity> getAll() {
        return this.toolRepository.findAll();
    }

    public Optional<ToolEntity> getByPublicId(UUID publicId) {
        return this.toolRepository.findByPublicId(publicId);
    }

    public ToolEntity createTool(ToolCreateAPI toolCreateAPI) {
        ToolAPI toolAPI = new ToolAPI(UUID.randomUUID(), toolCreateAPI.name());
        ToolEntity toolEntity = toToolEntity(toolAPI);
        toolEntity.setPublicId(UUID.randomUUID());
        return this.toolRepository.save(toolEntity);
    }

    public ToolEntity updateTool(UUID publicId, ToolAPI toolAPI) {
        ToolEntity toolEntity = this.toolRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ElementNotFoundException(publicId, EntityType.TOOL));
        toolEntity.setToolName(toolAPI.name());
        toolEntity.setUpdateTimestamp(Instant.now());
        return this.toolRepository.save(toolEntity);
    }

    public Boolean deleteTool(UUID publicId) {
        try {
            this.toolRepository.deleteByPublicId(publicId);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }

    }

    public Boolean deleteAllTools() {
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
