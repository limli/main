package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedStaff.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalStaff.BENSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.staff.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class JsonAdaptedStaffTest {
    private static final String INVALID_NAME = "B3n$0n";
    private static final String INVALID_PHONE = "+65456789";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_APPOINTMENT = "A+ Head Chef";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_APPOINTMENT = BENSON.getAppointment().toString();

    @Test
    public void toModelType_validStaffDetails_returnsStaff() throws Exception {
        JsonAdaptedStaff staff = new JsonAdaptedStaff(BENSON);
        assertEquals(BENSON, staff.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStaff staff =
                new JsonAdaptedStaff(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_APPOINTMENT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStaff staff = new JsonAdaptedStaff(null, VALID_PHONE, VALID_EMAIL, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStaff staff =
                new JsonAdaptedStaff(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_APPOINTMENT);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStaff staff = new JsonAdaptedStaff(VALID_NAME, null, VALID_EMAIL, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedStaff staff =
                new JsonAdaptedStaff(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_APPOINTMENT);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStaff staff = new JsonAdaptedStaff(VALID_NAME, VALID_PHONE, null, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_invalidAppointment_throwsIllegalValueException() {
        JsonAdaptedStaff staff =
                new JsonAdaptedStaff(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_APPOINTMENT);
        String expectedMessage = Appointment.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullAppointment_throwsIllegalValueException() {
        JsonAdaptedStaff staff = new JsonAdaptedStaff(VALID_NAME, VALID_PHONE, VALID_EMAIL, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Appointment.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

}
