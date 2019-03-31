package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_VALID_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_VALID_APPOINTMENT_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RestaurantBook;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.ShiftRoster;
import seedu.address.model.person.staff.Staff;

/**
 * A utility class containing a list of {@code Staff} objects to be used in tests.
 */
public class TypicalStaff {

    public static final Staff ALICE = new StaffBuilder().withName("Alice Pauline").withPhone("94351253")
            .withEmail("alice@example.com").withAppointment("Server")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();
    public static final Staff BENSON = new StaffBuilder().withName("Benson Meier").withPhone("98765432")
            .withEmail("johnd@example.com").withAppointment("Cook")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();
    public static final Staff CARL = new StaffBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAppointment("Floor Manager")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();
    public static final Staff DANIEL = new StaffBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAppointment("Bartender")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();
    public static final Staff ELLE = new StaffBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAppointment("Cook")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();
    public static final Staff FIONA = new StaffBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAppointment("Manager")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();
    public static final Staff GEORGE = new StaffBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAppointment("Cleaner")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();

    // Manually added
    public static final Staff HOON = new StaffBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAppointment("Server")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();
    public static final Staff IDA = new StaffBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAppointment("Cook")
            .withShiftRoster(new ShiftRoster(new Shift("MONDAY", "12:00", "MONDAY", "14:00"))).build();

    // Manually added - Staff's details found in {@code CommandTestUtil}
    public static final Staff AMY = new StaffBuilder().withName(PERSON_VALID_NAME_AMY).withPhone(PERSON_VALID_PHONE_AMY)
            .withEmail(PERSON_VALID_EMAIL_AMY).withAppointment(STAFF_VALID_APPOINTMENT_AMY)
            .withEmptyShiftRoster().build();
    public static final Staff BOB = new StaffBuilder().withName(PERSON_VALID_NAME_BOB).withPhone(PERSON_VALID_PHONE_BOB)
            .withEmail(PERSON_VALID_EMAIL_BOB).withAppointment(STAFF_VALID_APPOINTMENT_BOB)
            .withEmptyShiftRoster().build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStaff() {} // prevents instantiation

    /**
     * Returns an {@code RestaurantBook} with all the typical staff.
     */
    public static RestaurantBook getTypicalAddressBook() {
        RestaurantBook ab = new RestaurantBook();
        for (Staff staff : getTypicalStaff()) {
            ab.addStaff(staff);
        }
        return ab;
    }

    public static List<Staff> getTypicalStaff() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
