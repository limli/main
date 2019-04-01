package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX;
import static seedu.address.logic.commands.DeleteMemberCommand.MESSAGE_DELETE_MEMBER_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMember;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalMembers.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Member;

public class DeleteMemberCommandSystemTest extends RestaurantBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first member in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteMemberCommand.COMMAND_WORD + "      "
                + INDEX_FIRST.getOneBased() + "       ";
        Member deletedMember = removeMember(expectedModel, INDEX_FIRST);
        String expectedResultMessage = String.format(MESSAGE_DELETE_MEMBER_SUCCESS, deletedMember);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last member in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastMemberIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastMemberIndex);

        /* Case: undo deleting the last member in the list -> last member restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last member in the list -> last member deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeMember(modelBeforeDeletingLast, lastMemberIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle member in the list -> deleted */
        Index middleMemberIndex = getMidIndex(getModel());
        assertCommandSuccess(middleMemberIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered member list, delete index within bounds of address book and member list -> deleted */
        showMembersWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST;
        assertTrue(index.getZeroBased() < getModel().getFilteredMemberList().size());
        assertCommandSuccess(index);

        /* Case: filtered member list, delete index within bounds of address book but out of bounds of member list
         * -> rejected
         */
        showMembersWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getRestaurantBook().getMemberList().size();
        command = DeleteMemberCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a member card is selected ------------------------ */

        /* Case: delete the selected member -> member list panel selects the member before the deleted member */
        showAllMembers();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectMember(selectedIndex);
        command = DeleteMemberCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedMember = removeMember(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_MEMBER_SUCCESS, deletedMember);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteMemberCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteMemberCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getRestaurantBook().getMemberList().size() + 1);
        command = DeleteMemberCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteMemberCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteMemberCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
    }

    /**
     * Removes the {@code Member} at the specified {@code index} in {@code model}'s address book.
     * @return the removed member
     */
    private Member removeMember(Model model, Index index) {
        Member targetMember = getMember(model, index);
        model.deleteMember(targetMember);
        return targetMember;
    }

    /**
     * Deletes the member at {@code toDelete} by creating a default {@code DeleteMemberCommand} using
     * {@code toDelete} and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteMemberCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Member deletedMember = removeMember(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_MEMBER_SUCCESS, deletedMember);

        assertCommandSuccess(
                DeleteMemberCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteMemberCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see RestaurantBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
