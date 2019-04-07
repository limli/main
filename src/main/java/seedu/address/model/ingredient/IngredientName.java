package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * A class to represent the ingredientName in an ingredient
 */

public class IngredientName {
    public static final String MESSAGE_CONSTRAINTS =
            "Ingredient's name should only contain alphabets and spaces, with at least 2 alphabets. ";

    public static final String VALIDATION_REGEX_INGREDIENTNAME = "[a-zA-Z][a-zA-Z ]+";

    // Identity fields
    private final String ingredientName;

    /**
     * Constructs a {@code IngredientName}.
     *
     * @param name A valid ingredient name corresponding to VALIDATION_REGEX
     */
    public IngredientName(String name) {
        requireNonNull(name);
        checkArgument(isValidIngredientName(name), MESSAGE_CONSTRAINTS);
        this.ingredientName = name;
    }

    public String getName() {
        return ingredientName;
    }


    public static boolean isValidIngredientName(String test) {
        return test.matches(VALIDATION_REGEX_INGREDIENTNAME);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientName// instanceof handles nulls
                && ingredientName.equals(((IngredientName) other).getName())); // state check
    }

    @Override
    public int hashCode() {
        return ingredientName.hashCode();
    }

    @Override
    public String toString() {
        return ingredientName;
    }
}
