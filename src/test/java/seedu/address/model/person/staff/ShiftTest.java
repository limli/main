package seedu.address.model.person.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ShiftBuilder;

public class ShiftTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void conflictsWith() {
        Shift shift1 = new ShiftBuilder().withStartDayOfWeek("MONDAY").withStartTime("12:00")
                .withEndDayOfWeek("MONDAY").withEndTime("14:00").build();
        Shift shift2 = new ShiftBuilder().withStartDayOfWeek("TUESDAY").withStartTime("12:00")
                .withEndDayOfWeek("TUESDAY").withEndTime("14:00").build();

        assertFalse(shift1.conflictsWith(shift2));
        assertFalse(shift2.conflictsWith(shift1));

        shift1 = new ShiftBuilder().withStartDayOfWeek("MONDAY").withStartTime("12:00")
                .withEndDayOfWeek("MONDAY").withEndTime("14:00").build();
        shift2 = new ShiftBuilder().withStartDayOfWeek("MONDAY").withStartTime("13:59")
                .withEndDayOfWeek("TUESDAY").withEndTime("15:00").build();

        assertTrue(shift1.conflictsWith(shift2));
        assertTrue(shift2.conflictsWith(shift1));

        shift1 = new ShiftBuilder().withStartDayOfWeek("MONDAY").withStartTime("12:00")
                .withEndDayOfWeek("MONDAY").withEndTime("14:00").build();
        shift2 = new ShiftBuilder().withStartDayOfWeek("MONDAY").withStartTime("14:00")
                .withEndDayOfWeek("MONDAY").withEndTime("16:00").build();

        assertFalse(shift1.conflictsWith(shift2));
        assertFalse(shift2.conflictsWith(shift1));

        shift1 = new ShiftBuilder().withStartDayOfWeek("MONDAY").withStartTime("12:00")
                .withEndDayOfWeek("MONDAY").withEndTime("14:00").build();
        shift2 = new ShiftBuilder().withStartDayOfWeek("SUNDAY").withStartTime("12:00")
                .withEndDayOfWeek("MONDAY").withEndTime("12:00").build();

        assertFalse(shift1.conflictsWith(shift2));
        assertFalse(shift2.conflictsWith(shift1));

        shift1 = new ShiftBuilder().withStartDayOfWeek("SUNDAY").withStartTime("00:00")
                .withEndDayOfWeek("SATURDAY").withEndTime("23:58").build();
        shift2 = new ShiftBuilder().withStartDayOfWeek("SATURDAY").withStartTime("23:58")
                .withEndDayOfWeek("SATURDAY").withEndTime("23:59").build();

        assertFalse(shift1.conflictsWith(shift2));
        assertFalse(shift2.conflictsWith(shift1));

        shift1 = new ShiftBuilder().withStartDayOfWeek("SUNDAY").withStartTime("00:00")
                .withEndDayOfWeek("SATURDAY").withEndTime("23:59").build();
        shift2 = new ShiftBuilder().withStartDayOfWeek("SATURDAY").withStartTime("23:59")
                .withEndDayOfWeek("SUNDAY").withEndTime("00:00").build();

        assertTrue(shift1.conflictsWith(shift2));
        assertTrue(shift2.conflictsWith(shift1));

        shift1 = new ShiftBuilder().withStartDayOfWeek("FRIDAY").withStartTime("12:00")
                .withEndDayOfWeek("WEDNESDAY").withEndTime("14:00").build();
        shift2 = new ShiftBuilder().withStartDayOfWeek("WEDNESDAY").withStartTime("13:59")
                .withEndDayOfWeek("THURSDAY").withEndTime("15:00").build();

        assertTrue(shift1.conflictsWith(shift2));
        assertTrue(shift2.conflictsWith(shift1));
    }

    @Test
    public void isValidShift() {
        // same day earlier time to same day later time -> true
        assertTrue(Shift.isValidShift(DayOfWeek.valueOf("MONDAY"), LocalTime.parse("00:00"),
                DayOfWeek.valueOf("MONDAY"), LocalTime.parse("23:59")));

        // same day to same day same time -> false
        assertFalse(Shift.isValidShift(DayOfWeek.valueOf("MONDAY"), LocalTime.parse("00:00"),
                DayOfWeek.valueOf("MONDAY"), LocalTime.parse("00:00")));

        // same day later time to same day earlier time -> false
        assertFalse(Shift.isValidShift(DayOfWeek.valueOf("MONDAY"), LocalTime.parse("23:59"),
                DayOfWeek.valueOf("MONDAY"), LocalTime.parse("00:00")));

        // earlier day earlier time to later day later time -> true
        assertTrue(Shift.isValidShift(DayOfWeek.valueOf("MONDAY"), LocalTime.parse("00:00"),
                DayOfWeek.valueOf("SUNDAY"), LocalTime.parse("23:59")));

        // earlier day earlier time to later day same time -> true
        assertTrue(Shift.isValidShift(DayOfWeek.valueOf("MONDAY"), LocalTime.parse("00:00"),
                DayOfWeek.valueOf("SUNDAY"), LocalTime.parse("00:00")));

        // earlier day later time to later day earlier time -> true
        assertTrue(Shift.isValidShift(DayOfWeek.valueOf("MONDAY"), LocalTime.parse("23:59"),
                DayOfWeek.valueOf("SUNDAY"), LocalTime.parse("00:00")));

        // later day earlier time to earlier day later time -> true
        assertTrue(Shift.isValidShift(DayOfWeek.valueOf("SUNDAY"), LocalTime.parse("00:00"),
                DayOfWeek.valueOf("MONDAY"), LocalTime.parse("23:59")));

        // later day earlier time to earlier day same time -> true
        assertTrue(Shift.isValidShift(DayOfWeek.valueOf("SUNDAY"), LocalTime.parse("00:00"),
                DayOfWeek.valueOf("MONDAY"), LocalTime.parse("00:00")));

        // later day later time to earlier day earlier time -> true
        assertTrue(Shift.isValidShift(DayOfWeek.valueOf("SUNDAY"), LocalTime.parse("23:59"),
                DayOfWeek.valueOf("MONDAY"), LocalTime.parse("00:00")));
    }

    @Test
    public void equals() {
        Shift shift1 = new ShiftBuilder().build();
        Shift shift2 = new ShiftBuilder().build();

        // same values -> returns true
        assertTrue(shift1.equals(shift2));

        // same object -> returns true
        assertTrue(shift1.equals(shift1));

        // null -> returns false
        assertFalse(shift1.equals(null));

        // different type -> returns false
        assertFalse(shift1.equals(5));

        // different start day -> returns false
        Shift editedShift = new ShiftBuilder().withStartDayOfWeek("TUESDAY").build();
        assertFalse(shift1.equals(editedShift));

        // different end day -> returns false
        editedShift = new ShiftBuilder().withEndDayOfWeek("SATURDAY").build();
        assertFalse(shift1.equals(editedShift));

        // different start time -> returns false
        editedShift = new ShiftBuilder().withStartTime("01:00").build();
        assertFalse(shift1.equals(editedShift));

        // different end time -> returns false
        editedShift = new ShiftBuilder().withEndTime("22:00").build();
        assertFalse(shift1.equals(editedShift));

    }

}
