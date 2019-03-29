package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import javafx.util.Pair;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 *  A class to represent the set of ingredients used in a recipe.
 *  The Ingredient is stored along with the respective ingredientQuantity required in the recipe,
 *  as a Pair containing Ingredient and IngredientQuantity.
 *  TODO: toString for RecipeIngredientSet
 */

public class RecipeIngredientSet {

    public static final String MESSAGE_CONSTRAINTS =
            "Each ingredient and its respective quantity in a recipe should be in the form:"
            + "INGREDIENT_INDEX&INGREDIENT_QUANTITY\n"
            + "Ingredient index should be a non-zero unsigned integer,"
            + "representing the corresponding ingredient in the ingredient list\n"
            + IngredientQuantity.MESSAGE_CONSTRAINTS + "\n"
            + "INGREDIENT_INDEX and INGREDIENT_QUANTITY should be separated by the symbol '&'";

    private Set<Pair<Ingredient, IngredientQuantity>> ingredientSet;


    /**
     * Constructs a {@code RecipeIngredientSet}.
     *
     * @param set A valid set of pairs containing ingredient and ingredientQuantity
     *
     */
    public RecipeIngredientSet(Set<Pair<Ingredient, IngredientQuantity>> set) {
        requireNonNull(set);
        this.ingredientSet = set;
    }

    public Set<Pair<Ingredient, IngredientQuantity>> getIngredientSet() {
        return ingredientSet;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeIngredientSet// instanceof handles nulls
                && ingredientSet.equals(((RecipeIngredientSet) other).ingredientSet)); // state check
    }

}
