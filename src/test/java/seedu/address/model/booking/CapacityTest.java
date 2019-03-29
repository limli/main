package seedu.address.model.booking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.BOB;
import static seedu.address.testutil.TypicalMembers.CARL;
import static seedu.address.testutil.TypicalMembers.DANIEL;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CapacityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Capacity(null));
    }

    @Test
    public void constructor_invalidCapacity_throwsIllegalArgumentException() {
        String invalidCapacity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Capacity(invalidCapacity));
    }

    @Test
    public void isValidCapacity() {
        // null capacity
        Assert.assertThrows(NullPointerException.class, () -> Capacity.isValidCapacity(null));

        // valid integer values
        assertTrue(Capacity.isValidCapacity(1));
        assertTrue(Capacity.isValidCapacity(Capacity.MAX_CAPACITY));

        // valid string values
        assertTrue(Capacity.isValidCapacity("1"));
        assertTrue(Capacity.isValidCapacity(Integer.toString(Capacity.MAX_CAPACITY)));

        // invalid integer values
        assertFalse(Capacity.isValidCapacity(0));
        assertFalse(Capacity.isValidCapacity(-1));
        assertFalse(Capacity.isValidCapacity(Integer.MAX_VALUE));
        assertFalse(Capacity.isValidCapacity(Integer.MIN_VALUE));
        assertFalse(Capacity.isValidCapacity(Capacity.MAX_CAPACITY + 1));

        // strings that do not correspond to any integer
        assertFalse(Capacity.isValidCapacity("abc"));
        assertFalse(Capacity.isValidCapacity(""));
        assertFalse(Capacity.isValidCapacity("-"));

        // strings that correspond to an integer, not in range
        assertFalse(Capacity.isValidCapacity("0"));
        assertFalse(Capacity.isValidCapacity("-1"));
        assertFalse(Capacity.isValidCapacity("99999999999999999")); // integer overflow test
        assertFalse(Capacity.isValidCapacity("-99999999999999999")); // integer underflow test
        assertFalse(Capacity.isValidCapacity(Integer.toString(Capacity.MAX_CAPACITY + 1)));
    }

    @Test
    public void canAccommodate() {
        List<Booking> bookingList = new ArrayList<>();
        LocalDateTime startTime0600 = LocalDateTime.of(2019, Month.MARCH, 16, 6, 0);
        LocalDateTime startTime0630 = LocalDateTime.of(2019, Month.MARCH, 16, 6, 30);
        LocalDateTime startTime0659 = LocalDateTime.of(2019, Month.MARCH, 16, 6, 59);
        LocalDateTime startTime0700 = LocalDateTime.of(2019, Month.MARCH, 16, 7, 0);

        bookingList.add(new Booking(new BookingWindow(startTime0600), ALICE, new BookingSize(5)));
        bookingList.add(new Booking(new BookingWindow(startTime0630), BOB, new BookingSize(3)));
        bookingList.add(new Booking(new BookingWindow(startTime0659), CARL, new BookingSize(2)));
        bookingList.add(new Booking(new BookingWindow(startTime0700), DANIEL, new BookingSize(5)));

        // at 0700, alice would have left. there are at most 10 persons in the restaurant at any point in time
        // therefore a capacity of 9 would be insufficient
        assertFalse(new Capacity(9).canAccommodate(bookingList));

        // a capacity of 10 is sufficient
        assertTrue(new Capacity(10).canAccommodate(bookingList));
    }

    @Test
    public void constructorTest() {
        // checks that the same object is returned when using String and int
        assertEquals(new Capacity(1), new Capacity("1"));
        assertEquals(new Capacity(Capacity.MAX_CAPACITY), new Capacity(Integer.toString(Capacity.MAX_CAPACITY)));
    }
}
