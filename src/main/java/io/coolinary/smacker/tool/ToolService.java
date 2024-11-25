package io.coolinary.smacker.tool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToolService {
    List<ToolEntity> getAll();

    Optional<ToolEntity> getByPublicId(UUID publicId);

    ToolEntity createTool(ToolCreateAPI createAPI);

    ToolEntity updateTool(UUID publicId, ToolAPI toolAPI);

    Boolean deleteTool(UUID publicId);

    Boolean deleteAllTools();

}
