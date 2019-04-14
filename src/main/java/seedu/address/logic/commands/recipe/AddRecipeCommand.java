package seedu.address.logic.commands.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_INGREDIENT_AND_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_NAME;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.IngredientIndexedRecipe;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;

/**
 * A command that adds a recipe to the restaurant book.
 */
public class AddRecipeCommand extends Command {
    public static final String COMMAND_WORD = "addrecipe";
    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds recipe to the book.\n"
            + "Parameters: "
            + PREFIX_RECIPE_NAME + "RECIPE_NAME "
            + PREFIX_RECIPE_INGREDIENT_AND_QUANTITY + "INGREDIENT_INDEX&RESPECTIVE_QUANTITY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_RECIPE_NAME + "cheese sandwich "
            + PREFIX_RECIPE_INGREDIENT_AND_QUANTITY + "1&4";

    public static final String MESSAGE_SUCCESS = "New recipe added: %1$s";
    public static final String MESSAGE_DUPLICATE = "This recipe already exists in the book";

    private IngredientIndexedRecipe ingredientIndexedRecipe;

    public AddRecipeCommand(IngredientIndexedRecipe recipe) {
        requireNonNull(recipe);
        this.ingredientIndexedRecipe = recipe;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Recipe toAdd;
        Optional<Recipe> toAddOptional = ingredientIndexedRecipe.getRecipe(model);
        if (!toAddOptional.isPresent()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX_IN_RECIPE);
        } else {
            toAdd = toAddOptional.get();
            if (model.hasRecipe(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE);
            }
        }
        model.addRecipe(toAdd);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecipeCommand // instanceof handles nulls
                && ingredientIndexedRecipe.equals(((AddRecipeCommand) other).ingredientIndexedRecipe));
    }
}
