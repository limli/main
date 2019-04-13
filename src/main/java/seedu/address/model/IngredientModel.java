package seedu.address.model;

import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.exceptions.DuplicateItemException;

/**
 * The API that stores the ingredient side of the model.
 */
public interface IngredientModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Ingredient> PREDICATE_SHOW_ALL_INGREDIENTS = unused -> true;
    /**
     *
     * Returns true if a ingredient with the same identity as {@code ingredient} exists in the restaurant book.
     */
    boolean hasIngredient(Ingredient ingredient);

    /**
     * Deletes the given ingredient.
     * The ingredient must exist in the restaurant book.
     */
    void deleteIngredient(Ingredient target);

    /**
     * Adds the given ingredient.
     * {@code ingredient} must not already exist in the restaurant book.
     */
    void addIngredient(Ingredient ingredient);

    /**
     * Replaces the given ingredient {@code target} with {@code editedIngredient}.
     * {@code target} must exist in the restaurant book.
     * The ingredient identity of {@code editedIngredient}
     * must not be the same as another existing ingredient in the restaurant book.
     * @throws DuplicateItemException if editing will result in a duplicate ingredient. In that case,
     * no ingredients will be modified. Note that we cannot simply use
     * {@code !target.isSameItem(editedIngredient) && hasPerson(editedIngredient)} as that assumes transitivity
     * of the isSameItem operator.
     */
    void setIngredient(Ingredient target, Ingredient editedIngredient) throws DuplicateItemException;

    /** Returns an unmodifiable view of the filtered ingredient list */
    ObservableList<Ingredient> getFilteredIngredientList();

    /**
     * Updates the filter of the filtered ingredient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIngredientList(Predicate<Ingredient> predicate);

    /**
     * Selected ingredient in the filtered ingredient list.
     * null if no ingredient is selected.
     */
    ReadOnlyProperty<Ingredient> selectedIngredientProperty();

    /**
     * Returns the selected ingredient in the filtered ingredient list.
     * null if no ingredient is selected.
     */
    Ingredient getSelectedIngredient();

    /**
     * Sets the selected ingredient in the filtered ingredient list.
     */
    void setSelectedIngredient(Ingredient ingredient);
}
