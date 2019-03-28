package seedu.address.testutil;

import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.BENSON;
import static seedu.address.testutil.TypicalMembers.CARL;
import static seedu.address.testutil.TypicalMembers.DANIEL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RestaurantBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Member;

/**
 * A utility class containing a list of {@code Booking} objects to be used in tests.
 */
public class TypicalBookings {

    public static final LocalDateTime START_TIME = LocalDateTime.of(2019, 2, 23, 14, 30);
    public static final Booking ALICE_BOOKING =
            new Booking(new BookingWindow(START_TIME), ALICE, new BookingSize(5));
    public static final Booking BENSON_BOOKING =
            new Booking(new BookingWindow(START_TIME.plusHours(1)), BENSON, new BookingSize(5));
    public static final Booking CARL_BOOKING =
            new Booking(new BookingWindow(START_TIME.plusHours(1).plusDays(1)), CARL, new BookingSize(5));
    public static final Booking DANIEL_BOOKING =
            new Booking(new BookingWindow(START_TIME.minusDays(1)), DANIEL, new BookingSize(5));

    public static final Booking DANIEL_BOOKING_SECOND =
            new Booking(new BookingWindow(START_TIME.plusDays(3)), DANIEL, new BookingSize(5));

    public static RestaurantBook getTypicalAddressBook() {
        RestaurantBook ab = new RestaurantBook();
        // members should be loaded first before bookings
        for (Member member: TypicalMembers.getTypicalMembers()) {
            ab.addMember(member);
        }
        for (Booking booking: getTypicalBookings()) {
            ab.addBooking(booking);
        }
        return ab;
    }

    public static List<Booking> getTypicalBookings() {
        return new ArrayList<>(Arrays.asList(DANIEL_BOOKING, ALICE_BOOKING, BENSON_BOOKING, CARL_BOOKING,
                DANIEL_BOOKING_SECOND));
    }
}
