package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_WARNINGAMOUNT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;

/**
 * A command that adds an ingredient to the restaurant book.
 */
public class AddIngredientCommand extends Command {
    public static final String COMMAND_WORD = "addingredient";
    public static final String COMMAND_ALIAS = "ia";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an ingredient to the book. "
            + "Parameters: "
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + PREFIX_INGREDIENT_UNIT + "STANDARD_UNIT "
            + "[" + PREFIX_INGREDIENT_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_INGREDIENT_WARNINGAMOUNT + "WARNINGAMOUNT] + \n "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "cheese "
            + PREFIX_INGREDIENT_UNIT + "pounds "
            + PREFIX_INGREDIENT_QUANTITY + "8 "
            + PREFIX_INGREDIENT_WARNINGAMOUNT + "3";

    public static final String MESSAGE_SUCCESS = "New ingredient added: %1$s";
    public static final String MESSAGE_DUPLICATE = "This ingredient already exists in the book";

    private Ingredient toAdd;
    public AddIngredientCommand(Ingredient ingredient) {
        requireNonNull(ingredient);
        toAdd = ingredient;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasIngredient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        model.addIngredient(toAdd);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIngredientCommand // instanceof handles nulls
                && toAdd.equals(((AddIngredientCommand) other).toAdd));
    }
}
