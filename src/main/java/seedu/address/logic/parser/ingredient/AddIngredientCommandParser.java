package seedu.address.logic.parser.ingredient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_WARNINGAMOUNT;

import java.util.Optional;

import seedu.address.logic.commands.ingredient.AddIngredientCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.IngredientWarningAmount;

/**
 * Parses input arguments and creates a new AddIngredient Command object.
 */
public class AddIngredientCommandParser implements Parser<AddIngredientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddIngredientCmmand
     * and returns an AddIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public AddIngredientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME,
                        PREFIX_INGREDIENT_QUANTITY, PREFIX_INGREDIENT_UNIT, PREFIX_INGREDIENT_WARNINGAMOUNT);

        if (!argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_UNIT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddIngredientCommand.MESSAGE_USAGE));
        }

        IngredientName ingredientName =
                ParserUtil.parseIngredientName(argMultimap.getValue(PREFIX_INGREDIENT_NAME).get());
        IngredientUnit ingredientUnit =
                ParserUtil.parseIngredientUnit(argMultimap.getValue(PREFIX_INGREDIENT_UNIT).get());

        // optional fields parsed
        Optional<String> quantityField = argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY);
        IngredientQuantity ingredientQuantity;
        if (quantityField.isPresent()) {
            ingredientQuantity =
                    ParserUtil.parseIngredientQuantity(argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY).get());
        } else {
            ingredientQuantity = new IngredientQuantity(0);
        }

        Optional<String> warningAmountField = argMultimap.getValue(PREFIX_INGREDIENT_WARNINGAMOUNT);
        IngredientWarningAmount ingredientWarningAmount;
        if (warningAmountField.isPresent()) {
            ingredientWarningAmount =
                    ParserUtil.parseIngredientWarningAmount
                            (argMultimap.getValue(PREFIX_INGREDIENT_WARNINGAMOUNT).get());
        } else {
            ingredientWarningAmount = new IngredientWarningAmount(0);
        }

        Ingredient ingredient =
                new Ingredient(ingredientName, ingredientQuantity, ingredientUnit, ingredientWarningAmount);

        return new AddIngredientCommand(ingredient);
    }
}
