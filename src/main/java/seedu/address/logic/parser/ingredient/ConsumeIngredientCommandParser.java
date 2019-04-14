package seedu.address.logic.parser.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ingredient.ConsumeIngredientCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 * Parses input arguments and creates a new CosnsumeIngredientCommand.
 */
public class ConsumeIngredientCommandParser implements Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the ConsumeIngredientCommand
     * and returns an ConsumeIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ConsumeIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_INGREDIENT_QUANTITY);

        if (!argMultimap.arePrefixesPresent(PREFIX_INDEX, PREFIX_INGREDIENT_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ConsumeIngredientCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        IngredientQuantity ingredientQuantityToDeplete =
                ParserUtil.parseIngredientQuantity(argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY).get());
        return new ConsumeIngredientCommand(index, ingredientQuantityToDeplete);
    }
}
