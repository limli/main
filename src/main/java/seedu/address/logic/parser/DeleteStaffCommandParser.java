package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteStaffCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteStaffCommand object
 */
public class DeleteStaffCommandParser implements Parser<DeleteStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStaffCommand
     * and returns an DeleteStaffCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteStaffCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteStaffCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStaffCommand.MESSAGE_USAGE), pe);
        }
    }

}
