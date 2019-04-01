package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.MemberBuilder;

public class MemberTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameMember() {
        // same object -> returns true
        assertTrue(ALICE.isSameMember(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameMember(null));

        // different phone and email -> returns false
        Member editedAlice =
                new MemberBuilder(ALICE).withPhone(PERSON_VALID_PHONE_BOB).withEmail(PERSON_VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameMember(editedAlice));

        // different name -> returns false
        editedAlice = new MemberBuilder(ALICE).withName(PERSON_VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameMember(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new MemberBuilder(ALICE).withEmail(PERSON_VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSameMember(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new MemberBuilder(ALICE).withPhone(PERSON_VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSameMember(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new MemberBuilder(ALICE).build();
        assertTrue(ALICE.isSameMember(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Member aliceCopy = new MemberBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different member -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Member editedAlice = new MemberBuilder(ALICE).withName(PERSON_VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new MemberBuilder(ALICE).withPhone(PERSON_VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new MemberBuilder(ALICE).withEmail(PERSON_VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
