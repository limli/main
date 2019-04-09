package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * A class to represent the recipeName in recipe.
 */

public class RecipeName {
    public static final String MESSAGE_CONSTRAINTS =
            "Recipe's name should only contain alphabets and spaces, with at least 2 characters of the alphabet. ";

    public static final String VALIDATION_REGEX_RECIPENAME = "[a-zA-Z][a-zA-Z ]+";

    // Identity fields
    private final String recipeName;

    /**
     * Constructs a {@code RecipeName}.
     *
     * @param name A valid recipe name corresponding to VALIDATION_REGEX
     */
    public RecipeName(String name) {
        requireNonNull(name);
        checkArgument(isValidRecipeName(name), MESSAGE_CONSTRAINTS);
        this.recipeName = name;
    }

    public String getName() {
        return recipeName;
    }


    public static boolean isValidRecipeName(String test) {
        return test.matches(VALIDATION_REGEX_RECIPENAME);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeName// instanceof handles nulls
                && recipeName.equals(((RecipeName) other).getName())); // state check
    }


    @Override
    public String toString() {
        return recipeName;
    }
}
