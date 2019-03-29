package seedu.address.model.person.staff;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents a single shift of a staff member in the restaurant book.
 */
public class Shift implements Comparable<Shift> {

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Constructs a {@code Shift}.
     *
     * @param dayOfWeek The day of the week of the shift.
     * @param startTime The time the shift begins.
     * @param endTime The time the shift ends.
     */
    public Shift(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Shift(String dayOfWeekString, String startTimeString, String endTimeString) {
        // TODO: ensure starttime < endtime (shift is of non-zero duration)
        try {
            dayOfWeek = DayOfWeek.valueOf(dayOfWeekString);
            startTime = LocalTime.parse(startTimeString);
            endTime = LocalTime.parse(endTimeString);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks if this shift conflicts with {@code otherShift}
     *
     * @param otherShift The shift to check for conflicts with.
     * @return true if the two shifts conflict, false otherwise.
     */
    public boolean conflictsWith(Shift otherShift) {
        if (otherShift.dayOfWeek == dayOfWeek) {
            return otherShift.endTime.compareTo(startTime) == -1
                    || otherShift.startTime.compareTo(endTime) == 1;
        }
        return false;
    }

    @Override
    public int compareTo(Shift otherShift) {
        if (dayOfWeek == otherShift.dayOfWeek) {
            return startTime.compareTo(otherShift.startTime);
        } else {
            return dayOfWeek.compareTo(otherShift.dayOfWeek);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Shift) {
            return dayOfWeek.equals(((Shift) other).dayOfWeek)
                    && startTime.equals(((Shift) other).startTime)
                    && endTime.equals(((Shift) other).endTime);
        } else {
            return false;
        }
    }

}
