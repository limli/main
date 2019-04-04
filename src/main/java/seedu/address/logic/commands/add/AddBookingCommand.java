package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CustomerIndexedBooking;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * A command that adds a booking to the restaurant book.
 * This command is used when only the customer's index but not the full details are known.
 */
public class AddBookingCommand extends Command {

    public static final String COMMAND_WORD = "addbooking"; // make sure that this is in lower case
    public static final String COMMAND_ALIAS = "ab";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a booking to the restaurant. \n"
            + "Parameters: "
            + PREFIX_CUSTOMER + "CUSTOMER "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_NUMBER_PERSONS + "NUMBER_OF_PERSONS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_START_TIME + "2019-02-23T14:30 "
            + PREFIX_NUMBER_PERSONS + "3";

    public static final String MESSAGE_SUCCESS = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE = "Booking has already been made.";
    public static final String MESSAGE_FULL = "Restaurant is full. Please find another time slot.";

    private final CustomerIndexedBooking customerIndexedBooking;

    public AddBookingCommand(CustomerIndexedBooking customerIndexedBooking) {
        requireNonNull(customerIndexedBooking);
        this.customerIndexedBooking = customerIndexedBooking;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        Optional<Booking> toAddOptional = customerIndexedBooking.getBooking(model);
        if (!toAddOptional.isPresent()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        } else {
            Booking toAdd = toAddOptional.get();
            if (model.hasBooking(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE);
            }
            if (!model.canAccommodate(toAdd)) {
                throw new CommandException(MESSAGE_FULL);
            }

            model.addBooking(toAdd);
            model.commitRestaurantBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBookingCommand // instanceof handles nulls
                && customerIndexedBooking.equals(((AddBookingCommand) other).customerIndexedBooking)); // state check
    }
}
