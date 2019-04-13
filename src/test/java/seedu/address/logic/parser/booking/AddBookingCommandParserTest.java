package seedu.address.logic.parser.booking;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.CustomerIndexedBookingBuilder.DEFAULT_START_TIME;

import org.junit.Test;

import seedu.address.logic.CustomerIndexedBooking;
import seedu.address.logic.commands.booking.AddBookingCommand;
import seedu.address.testutil.CustomerIndexedBookingBuilder;

public class AddBookingCommandParserTest {
    private AddBookingCommandParser parser = new AddBookingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CustomerIndexedBooking expectedBooking = new CustomerIndexedBookingBuilder().build();

        // normal input
        String commandString = " " + PREFIX_START_TIME + DEFAULT_START_TIME + " " + PREFIX_CUSTOMER
                + "1 " + PREFIX_NUMBER_PERSONS + "5";
        assertParseSuccess(parser, commandString, new AddBookingCommand(expectedBooking));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookingCommand.MESSAGE_USAGE);

        // missing start time
        String commandString = " " + PREFIX_CUSTOMER + "1 " + PREFIX_NUMBER_PERSONS + "5";
        assertParseFailure(parser, commandString, expectedMessage);

        // missing member index
        commandString = " " + PREFIX_START_TIME + DEFAULT_START_TIME + " " + "1 "
                + PREFIX_NUMBER_PERSONS + "5";
        assertParseFailure(parser, commandString, expectedMessage);

        // missing number of persons
        commandString = " " + PREFIX_START_TIME + DEFAULT_START_TIME + " " + PREFIX_CUSTOMER + "1 ";
        assertParseFailure(parser, commandString, expectedMessage);
    }
}
