package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_INGREDIENT_AND_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_NAME;

import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.IngredientIndexedRecipe;
import seedu.address.logic.commands.add.AddRecipeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.recipe.RecipeName;

/**
 * Parses input arguments and creates a new AddRecipeCommand object
 */
public class AddRecipeCommandParser implements Parser<AddRecipeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddRecipeCommand
     * and returns an AddRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public AddRecipeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECIPE_NAME,
                        PREFIX_RECIPE_INGREDIENT_AND_QUANTITY);

        if (!argMultimap.arePrefixesPresent(PREFIX_RECIPE_NAME, PREFIX_RECIPE_INGREDIENT_AND_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRecipeCommand.MESSAGE_USAGE));
        }

        RecipeName recipeName =
                ParserUtil.parseRecipeName(argMultimap.getValue(PREFIX_RECIPE_NAME).get());

        Map<Index, IngredientQuantity> ingredientIndexedSet =
                ParserUtil.parseRecipeIngredientSet(argMultimap.getAllValues(PREFIX_RECIPE_INGREDIENT_AND_QUANTITY));

        return new AddRecipeCommand(new IngredientIndexedRecipe(recipeName, ingredientIndexedSet));
    }
}
