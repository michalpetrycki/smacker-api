package io.coolinary.smacker.recipe;

import java.util.Objects;

import io.coolinary.smacker.tool.ToolEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "RecipeTool")
@Table(name = "tool_to_recipe")
@Getter
@Setter
@NoArgsConstructor
public class RecipeTool {

    public RecipeTool(RecipeEntity recipe, ToolEntity tool) {
        this.recipe = recipe;
        this.tool = tool;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private RecipeEntity recipe;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private ToolEntity tool;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RecipeTool that = (RecipeTool) o;
        return Objects.equals(recipe, that.recipe) &&
                Objects.equals(tool, that.tool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, tool);
    }

}
