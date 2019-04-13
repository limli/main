package seedu.address.logic.commands.booking;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.booking.AddBookingCommand.MESSAGE_FULL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKINGS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.exceptions.DuplicateItemException;

/**
 * Edits the details of an existing booking in the address book.
 */
public class EditBookingCommand extends Command {

    public static final String COMMAND_WORD = "editbooking";
    public static final String COMMAND_ALIAS = "eb";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the booking identified "
            + "by the index number used in the displayed booking list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_START_TIME + "START_TIME] "
            + "[" + PREFIX_NUMBER_PERSONS + "NUMBER_OF_PERSONS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_START_TIME + "2019-03-17T11:00 "
            + PREFIX_NUMBER_PERSONS + "2"; // TODO: change this message

    public static final String MESSAGE_EDIT_BOOKING_SUCCESS = "Edited booking: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BOOKING = "This booking already exists in the restaurant book.";

    private final Index index;
    private final EditBookingDescriptor editBookingDescriptor;

    /**
     * @param index of the booking in the filtered booking list to edit
     * @param editBookingDescriptor details to edit the booking with
     */
    public EditBookingCommand(Index index, EditBookingDescriptor editBookingDescriptor) {
        requireNonNull(index);
        requireNonNull(editBookingDescriptor);

        this.index = index;
        this.editBookingDescriptor = new EditBookingDescriptor(editBookingDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Booking> lastShownList = model.getFilteredBookingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
        }

        Booking bookingToEdit = lastShownList.get(index.getZeroBased());
        Booking editedBooking = createEditedBooking(bookingToEdit, editBookingDescriptor);

        if (!model.canAccommodateEdit(bookingToEdit, editedBooking)) {
            throw new CommandException(MESSAGE_FULL);
        }

        try {
            model.setBooking(bookingToEdit, editedBooking);
        } catch (DuplicateItemException e) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKING);
        }

        model.updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking));
    }

    /**
     * Creates and returns a {@code booking} with the details of {@code bookingToEdit}
     * edited with {@code editBookingDescriptor}.
     */
    private static Booking createEditedBooking(Booking bookingToEdit, EditBookingDescriptor editBookingDescriptor) {
        assert bookingToEdit != null;

        BookingWindow bookingWindow = editBookingDescriptor.getBookingWindow().orElse(bookingToEdit.getBookingWindow());
        BookingSize bookingSize = editBookingDescriptor.getBookingSize().orElse(bookingToEdit.getNumMembers());
        return new Booking(bookingWindow, bookingToEdit.getCustomer(), bookingSize);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditBookingCommand // instanceof handles nulls
                && index.equals(((EditBookingCommand) other).index)
                && editBookingDescriptor.equals(((EditBookingCommand) other).editBookingDescriptor)); // state check
    }

    /**
     * Stores the details to edit the booking with. Each non-empty field value will replace the
     * corresponding field value of the booking.
     */
    public static class EditBookingDescriptor {
        private BookingWindow bookingWindow;
        private BookingSize bookingSize;

        public EditBookingDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBookingDescriptor(EditBookingDescriptor toCopy) {
            setBookingWindow(toCopy.bookingWindow);
            setBookingSize(toCopy.bookingSize);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(bookingWindow, bookingSize);
        }

        public void setBookingWindow(BookingWindow bookingWindow) {
            this.bookingWindow = bookingWindow;
        }

        public Optional<BookingWindow> getBookingWindow() {
            return Optional.ofNullable(bookingWindow);
        }

        public void setBookingSize(BookingSize bookingSize) {
            this.bookingSize = bookingSize;
        }

        public Optional<BookingSize> getBookingSize() {
            return Optional.ofNullable(bookingSize);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBookingDescriptor)) {
                return false;
            }

            // state check
            EditBookingDescriptor e = (EditBookingDescriptor) other;

            return getBookingWindow().equals(e.getBookingWindow())
                    && getBookingSize().equals(e.getBookingSize());
        }
    }

}
