package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.booking.AddBookingCommand;

/**
 * A utility class for Booking.
 */
public class BookingUtil {

    /**
     * Returns an add command string for adding the {@code member}.
     */
    public static String getAddBookingCommand(String timeString, Index customerIndex, int numPersons) {
        return " " + PREFIX_START_TIME + timeString + " " + PREFIX_CUSTOMER
                + customerIndex.getOneBased() + " " + PREFIX_NUMBER_PERSONS + numPersons;
    }

    /**
     * Returns an add command string for adding the {@code member}, using the command alias.
     */
    public static String getAddBookingCommandAlias(String timeString, Index customerIndex, int numPersons) {
        return AddBookingCommand.COMMAND_ALIAS + " " + PREFIX_START_TIME + "2019-02-23 14:30" + PREFIX_CUSTOMER
                + customerIndex.getOneBased() + " " + PREFIX_NUMBER_PERSONS + numPersons;
    }


}
