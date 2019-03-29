package seedu.address.model.person.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_VALID_APPOINTMENT_BOB;
import static seedu.address.testutil.TypicalStaff.ALICE;
import static seedu.address.testutil.TypicalStaff.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.StaffBuilder;

public class StaffTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameStaff() {
        // same object -> returns true
        assertTrue(ALICE.isSameStaff(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStaff(null));

        // different phone, email and appointment-> returns false
        Staff editedAlice = new StaffBuilder(ALICE).withPhone(PERSON_VALID_PHONE_BOB).withEmail(PERSON_VALID_EMAIL_BOB)
                .withAppointment(STAFF_VALID_APPOINTMENT_BOB).build();
        assertFalse(ALICE.isSameStaff(editedAlice));

        // different name -> returns false
        editedAlice = new StaffBuilder(ALICE).withName(PERSON_VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStaff(editedAlice));

        // same name, same phone, same appointment, different email -> returns true
        editedAlice = new StaffBuilder(ALICE).withEmail(PERSON_VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSameStaff(editedAlice));

        // same name, same email, same appointment, different phone -> returns true
        editedAlice = new StaffBuilder(ALICE).withPhone(PERSON_VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSameStaff(editedAlice));

        // same name, same email, same phone, different appointment -> returns true
        editedAlice = new StaffBuilder(ALICE).withAppointment(STAFF_VALID_APPOINTMENT_BOB).build();
        assertTrue(ALICE.isSameStaff(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Staff aliceCopy = new StaffBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different staff -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Staff editedAlice = new StaffBuilder(ALICE).withName(PERSON_VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new StaffBuilder(ALICE).withPhone(PERSON_VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StaffBuilder(ALICE).withEmail(PERSON_VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different appointment -> returns false
        editedAlice = new StaffBuilder(ALICE).withAppointment(STAFF_VALID_APPOINTMENT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
