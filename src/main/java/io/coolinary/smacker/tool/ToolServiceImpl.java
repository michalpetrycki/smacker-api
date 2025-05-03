package io.coolinary.smacker.tool;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class ToolServiceImpl implements ToolService {

	@Autowired
	ToolRepository toolRepository;

	public Optional<ToolEntity> getByPublicId(UUID publicId) {
		return this.toolRepository.findByPublicId(publicId);
	}

	public List<ToolEntity> getAll() {
		return this.toolRepository.findAll().stream().sorted(Comparator.comparingLong(ToolEntity::getId))
				.collect(Collectors.toList());
	}

	public Page<ToolEntity> getPaginated(Integer pageNo, Integer pageSize, String sortBy, String sortDirection,
			String filter) {
		int pIdx = pageNo != null ? pageNo : 0;
		int pSize = pageSize != null ? pageSize : 10;
		Sort sort = (sortBy != null)
				? Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
				: Sort.unsorted();
		PageRequest pageable = PageRequest.of(pIdx, pSize, sort);

		if (filter != null && !filter.isBlank()) {
			System.out.println(
					"pageNo: " + pageNo + ", pageSize: " + pageSize + ", sortBy: " + sortBy + ", sortDirection: "
							+ sortDirection + ", filter: " + filter);
			return toolRepository.findByToolNameContainingIgnoreCase(filter, pageable);
		} else {
			System.out.println(
					"pageNo: " + pageNo + ", pageSize: " + pageSize + ", sortBy: " + sortBy + ", sortDirection: "
							+ sortDirection);
			return toolRepository.findAll(pageable);
		}

	}

	public ToolEntity createTool(ToolCreateAPI toolCreateAPI) {
		ToolAPI toolAPI = new ToolAPI(UUID.randomUUID(), toolCreateAPI.name());
		ToolEntity toolEntity = toToolEntity(toolAPI);
		return this.toolRepository.save(toolEntity);
	}

	public ToolEntity updateTool(ToolEntity toolEntity, ToolAPI toolAPI) {
		toolEntity.setToolName(toolAPI.name());
		return this.toolRepository.save(toolEntity);
	}

	@Transactional
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
