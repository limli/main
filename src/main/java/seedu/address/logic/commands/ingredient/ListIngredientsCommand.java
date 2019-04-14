package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_WARNINGAMOUNT;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ingredient.IngredientNameAndWarningAmountPredicate;

/**
 * Finds and lists all ingredients in restaurant book whose name contains any of the argument keywords
 * or ingredientQuantity is less than ingredientWarningAmount
 * Keyword matching is case insensitive.
 */
public class ListIngredientsCommand extends Command {

    public static final String COMMAND_WORD = "listingredients";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all ingredients whose names contain any of "
            + "the specified keywords (case-insensitive) and has quantity less than warning amount.\n"
            + "The ingredients found are then displayed as a list with index numbers.\n"
            + "If fields are not specified, then all ingredients will be listed.\n"
            + "Parameters: ["
            + PREFIX_INGREDIENT_NAME
            + "INGREDINENT_NAME] [" + PREFIX_INGREDIENT_WARNINGAMOUNT + "]\n"
            + "Example: " + COMMAND_WORD
            + " " + PREFIX_INGREDIENT_NAME + "tomato"
            + " " + PREFIX_INGREDIENT_WARNINGAMOUNT + " ";

    private final IngredientNameAndWarningAmountPredicate predicate;

    public ListIngredientsCommand(IngredientNameAndWarningAmountPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredIngredientList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_INGREDIENTS_LISTED_OVERVIEW,
                        model.getFilteredIngredientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListIngredientsCommand // instanceof handles nulls
                && predicate.equals(((ListIngredientsCommand) other).predicate)); // state check
    }
}

