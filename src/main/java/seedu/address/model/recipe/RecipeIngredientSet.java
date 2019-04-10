package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.Map;

import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 *  A class to represent the ingredients used in a recipe.
 *  The Ingredient is stored along with the respective ingredientQuantity required in the recipe,
 *  as a Map containing Ingredient as key and IngredientQuantity as value.
 *
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
    private int numberOfServings;


    /**
     * Constructs a {@code RecipeIngredientSet}.
     * Calls the method to calculate the number of servings possible with the
     * current ingredient inventory at instantiation.
     * @param map A valid map containing ingredient as key and ingredientQuantity as value.
     *
     */
    public RecipeIngredientSet(Map<Ingredient, IngredientQuantity> map) {
        requireNonNull(map);
        this.ingredientMap = map;
        this.numberOfServings = calculateNumberOfServings(map);
    }

    /**
     * Calculates the number of servings possible with current ingredient inventory.
     * @param ingredQuantitymap A map containing ingredient as key and ingredientQuantity as value.
     * @return minimumNumberOfServings The number of servings possible for the recipe.
     */
    public int calculateNumberOfServings(Map<Ingredient, IngredientQuantity> ingredQuantitymap) {
        int minimumNumberOfServings = Integer.MAX_VALUE;
        Iterator<Ingredient> it = ingredQuantitymap.keySet().iterator();
        while (it.hasNext()) {
            Ingredient ingred = it.next();
            IngredientQuantity ingredQuantityInInventory = ingred.getIngredientQuantity();
            IngredientQuantity ingredQuantityForRecipe = (IngredientQuantity) ingredQuantitymap.get(ingred);
            assert ingredQuantityForRecipe.getQuantity() != 0
                : "Ingredient Quantity for each serving in recipe should not be 0";
            int numberOfServings = ingredQuantityInInventory.getQuantity() / ingredQuantityForRecipe.getQuantity();
            if (numberOfServings < minimumNumberOfServings) {
                minimumNumberOfServings = numberOfServings;
            }
        }
        return minimumNumberOfServings;
    }

    public Map<Ingredient, IngredientQuantity> getIngredientMap() {
        return ingredientMap;
    }

    public int getNumberOfServings() {
        return numberOfServings;
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
