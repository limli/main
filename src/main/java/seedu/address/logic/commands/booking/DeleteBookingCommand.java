package seedu.address.logic.commands.booking;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * Deletes a booking identified using it's displayed index from the address book.
 */
public class DeleteBookingCommand extends Command {
    public static final String COMMAND_WORD = "deletebooking";
    public static final String COMMAND_ALIAS = "db";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the booking identified by the index number used in the displayed member list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BOOKING_SUCCESS = "Deleted Booking: %1$s";

    private final Index targetIndex;

    public DeleteBookingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Booking> lastShownList = model.getFilteredBookingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
        }

        Booking bookingToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBooking(bookingToDelete);

        // by right, deleting a booking should never cause the capacity to be exceeded
        assert(model.getCapacity().canAccommodate(model.getFilteredBookingList()));
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_DELETE_BOOKING_SUCCESS, bookingToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBookingCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBookingCommand) other).targetIndex)); // state check
    }
}
