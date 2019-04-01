package seedu.address.logic.commands.booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookingAtIndex;
import static seedu.address.testutil.TypicalBookings.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.DeleteBookingCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.Booking;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteBookingCommand}.
 */
public class DeleteBookingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Booking bookingToDelete = model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased());
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteBookingCommand.MESSAGE_DELETE_BOOKING_SUCCESS, bookingToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteBooking(bookingToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(outOfBoundIndex);

        assertCommandFailure(deleteBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookingAtIndex(model, INDEX_FIRST);

        Booking bookingToDelete = model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased());
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteBookingCommand.MESSAGE_DELETE_BOOKING_SUCCESS, bookingToDelete);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteBooking(bookingToDelete);
        expectedModel.commitRestaurantBook();
        showNoBooking(expectedModel);

        assertCommandSuccess(deleteBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookingAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getBookingList().size());

        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(outOfBoundIndex);

        assertCommandFailure(deleteBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Booking bookingToDelete = model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased());
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteBooking(bookingToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first booking deleted
        deleteBookingCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered booking list to show all bookings
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first booking deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookingList().size() + 1);
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteBookingCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_BOOKING_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Booking} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted booking in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the booking object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookingDeleted() throws Exception {
        DeleteBookingCommand deleteBookingCommand = new DeleteBookingCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showBookingAtIndex(model, INDEX_SECOND);
        Booking bookingToDelete = model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteBooking(bookingToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second booking in unfiltered booking list / first booking in filtered booking list
        deleteBookingCommand.execute(model, commandHistory);

        // undo -> reverts restaurant book back to previous state and filtered booking list to show all bookings
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(bookingToDelete, model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second booking in unfiltered bookings list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteBookingCommand deleteFirstCommand = new DeleteBookingCommand(INDEX_FIRST);
        DeleteBookingCommand deleteSecondCommand = new DeleteBookingCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBookingCommand deleteFirstCommandCopy = new DeleteBookingCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBooking(Model model) {
        model.updateFilteredBookingList(p -> false);
        assertTrue(model.getFilteredBookingList().isEmpty());
    }
}
