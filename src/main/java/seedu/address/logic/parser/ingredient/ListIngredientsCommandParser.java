package seedu.address.logic.parser.ingredient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_WARNINGAMOUNT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.ingredient.ListIngredientsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientNameAndWarningAmountPredicate;

/**
 * Parses input arguments and creates a new ListIngredientsCommand object
 */
public class ListIngredientsCommandParser implements Parser<ListIngredientsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListIngredientsCommand
     * and returns an ListIngredientsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListIngredientsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_WARNINGAMOUNT);

        if (!argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListIngredientsCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords;
        if (argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent()
                && !argMultimap.getValue(PREFIX_INGREDIENT_NAME).get().trim().isEmpty()) {
            nameKeywords = Arrays.asList(argMultimap.getValue(PREFIX_INGREDIENT_NAME).get().split("\\s+"));
        } else {
            nameKeywords = new ArrayList<>(); // if no names given, set to empty list
        }

        boolean filterByWarnAmt = false;
        if (argMultimap.getValue(PREFIX_INGREDIENT_WARNINGAMOUNT).isPresent()) {
            filterByWarnAmt = true;
        }

        return new ListIngredientsCommand(new IngredientNameAndWarningAmountPredicate(nameKeywords, filterByWarnAmt));
    }

}
