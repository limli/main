package seedu.address.logic.parser.member;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOYALTY_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.member.ListMembersCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.member.NameAndLoyaltyPointsPredicate;

/**
 * Parses input arguments and creates a new ListMembersCommand object
 */
public class ListMembersCommandParser implements Parser<ListMembersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListMembersCommand
     * and returns an ListMembersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListMembersCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOYALTY_POINTS);

        if (!argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMembersCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()
                && !argMultimap.getValue(PREFIX_NAME).get().trim().isEmpty()) {
            nameKeywords = Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+"));
        } else {
            nameKeywords = new ArrayList<>(); // if no names given, set to empty list
        }

        int minLoyaltyPoints = 0;
        if (argMultimap.getValue(PREFIX_LOYALTY_POINTS).isPresent()) {
            minLoyaltyPoints = ParserUtil.parseLoyaltyPoints(argMultimap.getValue(PREFIX_LOYALTY_POINTS).get()).value;
        }

        return new ListMembersCommand(new NameAndLoyaltyPointsPredicate(nameKeywords, minLoyaltyPoints));
    }

}
