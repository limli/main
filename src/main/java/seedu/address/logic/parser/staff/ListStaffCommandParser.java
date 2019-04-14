package seedu.address.logic.parser.staff;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.staff.ListStaffCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.staff.StaffPredicate;

/**
 * Parses input arguments and creates a new ListStaffCommand object
 */
public class ListStaffCommandParser implements Parser<ListStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListStaffCommand
     * and returns an ListStaffCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListStaffCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_APPOINTMENT);

        if (!argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListStaffCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()
                && !argMultimap.getValue(PREFIX_NAME).get().trim().isEmpty()) {
            nameKeywords = Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+"));
        } else {
            nameKeywords = new ArrayList<>(); // if no names given, set to empty list
        }

        List<String> appointmentKeywords;
        if (argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()
                && !argMultimap.getValue(PREFIX_APPOINTMENT).get().trim().isEmpty()) {
            appointmentKeywords = Arrays.asList(argMultimap.getValue(PREFIX_APPOINTMENT).get().split("\\s+"));
        } else {
            appointmentKeywords = new ArrayList<>(); // if no names given, set to empty list
        }

        return new ListStaffCommand(new StaffPredicate(nameKeywords, appointmentKeywords));
    }

}
