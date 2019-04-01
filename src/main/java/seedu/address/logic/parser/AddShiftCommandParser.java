package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.DayOfWeek;
import java.time.LocalTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.add.AddShiftCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.staff.Shift;

/**
 * Parses input arguments and creates a new AddShiftCommand object.
 */
public class AddShiftCommandParser implements Parser<AddShiftCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddShiftCommand
     * and returns an AddShiftCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddShiftCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DAY_OF_WEEK, PREFIX_END_DAY_OF_WEEK,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddShiftCommand.MESSAGE_USAGE), pe);
        }

        DayOfWeek startDayOfWeek = ParserUtil.parseDayOfWeek(argMultimap.getValue(PREFIX_START_DAY_OF_WEEK).get());
        LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        DayOfWeek endDayOfWeek = ParserUtil.parseDayOfWeek(argMultimap.getValue(PREFIX_END_DAY_OF_WEEK).get());
        LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
        Shift shift = ParserUtil.parseShift(startDayOfWeek, startTime, endDayOfWeek, endTime);

        return new AddShiftCommand(index, shift);
    }
}
