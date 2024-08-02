package io.coolinary.smacker.tool;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tool")
public class ToolEntity {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name = "public_identifier")
    private UUID publicId;
    @Column(name = "tool_name")
    private String toolName;

    public ToolEntity(String toolName) {
        this.publicId = UUID.randomUUID();
        this.toolName = toolName;
    }

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
