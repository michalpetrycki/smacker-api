package io.coolinary.smacker.tool;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.coolinary.smacker.recipe.RecipeTool;
import io.coolinary.smacker.shared.UpdatableEntity;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "tool")
@AllArgsConstructor
@NoArgsConstructor
public class ToolEntity extends UpdatableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "public_identifier")
    private UUID publicId;
    // @NaturalId
    @Column(name = "tool_name")
    private String toolName;

    @OneToMany(mappedBy = "tool")
    @JsonIgnore
    @Builder.Default
    private Set<RecipeTool> recipeTools = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ToolEntity tool = (ToolEntity) o;
        return Objects.equals(id, tool.id);
    }

    @Override
    public String toString() {
        return "Tool [id=" + id + ", toolName=" + toolName + "]";
    }

}
