package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;

import org.junit.Test;

import seedu.address.logic.commands.EditMemberCommand.EditMemberDescriptor;
import seedu.address.testutil.EditMemberDescriptorBuilder;

public class EditMemberDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditMemberDescriptor descriptorWithSameValues = new EditMemberDescriptor(MEMBER_DESC_AMY);
        assertTrue(MEMBER_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(MEMBER_DESC_AMY.equals(MEMBER_DESC_AMY));

        // null -> returns false
        assertFalse(MEMBER_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(MEMBER_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(MEMBER_DESC_AMY.equals(MEMBER_DESC_BOB));

        // different name -> returns false
        EditMemberDescriptor editedAmy =
                new EditMemberDescriptorBuilder(MEMBER_DESC_AMY).withName(PERSON_VALID_NAME_BOB).build();
        assertFalse(MEMBER_DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditMemberDescriptorBuilder(MEMBER_DESC_AMY).withPhone(PERSON_VALID_PHONE_BOB).build();
        assertFalse(MEMBER_DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditMemberDescriptorBuilder(MEMBER_DESC_AMY).withEmail(PERSON_VALID_EMAIL_BOB).build();
        assertFalse(MEMBER_DESC_AMY.equals(editedAmy));

    }
}
