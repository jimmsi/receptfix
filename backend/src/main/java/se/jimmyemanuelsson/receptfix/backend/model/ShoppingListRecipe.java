package se.jimmyemanuelsson.receptfix.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopping_list_recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingListRecipe {

    @EmbeddedId
    private ShoppingListRecipeId id;

    @ManyToOne
    @MapsId("shoppingListId")
    @JoinColumn(name = "shopping_list_id", nullable = false)
    private ShoppingList shoppingList;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
}
