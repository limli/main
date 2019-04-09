package seedu.address.model;

import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.recipe.Recipe;

/**
 * The API that stores the recipe side of the model.
 */
public interface RecipeModel {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Recipe> PREDICATE_SHOW_ALL_RECIPES = unused -> true;

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the restaurant book.
     */
    boolean hasRecipe(Recipe recipe);

    /**
     * Deletes the given recipe.
     * The recipe must exist in the restaurant book.
     */
    void deleteRecipe(Recipe target);

    /**
     * Adds the given recipe.
     * {@code recipe} must not already exist in the restaurant book.
     */
    void addRecipe(Recipe recipe);

    /**
     * Replaces the given recipe {@code target} with {@code editedRecipe}.
     * {@code target} must exist in the restaurant book.
     * The recipe identity of {@code editedRecipe}
     * must not be the same as another existing recipe in the restaurant book.
     */
    void setRecipe(Recipe target, Recipe editedItem);

    /**
     * Returns an unmodifiable view of the filtered recipe list
     */
    ObservableList<Recipe> getFilteredRecipeList();

    /**
     * Updates the filter of the filtered recipe list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecipeList(Predicate<Recipe> predicate);

    /**
     * Selected recipe in the filtered recipe list.
     * null if no recipe is selected.
     */
    ReadOnlyProperty<Recipe> selectedRecipeProperty();

    /**
     * Returns the selected recipe in the filtered recipe list.
     * null if no recipe is selected.
     */
    Recipe getSelectedRecipe();

    /**
     * Sets the selected recipe in the filtered recipe list.
     */
    void setSelectedRecipe(Recipe recipe);

    /**
     * Gets the set of recipe names associated to the ingredient
     */
    Set<String> getRecipesAssociated(Ingredient ingredient);
}
