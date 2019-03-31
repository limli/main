package seedu.address.model.ingredient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.Item;

/**
 * Represents an ingredient in the book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Ingredient implements Item {

    // Identity fields
    private IngredientName ingredientName;
    private IngredientQuantity ingredientQuantity;
    private IngredientUnit ingredientUnit;
    private IngredientWarningAmount ingredientWarningAmount;

    /**
     * IngredientName, and IngredientUnit must be present. Other fields are optional.
     */
    public Ingredient(IngredientName name, IngredientQuantity quantity, IngredientUnit unit,
                      IngredientWarningAmount warningAmount) {
        requireAllNonNull(name, unit);
        this.ingredientName = name;
        this.ingredientQuantity = quantity;
        this.ingredientUnit = unit;
        this.ingredientWarningAmount = warningAmount;
    }

    public IngredientName getIngredientName() {
        return ingredientName;
    }

    public IngredientQuantity getIngredientQuantity() {
        return ingredientQuantity;
    }

    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    public IngredientWarningAmount getIngredientWarningAmount() {
        return ingredientWarningAmount;
    }

    /**
     * Returns true if both ingredients have same name.
     */
    @Override
    public boolean isSameItem(Object other) {
        if (other instanceof Ingredient) {
            return ingredientName.equals(((Ingredient) other).getIngredientName());
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Ingredient)) {
            return false;
        }
        return ingredientName.equals(((Ingredient) other).getIngredientName())
                && ingredientQuantity.equals(((Ingredient) other).getIngredientQuantity())
                && ingredientUnit.equals(((Ingredient) other).getIngredientUnit())
                && ingredientWarningAmount.equals(((Ingredient) other).getIngredientWarningAmount());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ingredientName, ingredientQuantity, ingredientUnit, ingredientWarningAmount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Ingredient: ")
                .append(getIngredientName())
                .append(" Quantity: ")
                .append(getIngredientQuantity())
                .append(" Standard-Unit: ")
                .append(getIngredientUnit())
                .append("Warning amount: ")
                .append(getIngredientWarningAmount());
        return builder.toString();
    }
}


