package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_LOYALTY_POINTS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_LOYALTY_POINTS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_AMY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalMembers.AMY;
import static seedu.address.testutil.TypicalMembers.BOB;
import static seedu.address.testutil.TypicalMembers.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMemberCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Member;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.MemberBuilder;
import seedu.address.testutil.MemberUtil;

public class EditMemberCommandSystemTest extends RestaurantBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST;
        String command = " " + EditMemberCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + PERSON_NAME_DESC_BOB
                + "  " + PERSON_PHONE_DESC_BOB + " " + PERSON_EMAIL_DESC_BOB + " " + MEMBER_LOYALTY_POINTS_DESC_BOB;
        Member editedMember = new MemberBuilder(BOB).build();
        assertCommandSuccess(command, index, editedMember);

        /* Case: undo editing the last member in the list -> last member restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last member in the list -> last member edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setMember(getModel().getFilteredMemberList().get(INDEX_FIRST.getZeroBased()),
                editedMember);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a member with new values same as existing values -> edited */
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                + PERSON_EMAIL_DESC_BOB;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a member with new values same as another member's values but with different name -> edited */
        assertTrue(getModel().getRestaurantBook().getMemberList().contains(BOB));
        index = INDEX_SECOND;
        assertNotEquals(getModel().getFilteredMemberList().get(index.getZeroBased()), BOB);
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_AMY + PERSON_PHONE_DESC_BOB
                + PERSON_EMAIL_DESC_BOB + " " + MEMBER_LOYALTY_POINTS_DESC_BOB;
        editedMember = new MemberBuilder(BOB).withName(PERSON_VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedMember);

        /* Case: edit a member with new values same as another member's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND;
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_AMY
                + PERSON_EMAIL_DESC_AMY;
        editedMember = new MemberBuilder(BOB).withPhone(PERSON_VALID_PHONE_AMY)
                .withEmail(PERSON_VALID_EMAIL_AMY).build();
        assertCommandSuccess(command, index, editedMember);

        /* Case: edit with invalid tag argument -> rejected */
        index = INDEX_FIRST;
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased() + " " + "t/";
        assertCommandFailure(command,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditMemberCommand.MESSAGE_USAGE));

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered member list, edit index within bounds of address book and member list -> edited */
        showMembersWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST;
        assertTrue(index.getZeroBased() < getModel().getFilteredMemberList().size());
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PERSON_NAME_DESC_BOB;
        Member memberToEdit = getModel().getFilteredMemberList().get(index.getZeroBased());
        editedMember = new MemberBuilder(memberToEdit).withName(PERSON_VALID_NAME_BOB).build();

        assertCommandSuccess(command, index, editedMember);

        /* Case: filtered member list, edit index within bounds of address book but out of bounds of member list
         * -> rejected
         */
        showMembersWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getRestaurantBook().getMemberList().size();
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + " " + invalidIndex + PERSON_NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a member card is selected -------------------------- */

        /* Case: selects first card in the member list, edit a member -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllMembers();
        index = INDEX_FIRST;
        selectMember(index);
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_AMY + PERSON_PHONE_DESC_AMY
                + PERSON_EMAIL_DESC_AMY + " " + MEMBER_LOYALTY_POINTS_DESC_AMY;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new member's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + " 0" + PERSON_NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditMemberCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + " -1" + PERSON_NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditMemberCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredMemberList().size() + 1;
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + " " + invalidIndex + PERSON_NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + PERSON_NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditMemberCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                EditMemberCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + PERSON_INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + PERSON_INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditMemberCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + PERSON_INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        /* Case: edit a member with new values same as another member's values -> rejected */
        executeCommand(MemberUtil.getAddCommand(BOB));
        assertTrue(getModel().getRestaurantBook().getMemberList().contains(BOB));
        index = INDEX_FIRST;
        assertFalse(getModel().getFilteredMemberList().get(index.getZeroBased()).equals(BOB));
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                + PERSON_EMAIL_DESC_BOB;
        assertCommandFailure(command, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);

        /* Case: edit a member with new values same as another member's values but with different tags -> rejected */
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                + PERSON_EMAIL_DESC_BOB;
        assertCommandFailure(command, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);

        /* Case: edit a member with new values same as another member's values but with different address -> rejected */
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                + PERSON_EMAIL_DESC_BOB;
        assertCommandFailure(command, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);

        /* Case: edit a member with new values same as another member's values but with different phone -> rejected */
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_AMY
                + PERSON_EMAIL_DESC_BOB;
        assertCommandFailure(command, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);

        /* Case: edit a member with new values same as another member's values but with different email -> rejected */
        command = EditMemberCommand.COMMAND_WORD + " " + index.getOneBased()
                + PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_BOB
                + PERSON_EMAIL_DESC_AMY;
        assertCommandFailure(command, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Member, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditMemberCommandSystemTest#assertCommandSuccess(String, Index, Member, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Member editedMember) {
        assertCommandSuccess(command, toEdit, editedMember, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditMemberCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the member at index {@code toEdit} being
     * updated to values specified {@code editedMember}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditMemberCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Member editedMember,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setMember(expectedModel.getFilteredMemberList().get(toEdit.getZeroBased()), editedMember);
        expectedModel.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditMemberCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see RestaurantBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
