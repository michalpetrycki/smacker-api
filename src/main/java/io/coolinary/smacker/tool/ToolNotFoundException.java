package io.coolinary.smacker.tool;

public class ToolNotFoundException extends RuntimeException {
    ToolNotFoundException(Long id) {
        super("Could not find tool: " + id);
    }
}
