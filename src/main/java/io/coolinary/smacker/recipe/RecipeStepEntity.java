package io.coolinary.smacker.recipe;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "recipe_steps")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeStepEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // @Column(name = "recipe_id")
    // private Long recipeId;
    @Column(name = "step_order")
    private int stepOrder;
    @Column(name = "step_text")
    private String stepText;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipeEntity;

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
        RecipeStepEntity step = (RecipeStepEntity) o;
        return Objects.equals(id, step.id);
    }

    @Override
    public String toString() {
        return "Step [id=" + id + ", stepOrder=" + stepOrder + ", stepText=" + stepText
                + "]";
    }

}
