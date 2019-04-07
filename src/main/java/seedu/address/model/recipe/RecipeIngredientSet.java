package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 *  A class to represent the ingredients used in a recipe.
 *  The Ingredient is stored along with the respective ingredientQuantity required in the recipe,
 *  as a Map containing Ingredient as key and IngredientQuantity as value.
 *  TODO: toString for RecipeIngredientSet
 */

public class RecipeIngredientSet {

    public static final String MESSAGE_CONSTRAINTS =
            "Each ingredient and its respective quantity in a recipe should be in the form: "
            + "INGREDIENT_INDEX&INGREDIENT_QUANTITY\n"
            + "Ingredient index should be a non-zero unsigned integer, "
            + "representing the corresponding ingredient in the ingredient list.\n"
            + "Ingredient quantity should be a non-zero unsigned integer. \n"
            + "INGREDIENT_INDEX and INGREDIENT_QUANTITY should be separated by the symbol '&'.";

    private Map<Ingredient, IngredientQuantity> ingredientMap;


    /**
     * Constructs a {@code RecipeIngredientSet}.
     *
     * @param map A valid map containing ingredient as key and ingredientQuantity as value.
     *
     */
    public RecipeIngredientSet(Map<Ingredient, IngredientQuantity> map) {
        requireNonNull(map);
        this.ingredientMap = map;
    }

    public Map<Ingredient, IngredientQuantity> getIngredientMap() {
        return ingredientMap;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeIngredientSet// instanceof handles nulls
                && ingredientMap.equals(((RecipeIngredientSet) other).ingredientMap)); // state check
    }

    @Override
    public String toString() {
        String ingredientsList = "";
        for (Ingredient ingredientKey : ingredientMap.keySet()) {
            ingredientsList += ingredientKey.getIngredientName() + ": " + ingredientMap.get(ingredientKey)
                    + " " + ingredientKey.getIngredientUnit() + "\n";
        }
        return ingredientsList;
    }

}
