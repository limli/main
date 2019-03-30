package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeIngredientSet;
import seedu.address.model.recipe.RecipeName;

/**
 * Represents a recipe with the a set of ingredients given by index rather than actual ingredients.
 */
public class IngredientIndexedRecipe {
    private RecipeName recipeName;
    private Map<Index, IngredientQuantity> ingredientIndexedSet;

    public IngredientIndexedRecipe(RecipeName name, Map<Index, IngredientQuantity> map) {
        this.recipeName = name;
        this.ingredientIndexedSet = map;
    }

    /**
     * Retrieves ingredient details for each index in the set and generates a {@code Optional<Recipe>}.
     *
     * @param model The model used to generate the recipe
     * @return If any of the indexes of ingredients are out of bounds, return Optional.empty().
     * Otherwise, it retrieves all Ingredients and returns a Recipe
     */
    public Optional<Recipe> getRecipe(Model model) {
        requireNonNull(model);
        Map<Ingredient, IngredientQuantity> ingredsInRecipe = new HashMap<>();
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        Iterator<Index> it = ingredientIndexedSet.keySet().iterator();

        while (((Iterator) it).hasNext()) {
            Index ingredientIndex = (Index) it.next();
            if (ingredientIndex.getZeroBased() >= lastShownList.size()) {
                return Optional.empty();
            } else {
                Ingredient ingredient = lastShownList.get(ingredientIndex.getZeroBased());
                IngredientQuantity quantityForRecipe = ingredientIndexedSet.get(ingredientIndex);
                ingredsInRecipe.put(ingredient, quantityForRecipe);
            }
        }

        RecipeIngredientSet recipeIngredientSet = new RecipeIngredientSet(ingredsInRecipe);
        return Optional.of(new Recipe(recipeName, recipeIngredientSet));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientIndexedRecipe // instanceof handles nulls
                && recipeName.equals(((IngredientIndexedRecipe) other).recipeName) // state check
                && ingredientIndexedSet.equals(((IngredientIndexedRecipe) other).ingredientIndexedSet));
    }
}


