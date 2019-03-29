package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.staff.Appointment;
import seedu.address.testutil.Assert;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null));
    }

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointment = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }

    @Test
    public void isValidAppointment() {
        // null appointment name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid appointment name
        assertFalse(Appointment.isValidAppointmentName("")); // empty string
        assertFalse(Appointment.isValidAppointmentName(" ")); // spaces only
        assertFalse(Appointment.isValidAppointmentName("^")); // only non-alphanumeric characters
        assertFalse(Appointment.isValidAppointmentName("server*")); // contains non-alphanumeric characters

        // valid appointment name
        assertTrue(Appointment.isValidAppointmentName("floor manager")); // alphabets only
        assertTrue(Appointment.isValidAppointmentName("12345")); // numbers only
        assertTrue(Appointment.isValidAppointmentName("Table 2 Server")); // alphanumeric characters
        assertTrue(Appointment.isValidAppointmentName("Floor Manager")); // with capital letters
        assertTrue(Appointment.isValidAppointmentName("Manager of the 2nd Branch")); // long names
    }
}
