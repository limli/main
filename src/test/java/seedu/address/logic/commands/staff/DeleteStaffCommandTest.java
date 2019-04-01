package seedu.address.logic.commands.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStaffAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStaff.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.DeleteStaffCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.staff.Staff;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteStaffCommand}.
 */
public class DeleteStaffCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteStaffCommand.MESSAGE_DELETE_STAFF_SUCCESS, staffToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteStaff(staffToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(outOfBoundIndex);

        assertCommandFailure(deleteStaffCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStaffAtIndex(model, INDEX_FIRST);

        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteStaffCommand.MESSAGE_DELETE_STAFF_SUCCESS, staffToDelete);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteStaff(staffToDelete);
        expectedModel.commitRestaurantBook();
        showNoStaff(expectedModel);

        assertCommandSuccess(deleteStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStaffAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getStaffList().size());

        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(outOfBoundIndex);

        assertCommandFailure(deleteStaffCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteStaff(staffToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first staff deleted
        deleteStaffCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered staff list to show all staffs
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first staff deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteStaffCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Staff} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted staff in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the staff object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameStaffDeleted() throws Exception {
        DeleteStaffCommand deleteStaffCommand = new DeleteStaffCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showStaffAtIndex(model, INDEX_SECOND);
        Staff staffToDelete = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteStaff(staffToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second staff in unfiltered staff list / first staff in filtered staff list
        deleteStaffCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered staff list to show all staffs
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(staffToDelete, model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second staff in unfiltered staff list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteStaffCommand deleteFirstCommand = new DeleteStaffCommand(INDEX_FIRST);
        DeleteStaffCommand deleteSecondCommand = new DeleteStaffCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStaffCommand deleteFirstCommandCopy = new DeleteStaffCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different staff -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStaff(Model model) {
        model.updateFilteredStaffList(p -> false);

        assertTrue(model.getFilteredStaffList().isEmpty());
    }
}
