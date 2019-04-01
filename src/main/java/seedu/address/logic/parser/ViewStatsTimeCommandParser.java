package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewStatsTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Statistics;

/**
 * Parses input arguments and creates a new ViewStatsTimeCommand object.
 */
public class ViewStatsTimeCommandParser implements Parser<ViewStatsTimeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewStatsDaysCommand
     * and returns an ViewStatsDaysCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewStatsTimeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        String preamble = argMultimap.getPreamble();
        int days;
        try {
            days = Integer.parseInt(preamble);
        } catch (NumberFormatException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatsTimeCommand.MESSAGE_USAGE));
        }

        if (days > Statistics.getMaxDays() || days <= 0) {
            throw new ParseException(ViewStatsTimeCommand.MESSAGE_SIZE_CONSTRAINTS);
        }
        return new ViewStatsTimeCommand(days);
    }
}
