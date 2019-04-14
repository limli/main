package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_WARNINGAMOUNT;

import seedu.address.logic.commands.add.AddIngredientCommand;
import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class for Ingredient.
 */
public class IngredientUtil {

    /**
     * Returns an add command string for adding the {@code ingredient}.
     */
    public static String getAddCommand(Ingredient ingredient) {
        return AddIngredientCommand.COMMAND_WORD + " " + getIngredientDetails(ingredient);
    }

    /**
     * Returns an add command string for adding the {@code ingredient}, using the command alias.
     */
    public static String getAddCommandAlias(Ingredient ingredient) {
        return AddIngredientCommand.COMMAND_ALIAS + " " + getIngredientDetails(ingredient);
    }

    /**
     * Returns the part of command string for the given {@code ingredient}'s details.
     */
    public static String getIngredientDetails(Ingredient ingredient) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_INGREDIENT_NAME + ingredient.getIngredientName().getName() + " ");
        sb.append(PREFIX_INGREDIENT_QUANTITY + Integer.toString(ingredient
                .getIngredientQuantity().getQuantity()) + " ");
        sb.append(PREFIX_INGREDIENT_UNIT + ingredient.getIngredientUnit().getUnit() + " ");
        sb.append(PREFIX_INGREDIENT_WARNINGAMOUNT + Integer.toString(ingredient
                .getIngredientWarningAmount().getWarningAmount()) + " ");
        return sb.toString();
    }
}
