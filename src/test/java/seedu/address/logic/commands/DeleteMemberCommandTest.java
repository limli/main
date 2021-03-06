package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMemberAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalMembers.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.member.DeleteMemberCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.member.Member;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteMemberCommand}.
 */
public class DeleteMemberCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Member memberToDelete = model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased());
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteMemberCommand.MESSAGE_DELETE_MEMBER_SUCCESS, memberToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteMember(memberToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteMemberCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(outOfBoundIndex);

        assertCommandFailure(deleteMemberCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMemberAtIndex(model, INDEX_FIRST);

        Member memberToDelete = model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased());
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteMemberCommand.MESSAGE_DELETE_MEMBER_SUCCESS, memberToDelete);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteMember(memberToDelete);
        expectedModel.commitRestaurantBook();
        showNoMember(expectedModel);

        assertCommandSuccess(deleteMemberCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMemberAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getMemberList().size());

        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(outOfBoundIndex);

        assertCommandFailure(deleteMemberCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Member memberToDelete = model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased());
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteMember(memberToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first member deleted
        deleteMemberCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered member list to show all members
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first member deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteMemberCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Member} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted member in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the member object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameMemberDeleted() throws Exception {
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showMemberAtIndex(model, INDEX_SECOND);
        Member memberToDelete = model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteMember(memberToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second member in unfiltered member list / first member in filtered member list
        deleteMemberCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered member list to show all members
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(memberToDelete, model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second member in unfiltered member list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteMemberCommand deleteFirstCommand = new DeleteMemberCommand(INDEX_FIRST);
        DeleteMemberCommand deleteSecondCommand = new DeleteMemberCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMemberCommand deleteFirstCommandCopy = new DeleteMemberCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMember(Model model) {
        model.updateFilteredMemberList(p -> false);

        assertTrue(model.getFilteredMemberList().isEmpty());
    }
}
