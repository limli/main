package seedu.address.logic.commands.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_VALID_APPOINTMENT_BOB;
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
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.staff.EditStaffCommand.EditStaffDescriptor;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.staff.Staff;
import seedu.address.testutil.EditStaffDescriptorBuilder;
import seedu.address.testutil.StaffBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditStaffCommand.
 */
public class EditStaffCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Staff editedStaff = new StaffBuilder().build();
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(editedStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditStaffCommand.MESSAGE_EDIT_STAFF_SUCCESS, editedStaff);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setStaff(model.getFilteredStaffList().get(0), editedStaff);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStaff = Index.fromOneBased(model.getFilteredStaffList().size());
        Staff lastStaff = model.getFilteredStaffList().get(indexLastStaff.getZeroBased());

        StaffBuilder staffInList = new StaffBuilder(lastStaff);
        Staff editedStaff = staffInList.withName(PERSON_VALID_NAME_BOB).withPhone(PERSON_VALID_PHONE_BOB)
                .withAppointment(STAFF_VALID_APPOINTMENT_BOB).build();

        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(PERSON_VALID_NAME_BOB)
                .withPhone(PERSON_VALID_PHONE_BOB).withAppointment(STAFF_VALID_APPOINTMENT_BOB).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(indexLastStaff, descriptor);

        String expectedMessage = String.format(EditStaffCommand.MESSAGE_EDIT_STAFF_SUCCESS, editedStaff);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setStaff(lastStaff, editedStaff);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST, new EditStaffDescriptor());
        Staff editedStaff = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditStaffCommand.MESSAGE_EDIT_STAFF_SUCCESS, editedStaff);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStaffAtIndex(model, INDEX_FIRST);

        Staff staffInFilteredList = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());
        Staff editedStaff = new StaffBuilder(staffInFilteredList).withName(PERSON_VALID_NAME_BOB).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST,
                new EditStaffDescriptorBuilder().withName(PERSON_VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditStaffCommand.MESSAGE_EDIT_STAFF_SUCCESS, editedStaff);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setStaff(model.getFilteredStaffList().get(0), editedStaff);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editStaffCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStaffUnfilteredList_failure() {
        Staff firstStaff = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(firstStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editStaffCommand, model, commandHistory, EditStaffCommand.MESSAGE_DUPLICATE_STAFF);
    }

    @Test
    public void execute_duplicateStaffFilteredList_failure() {
        showStaffAtIndex(model, INDEX_FIRST);

        // edit staff in filtered list into a duplicate in address book
        Staff staffInList =
                model.getRestaurantBook().getStaffList().get(INDEX_SECOND.getZeroBased());
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST,
                new EditStaffDescriptorBuilder(staffInList).build());

        assertCommandFailure(editStaffCommand, model, commandHistory, EditStaffCommand.MESSAGE_DUPLICATE_STAFF);
    }

    @Test
    public void execute_invalidStaffIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(PERSON_VALID_NAME_BOB).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidStaffIndexFilteredList_failure() {
        showStaffAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getStaffList().size());

        EditStaffCommand editStaffCommand = new EditStaffCommand(outOfBoundIndex,
                new EditStaffDescriptorBuilder().withName(PERSON_VALID_NAME_BOB).build());

        assertCommandFailure(editStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Staff editedStaff = new StaffBuilder().build();
        Staff staffToEdit = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(editedStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setStaff(staffToEdit, editedStaff);
        expectedModel.commitRestaurantBook();

        // edit -> first staff edited
        editStaffCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered staff list to show all staffs
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first staff edited again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(PERSON_VALID_NAME_BOB).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editStaffCommand, model, commandHistory, Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Staff} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited staff in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the staff object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameStaffEdited() throws Exception {
        Staff editedStaff = new StaffBuilder().build();
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(editedStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());

        showStaffAtIndex(model, INDEX_SECOND);
        Staff staffToEdit = model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased());
        expectedModel.setStaff(staffToEdit, editedStaff);
        expectedModel.commitRestaurantBook();

        // edit -> edits second staff in unfiltered staff list / first staff in filtered staff list
        editStaffCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered staff list to show all staffs
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredStaffList().get(INDEX_FIRST.getZeroBased()), staffToEdit);
        // redo -> edits same second staff in unfiltered staff list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditStaffCommand standardCommand = new EditStaffCommand(INDEX_FIRST, STAFF_DESC_AMY);

        // same values -> returns true

        EditStaffDescriptor copyDescriptor = new EditStaffDescriptor(STAFF_DESC_AMY);
        EditStaffCommand commandWithSameValues = new EditStaffCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditStaffCommand(INDEX_SECOND, STAFF_DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditStaffCommand(INDEX_FIRST, STAFF_DESC_BOB)));
    }

}
