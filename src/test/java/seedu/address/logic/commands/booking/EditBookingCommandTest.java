package seedu.address.logic.commands.booking;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookingAtIndex;
import static seedu.address.testutil.TypicalBookings.DANIEL_BOOKING_SECOND;
import static seedu.address.testutil.TypicalBookings.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;

import seedu.address.logic.commands.booking.EditBookingCommand.EditBookingDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.Booking;
import seedu.address.testutil.BookingBuilder;
import seedu.address.testutil.EditBookingDescriptorBuilder;

public class EditBookingCommandTest {

    private static final LocalDateTime sampleDateTime = LocalDateTime.of(2019, 3, 1, 12, 00);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // both booking size + time present
        Booking bookingToEdit = model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased());
        Booking editedBooking = new BookingBuilder(bookingToEdit).withDate(sampleDateTime).withNumPersons(5).build();

        EditBookingDescriptor descriptor =
                new EditBookingDescriptorBuilder().withBookingWindow(sampleDateTime).withBookingSize(5).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setBooking(model.getFilteredBookingList().get(0), editedBooking);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // only time present
        Booking bookingToEdit = model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased());
        Booking editedBooking = new BookingBuilder(bookingToEdit).withDate(sampleDateTime).build();

        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withBookingWindow(sampleDateTime).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setBooking(model.getFilteredBookingList().get(0), editedBooking);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldsSpecifiedUnfilteredList_success() {
        // empty descriptor
        int listSize = model.getFilteredBookingList().size();
        Index last = Index.fromOneBased(listSize);
        Booking bookingToEdit = model.getFilteredBookingList().get(last.getZeroBased());
        Booking editedBooking = new BookingBuilder(bookingToEdit).build();

        EditBookingDescriptor descriptor = new EditBookingDescriptor();
        EditBookingCommand editBookingCommand = new EditBookingCommand(last, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setBooking(model.getFilteredBookingList().get(last.getZeroBased()), editedBooking);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedFilteredList_success() {
        showBookingAtIndex(model, INDEX_SECOND);
        Booking bookingToEdit = model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased());
        Booking editedBooking = new BookingBuilder(bookingToEdit).withDate(sampleDateTime).build();

        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withBookingWindow(sampleDateTime).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setBooking(model.getFilteredBookingList().get(0), editedBooking);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBookingUnfilteredList_failure() {
        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder(DANIEL_BOOKING_SECOND).build();

        // this command edits the first booking by Daniel to be the same as the second one
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST, descriptor);

        assertCommandFailure(editBookingCommand, model, commandHistory, EditBookingCommand.MESSAGE_DUPLICATE_BOOKING);
    }

    @Test
    public void execute_invalidBookingIndexUnfilteredList_failure() {
        Index outOfBounds = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        EditBookingCommand editBookingCommand = new EditBookingCommand(outOfBounds, new EditBookingDescriptor());

        assertCommandFailure(editBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidBookingIndexFilteredList_failure() {
        showBookingAtIndex(model, INDEX_FIRST);
        Index outOfBounds = INDEX_SECOND;
        EditBookingCommand editBookingCommand = new EditBookingCommand(outOfBounds, new EditBookingDescriptor());

        assertCommandFailure(editBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }
}
