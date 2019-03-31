package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysStaff;

import org.junit.Test;

import guitests.guihandles.StaffCardHandle;
import seedu.address.model.person.staff.Staff;
import seedu.address.testutil.StaffBuilder;

public class StaffCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Staff staff = new StaffBuilder().build();
        StaffCard staffCard = new StaffCard(staff, 1);
        uiPartRule.setUiPart(staffCard);
        assertCardDisplay(staffCard, staff, 1);
    }

    @Test
    public void equals() {
        Staff staff = new StaffBuilder().build();
        StaffCard staffCard = new StaffCard(staff, 0);

        // same staff, same index -> returns true
        StaffCard copy = new StaffCard(staff, 0);
        assertTrue(staffCard.equals(copy));

        // same object -> returns true
        assertTrue(staffCard.equals(staffCard));

        // null -> returns false
        assertFalse(staffCard.equals(null));

        // different types -> returns false
        assertFalse(staffCard.equals(0));

        // different staff, same index -> returns false
        Staff differentStaff = new StaffBuilder().withName("differentName").build();
        assertFalse(staffCard.equals(new StaffCard(differentStaff, 0)));

        // same staff, different index -> returns false
        assertFalse(staffCard.equals(new StaffCard(staff, 1)));
    }

    /**
     * Asserts that {@code staffCard} displays the details of {@code expectedStaff} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(StaffCard staffCard, Staff expectedStaff, int expectedId) {
        guiRobot.pauseForHuman();

        StaffCardHandle staffCardHandle = new StaffCardHandle(staffCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", staffCardHandle.getId());

        // verify staff details are displayed correctly
        assertCardDisplaysStaff(expectedStaff, staffCardHandle);
    }
}
