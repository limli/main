package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ViewStatsDaysCommand.MESSAGE_SIZE_CONSTRAINTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewStatsDaysCommand;

public class ViewStatsDaysCommandParserTest {

    private ViewStatsDaysCommandParser parser = new ViewStatsDaysCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStatsDaysCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListMembersCommand() {
        ViewStatsDaysCommand expectedViewStatsDaysCommand = new ViewStatsDaysCommand(30);
        assertParseSuccess(parser, " 30  ", expectedViewStatsDaysCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "   asd  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStatsDaysCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "   0  ", MESSAGE_SIZE_CONSTRAINTS);
    }

}
