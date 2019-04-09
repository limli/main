package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * A class to represent the ingredientUnit in an ingredient
 */

public class IngredientUnit {

    public static final String MESSAGE_CONSTRAINTS =
            "Ingredient's unit should only contain alphabets and spaces, with at least 2 characters of the alphabet.";

    public static final String VALIDATION_REGEX_INGREDIENTUNIT = "[a-zA-Z][a-zA-Z ]+";



    // Identity fields
    private final String ingredientUnit;

    /**
     * Constructs a {@code IngredientUnit}.
     * @param unit A valid ingredient unit corresponding to VALIDATION_REGEX
     */
    public IngredientUnit(String unit) {
        requireNonNull(unit);
        checkArgument(isValidIngredientUnit(unit), MESSAGE_CONSTRAINTS);
        this.ingredientUnit = unit;

    }


    public String getUnit() {
        return ingredientUnit;
    }


    /**
     * Returns true if ingredientUnit is valid.
     * @param test
     * @return
     */

    public static boolean isValidIngredientUnit(String test) {
        return test.matches(VALIDATION_REGEX_INGREDIENTUNIT);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientUnit// instanceof handles nulls
                && ingredientUnit.equals(((IngredientUnit) other).getUnit())); // state check
    }

    @Override
    public int hashCode() {
        return ingredientUnit.hashCode();
    }

    @Override
    public String toString() {
        return ingredientUnit;
    }
}

