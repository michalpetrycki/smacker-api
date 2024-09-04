package io.coolinary.smacker.tool;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import io.coolinary.smacker.shared.UpdatableEntity;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "tool")
@AllArgsConstructor
@NoArgsConstructor
public class ToolEntity extends UpdatableEntity {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name = "public_identifier")
    private UUID publicId;
    @Column(name = "tool_name")
    private String toolName;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((toolName == null) ? 0 : toolName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ToolEntity other = (ToolEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (toolName == null) {
            if (other.toolName != null)
                return false;
        } else if (!toolName.equals(other.toolName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Tool [id=" + id + ", toolName=" + toolName + "]";
    }

}
