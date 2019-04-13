package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedBooking.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.member.LoyaltyPoints;
import seedu.address.model.person.member.Member;
import seedu.address.testutil.Assert;
import seedu.address.testutil.MemberBuilder;



public class JsonAdaptedBookingTest {
    private static final String INVALID_CUSTOMER_NAME = "j@ck";
    private static final String INVALID_CUSTOMER_PHONE = "9!o3!234";
    private static final String INVALID_CUSTOMER_EMAIL = "bademail";
    private static final int INVALID_CUSTOMER_LOYALTY_POINTS = -1;
    private static final String INVALID_START_TIME = "ab:cd";
    private static final int INVALID_NUM_PERSONS = -1;

    private static final String VALID_CUSTOMER_NAME = "peter";
    private static final String VALID_CUSTOMER_PHONE = "91234567";
    private static final String VALID_CUSTOMER_EMAIL = "peter@example.com";
    private static final int VALID_CUSTOMER_LOYALTY_POINTS = 5;
    private static final String VALID_START_TIME = "2019-04-07T12:00";
    private static final int VALID_NUM_PERSONS = 10;

    private static final Member VALID_MEMBER = new MemberBuilder().withName(VALID_CUSTOMER_NAME)
            .withPhone(VALID_CUSTOMER_PHONE).withEmail(VALID_CUSTOMER_EMAIL)
            .withLoyaltyPoints(VALID_CUSTOMER_LOYALTY_POINTS).build();
    private static final BookingWindow VALID_BOOKING_WINDOW = new BookingWindow(VALID_START_TIME);
    private static final BookingSize VALID_BOOKING_SIZE = new BookingSize(VALID_NUM_PERSONS);
    private static final Booking VALID_BOOKING = new Booking(VALID_BOOKING_WINDOW, VALID_MEMBER, VALID_BOOKING_SIZE);

    @Test
    public void toModelType_validBookingDetails_returnsBooking() throws Exception {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(VALID_CUSTOMER_NAME, VALID_CUSTOMER_PHONE, VALID_CUSTOMER_EMAIL,
                        VALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, VALID_NUM_PERSONS);

        assertEquals(VALID_BOOKING, booking.toModelType());
    }

    @Test
    public void toModelType_validBooking_returnsBooking() throws Exception {
        JsonAdaptedBooking booking = new JsonAdaptedBooking(VALID_BOOKING);
        assertEquals(VALID_BOOKING, booking.toModelType());
    }

    @Test
    public void toModelType_invalidCustomerName_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(INVALID_CUSTOMER_NAME, VALID_CUSTOMER_PHONE, VALID_CUSTOMER_EMAIL,
                        VALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, VALID_NUM_PERSONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_nullCustomerName_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(null, VALID_CUSTOMER_PHONE, VALID_CUSTOMER_EMAIL,
                        VALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, VALID_NUM_PERSONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidCustomerPhone_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(VALID_CUSTOMER_NAME, INVALID_CUSTOMER_PHONE, VALID_CUSTOMER_EMAIL,
                        VALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, VALID_NUM_PERSONS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_nullCustomerPhone_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(VALID_CUSTOMER_NAME, null, VALID_CUSTOMER_EMAIL,
                        VALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, VALID_NUM_PERSONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidCustomerEmail_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(VALID_CUSTOMER_NAME, VALID_CUSTOMER_PHONE, INVALID_CUSTOMER_EMAIL,
                        VALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, VALID_NUM_PERSONS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_nullCustomerEmail_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(VALID_CUSTOMER_NAME, VALID_CUSTOMER_PHONE, null,
                        VALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, VALID_NUM_PERSONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidCustomerLoyaltyPoints_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(VALID_CUSTOMER_NAME, VALID_CUSTOMER_PHONE, VALID_CUSTOMER_EMAIL,
                        INVALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, VALID_NUM_PERSONS);
        String expectedMessage = LoyaltyPoints.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(VALID_CUSTOMER_NAME, VALID_CUSTOMER_PHONE, VALID_CUSTOMER_EMAIL,
                        VALID_CUSTOMER_LOYALTY_POINTS, INVALID_START_TIME, VALID_NUM_PERSONS);
        String expectedMessage = BookingWindow.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

    @Test
    public void toModelType_invalidNumPersons_throwsIllegalValueException() {
        JsonAdaptedBooking booking =
                new JsonAdaptedBooking(VALID_CUSTOMER_NAME, VALID_CUSTOMER_PHONE, VALID_CUSTOMER_EMAIL,
                        VALID_CUSTOMER_LOYALTY_POINTS, VALID_START_TIME, INVALID_NUM_PERSONS);
        String expectedMessage = BookingSize.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }
}
