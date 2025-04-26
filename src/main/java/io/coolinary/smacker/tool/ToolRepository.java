package io.coolinary.smacker.tool;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<ToolEntity, Long> {

    Optional<ToolEntity> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID publicId);

    Page<ToolEntity> findByToolNameContainingIgnoreCase(String name, Pageable pageable);

}
