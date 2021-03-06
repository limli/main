package seedu.address.logic.parser.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.DayOfWeek;
import java.time.LocalTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.staff.DeleteShiftCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.staff.Shift;


/**
 * Parses input arguments and creates a new DeleteShiftCommand object
 */
public class DeleteShiftCommandParser implements Parser<DeleteShiftCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteShiftCommand
     * and returns an DeleteShiftCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteShiftCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DAY_OF_WEEK, PREFIX_END_DAY_OF_WEEK,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        if (!argMultimap.arePrefixesPresent(PREFIX_START_DAY_OF_WEEK, PREFIX_END_DAY_OF_WEEK,
                PREFIX_START_TIME, PREFIX_END_TIME) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteShiftCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteShiftCommand.MESSAGE_USAGE), pe);
        }

        DayOfWeek startDayOfWeek = ParserUtil.parseDayOfWeek(argMultimap.getValue(PREFIX_START_DAY_OF_WEEK).get());
        LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        DayOfWeek endDayOfWeek = ParserUtil.parseDayOfWeek(argMultimap.getValue(PREFIX_END_DAY_OF_WEEK).get());
        LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
        Shift shiftToDelete = ParserUtil.parseShift(startDayOfWeek, startTime, endDayOfWeek, endTime);

        return new DeleteShiftCommand(index, shiftToDelete);
    }

}
