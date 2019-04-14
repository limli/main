package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;

/**
 * Deletes an ingredient identified using its displayed index from the restaurant book.
 */

public class DeleteIngredientCommand extends Command {
    public static final String COMMAND_WORD = "deleteingredient";
    public static final String COMMAND_ALIAS = "di";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the ingredient identified by the index number used in the displayed ingredient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_INGREDIENT_SUCCESS = "Deleted Ingredient: %1$s";
    public static final String MESSAGE_DELETED_RECIPES = "recipe(s): %1$s that use this ingredient is also deleted";

    private final Index targetIndex;

    public DeleteIngredientCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }


        Ingredient ingredientToDelete = lastShownList.get(targetIndex.getZeroBased());
        Set<String> recipesAssociated = model.getRecipesAssociated(ingredientToDelete);
        model.deleteIngredient(ingredientToDelete);
        model.commitRestaurantBook();

        if (recipesAssociated.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_DELETE_INGREDIENT_SUCCESS, ingredientToDelete));
        } else {
            String displayMessage = String.format(MESSAGE_DELETE_INGREDIENT_SUCCESS, ingredientToDelete) + "\n"
                    + String.format(MESSAGE_DELETED_RECIPES, recipesAssociated.toString());
            return new CommandResult(displayMessage);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIngredientCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteIngredientCommand) other).targetIndex)); // state check
    }
}

