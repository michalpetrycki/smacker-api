package io.coolinary.smacker.tool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

public interface ToolService {
    List<ToolEntity> getAll();

    Page<ToolEntity> getPaginated(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String filter);

    Optional<ToolEntity> getByPublicId(UUID publicId);

    ToolEntity createTool(ToolCreateAPI createAPI);

    ToolEntity updateTool(ToolEntity toolEntity, ToolAPI toolAPI) throws DataIntegrityViolationException;

    Boolean deleteTool(UUID publicId);

    Boolean deleteAllTools();

}
