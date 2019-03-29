package seedu.address.model.person.staff;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents a single shift of a staff member in the restaurant book.
 */
public class Shift implements Comparable<Shift> {

    public final DayOfWeek dayOfWeek;
    public final LocalTime startTime;
    public final LocalTime endTime;

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
        // TODO: ensure starttime < endtime
        try {
            dayOfWeek = DayOfWeek.valueOf(dayOfWeekString);
            startTime = LocalTime.parse(startTimeString);
            endTime = LocalTime.parse(endTimeString);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int compareTo(Shift otherShift) {
        if (dayOfWeek == otherShift.dayOfWeek) {
            return startTime.compareTo(otherShift.startTime);
        } else {
            return dayOfWeek.compareTo(otherShift.dayOfWeek);
        }
    }

}
