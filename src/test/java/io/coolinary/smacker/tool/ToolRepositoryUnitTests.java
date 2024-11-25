package io.coolinary.smacker.tool;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToolRepositoryUnitTests {

    @Autowired
    private ToolRepository toolRepository;

    @Test
    @DisplayName("Test 1: Save tool test")
    @Order(1)
    @Rollback(value = false)
    public void saveToolTest() {

        ToolEntity tool = ToolEntity.builder().toolName("Spatula").publicId(UUID.randomUUID())
                .creationTimestamp(Instant.now()).build();

        toolRepository.save(tool);

        System.out.println(tool);
        Assertions.assertThat(tool.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getToolTest() {

        // Action
        ToolEntity tool = toolRepository.findById(1L).get();
        // Verify
        System.out.println(tool);
        Assertions.assertThat(tool.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfToolsTest() {
        // Action
        List<ToolEntity> tools = toolRepository.findAll();
        // Verify
        System.out.println(tools);
        Assertions.assertThat(tools.size()).isGreaterThan(0);
    }

}
