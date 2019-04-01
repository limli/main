package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.AMY;
import static seedu.address.testutil.TypicalMembers.BOB;
import static seedu.address.testutil.TypicalMembers.CARL;
import static seedu.address.testutil.TypicalMembers.HOON;
import static seedu.address.testutil.TypicalMembers.IDA;
import static seedu.address.testutil.TypicalMembers.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.add.AddMemberCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Member;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.MemberBuilder;
import seedu.address.testutil.MemberUtil;

public class AddMemberCommandSystemTest extends RestaurantBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a member to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Member toAdd = AMY;
        String command = "   " + AddMemberCommand.COMMAND_WORD + "  " + PERSON_NAME_DESC_AMY + "  "
                + PERSON_PHONE_DESC_AMY + " " + PERSON_EMAIL_DESC_AMY + " " + MEMBER_LOYALTY_POINTS_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addMember(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a member with all fields same as another member in the address book except name -> added */
        toAdd = new MemberBuilder(AMY).withName(PERSON_VALID_NAME_BOB).build();
        command = AddMemberCommand.COMMAND_WORD + PERSON_NAME_DESC_BOB + PERSON_PHONE_DESC_AMY + PERSON_EMAIL_DESC_AMY
                + MEMBER_LOYALTY_POINTS_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* Case: add a member with all fields same as another member in the address book except phone and email
         * -> added
         */
        toAdd = new MemberBuilder(AMY).withPhone(PERSON_VALID_PHONE_BOB).withEmail(PERSON_VALID_EMAIL_BOB).build();
        command = MemberUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllMembers();
        assertCommandSuccess(ALICE);

        /* Case: add a member, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddMemberCommand.COMMAND_WORD + MEMBER_LOYALTY_POINTS_DESC_BOB + PERSON_PHONE_DESC_BOB
                + PERSON_NAME_DESC_BOB + PERSON_EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a member -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the member list before adding -> added */
        showMembersWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a member card is selected --------------------------- */

        /* Case: selects first card in the member list, add a member -> added, card selection remains unchanged */
        selectMember(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate member -> rejected */
        command = MemberUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddMemberCommand.MESSAGE_DUPLICATE_MEMBER);

        /* Case: add a duplicate member except with different phone -> rejected */
        toAdd = new MemberBuilder(HOON).withPhone(PERSON_VALID_PHONE_BOB).build();
        command = MemberUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddMemberCommand.MESSAGE_DUPLICATE_MEMBER);

        /* Case: add a duplicate member except with different email -> rejected */
        toAdd = new MemberBuilder(HOON).withEmail(PERSON_VALID_EMAIL_BOB).build();
        command = MemberUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddMemberCommand.MESSAGE_DUPLICATE_MEMBER);

        /* Case: missing name -> rejected */
        command = AddMemberCommand.COMMAND_WORD + PERSON_PHONE_DESC_AMY + PERSON_EMAIL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddMemberCommand.COMMAND_WORD + PERSON_NAME_DESC_AMY + PERSON_EMAIL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddMemberCommand.COMMAND_WORD + PERSON_NAME_DESC_AMY + PERSON_PHONE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + MemberUtil.getMemberDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddMemberCommand.COMMAND_WORD + PERSON_INVALID_NAME_DESC
                + PERSON_PHONE_DESC_AMY + PERSON_EMAIL_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddMemberCommand.COMMAND_WORD + PERSON_NAME_DESC_AMY
                + PERSON_INVALID_PHONE_DESC + PERSON_EMAIL_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddMemberCommand.COMMAND_WORD + PERSON_NAME_DESC_AMY
                + PERSON_PHONE_DESC_AMY + PERSON_INVALID_EMAIL_DESC;
        assertCommandFailure(command, Email.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddMemberCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddMemberCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code MemberListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestaurantBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Member toAdd) {
        assertCommandSuccess(MemberUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Member)}. Executes {@code command}
     * instead.
     * @see AddMemberCommandSystemTest#assertCommandSuccess(Member)
     */
    private void assertCommandSuccess(String command, Member toAdd) {
        Model expectedModel = getModel();
        expectedModel.addMember(toAdd);
        String expectedResultMessage = String.format(AddMemberCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Member)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code MemberListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddMemberCommandSystemTest#assertCommandSuccess(String, Member)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code MemberListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
