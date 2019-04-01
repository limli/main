package seedu.address.logic.parser.booking;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.logic.commands.EditBookingCommand;
import seedu.address.logic.commands.EditBookingCommand.EditBookingDescriptor;
import seedu.address.logic.parser.EditBookingCommandParser;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;

public class EditBookingCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBookingCommand.MESSAGE_USAGE);

    private EditBookingCommandParser parser = new EditBookingCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, " n/5", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditBookingCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + " " + PREFIX_NUMBER_PERSONS + "5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + " " + PREFIX_NUMBER_PERSONS + "5", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_numPersonsOnly_success() {
        EditBookingDescriptor ebd = new EditBookingDescriptor();
        ebd.setBookingSize(new BookingSize(5));
        String userInput = "2 " + PREFIX_NUMBER_PERSONS + "5";
        EditBookingCommand expected = new EditBookingCommand(INDEX_SECOND, ebd);
        assertParseSuccess(parser, userInput, expected);

    }

    @Test
    public void parse_startTimeOnly_success() {
        EditBookingDescriptor ebd = new EditBookingDescriptor();
        String startTimeString = "2019-03-24T10:00";
        ebd.setBookingWindow(new BookingWindow(startTimeString));
        String userInput = "1 " + PREFIX_START_TIME + startTimeString;
        EditBookingCommand expected = new EditBookingCommand(INDEX_FIRST, ebd);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_bothFieldsPresent_success() {
        EditBookingDescriptor ebd = new EditBookingDescriptor();
        String startTimeString = "2019-03-24T10:00";
        ebd.setBookingWindow(new BookingWindow(startTimeString));
        ebd.setBookingSize(new BookingSize(5));
        String userInput = "3 " + PREFIX_NUMBER_PERSONS + "5 " + PREFIX_START_TIME + startTimeString;
        EditBookingCommand expected = new EditBookingCommand(INDEX_THIRD, ebd);
        assertParseSuccess(parser, userInput, expected);
    }


}
