package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
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
    private Set<Pair<Index, IngredientQuantity>> ingredientIndexedSet;

    public IngredientIndexedRecipe(RecipeName name, Set<Pair<Index, IngredientQuantity>> set) {
        this.recipeName = name;
        this.ingredientIndexedSet = set;
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
        Set<Pair<Ingredient, IngredientQuantity>> ingredsInRecipe = new HashSet<>();
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        Iterator<Pair<Index, IngredientQuantity>> it = ingredientIndexedSet.iterator();

        while (((Iterator) it).hasNext()) {
            Pair<Index, IngredientQuantity> indexedIngred = it.next();
            Index ingredientIndex = indexedIngred.getKey();
            if (ingredientIndex.getZeroBased() >= lastShownList.size()) {
                return Optional.empty();
            } else {
                Ingredient ingredient = lastShownList.get(ingredientIndex.getZeroBased());
                IngredientQuantity quantityForRecipe = indexedIngred.getValue();
                Pair<Ingredient, IngredientQuantity> processedIngred = new Pair(ingredient, quantityForRecipe);
                ingredsInRecipe.add(processedIngred);
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


