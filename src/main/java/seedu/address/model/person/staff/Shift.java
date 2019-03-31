package seedu.address.model.person.staff;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents a single shift of a staff member in the restaurant book.
 */
public class Shift implements Comparable<Shift> {

    public static final String MESSAGE_CONSTRAINTS =
            "Day of week should be specified as MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY or SUNDAY.\n"
                    + "Start time and end time should be in the HH:MM format.\n";

    private final DayOfWeek startDayOfWeek;
    private final LocalTime startTime;
    private final DayOfWeek endDayOfWeek;
    private final LocalTime endTime;

    /**
     * Constructs a {@code Shift}.
     *
     * @param startDayOfWeek The start day of the week of the shift.
     * @param startTime The time the shift begins.
     * @param endDayOfWeek The start day of the week of the shift.
     * @param endTime The time the shift ends.
     */
    public Shift(DayOfWeek startDayOfWeek, LocalTime startTime, DayOfWeek endDayOfWeek, LocalTime endTime) {
        this.startDayOfWeek = startDayOfWeek;
        this.startTime = startTime;
        this.endDayOfWeek = endDayOfWeek;
        this.endTime = endTime;
    }

    @JsonCreator
    public Shift(@JsonProperty("startDayOfWeek") String startDayOfWeekString,
                  @JsonProperty("startTime") String startTimeString,
                 @JsonProperty("endDayOfWeek") String endDayOfWeekString,
                 @JsonProperty("endTime") String endTimeString) {
        // TODO: ensure starttime < endtime (shift is of non-zero duration)
        try {
            startDayOfWeek = DayOfWeek.valueOf(startDayOfWeekString);
            endDayOfWeek = DayOfWeek.valueOf(endDayOfWeekString);
            startTime = LocalTime.parse(startTimeString);
            endTime = LocalTime.parse(endTimeString);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks if this shift conflicts with {@code otherShift}
     *
     * @param otherShift The shift to check for conflicts with.
     * @return true if the two shifts conflict, false otherwise.
     */
    public boolean conflictsWith(Shift otherShift) {
        int thisStartDayOfWeek = this.startDayOfWeek.getValue();
        int thisEndDayOfWeek = this.endDayOfWeek.getValue();
        int otherStartDayOfWeek = otherShift.startDayOfWeek.getValue();
        int otherEndDayOfWeek = otherShift.endDayOfWeek.getValue();

        if (thisStartDayOfWeek <= thisEndDayOfWeek && otherStartDayOfWeek <= otherEndDayOfWeek) {
            return !(thisEndDayOfWeek < otherStartDayOfWeek || otherEndDayOfWeek < thisStartDayOfWeek
                    || (thisEndDayOfWeek == otherStartDayOfWeek && !this.endTime.isAfter(otherShift.startTime))
                    || (otherEndDayOfWeek == thisStartDayOfWeek && !otherShift.endTime.isAfter(this.startTime)));
        } else if (thisStartDayOfWeek <= thisEndDayOfWeek && otherStartDayOfWeek > otherEndDayOfWeek) {
            return !((otherEndDayOfWeek < thisStartDayOfWeek && thisEndDayOfWeek < otherStartDayOfWeek)
                    || (otherEndDayOfWeek < thisStartDayOfWeek && thisEndDayOfWeek == otherStartDayOfWeek
                        && (!this.endTime.isAfter(otherShift.startTime)))
                    || (otherEndDayOfWeek == thisStartDayOfWeek && thisEndDayOfWeek < otherStartDayOfWeek
                        && (!otherShift.endTime.isAfter(this.startTime))));
        } else if (thisStartDayOfWeek > thisEndDayOfWeek && otherStartDayOfWeek <= otherEndDayOfWeek) {
            return !((thisEndDayOfWeek < otherStartDayOfWeek && otherEndDayOfWeek < thisStartDayOfWeek)
                    || (thisEndDayOfWeek < otherStartDayOfWeek && otherEndDayOfWeek == thisStartDayOfWeek
                    && (!otherShift.endTime.isAfter(this.startTime)))
                    || (thisEndDayOfWeek == otherStartDayOfWeek && otherEndDayOfWeek < thisStartDayOfWeek
                    && (!this.endTime.isAfter(otherShift.startTime))));
        }
        return true;
    }

    @Override
    public int compareTo(Shift otherShift) {
        if (startDayOfWeek == otherShift.startDayOfWeek) {
            return startTime.compareTo(otherShift.startTime);
        } else {
            return startDayOfWeek.compareTo(otherShift.startDayOfWeek);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Shift) {
            return startDayOfWeek.equals(((Shift) other).startDayOfWeek)
                    && endDayOfWeek.equals(((Shift) other).endDayOfWeek)
                    && startTime.equals(((Shift) other).startTime)
                    && endTime.equals(((Shift) other).endTime);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%s, %s to %s, %s", startDayOfWeek, startTime, endDayOfWeek, endTime);
    }

}
