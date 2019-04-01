package seedu.address.logic.commands.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_DESC_BOB;

import org.junit.Test;

import seedu.address.logic.commands.EditStaffCommand.EditStaffDescriptor;
import seedu.address.testutil.EditStaffDescriptorBuilder;

public class EditStaffDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditStaffDescriptor descriptorWithSameValues = new EditStaffDescriptor(STAFF_DESC_AMY);
        assertTrue(STAFF_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(STAFF_DESC_AMY.equals(STAFF_DESC_AMY));

        // null -> returns false
        assertFalse(STAFF_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(STAFF_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(STAFF_DESC_AMY.equals(STAFF_DESC_BOB));

        // different name -> returns false
        EditStaffDescriptor editedAmy =
                new EditStaffDescriptorBuilder(STAFF_DESC_AMY).withName(PERSON_VALID_NAME_BOB).build();
        assertFalse(STAFF_DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditStaffDescriptorBuilder(STAFF_DESC_AMY).withPhone(PERSON_VALID_PHONE_BOB).build();
        assertFalse(STAFF_DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditStaffDescriptorBuilder(STAFF_DESC_AMY).withEmail(PERSON_VALID_EMAIL_BOB).build();
        assertFalse(STAFF_DESC_AMY.equals(editedAmy));

    }
}
