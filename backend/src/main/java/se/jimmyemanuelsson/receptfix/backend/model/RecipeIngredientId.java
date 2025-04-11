package se.jimmyemanuelsson.receptfix.backend.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecipeIngredientId implements Serializable {

    private UUID recipeId;
    private UUID ingredientId;
}
